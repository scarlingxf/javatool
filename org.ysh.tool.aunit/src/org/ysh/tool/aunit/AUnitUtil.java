package org.ysh.tool.aunit;

import java.util.ArrayList;
import java.util.List;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.ysh.tool.aunit.annotation.After;
import org.ysh.tool.aunit.annotation.AfterClass;
import org.ysh.tool.aunit.annotation.Before;
import org.ysh.tool.aunit.annotation.BeforeClass;
import org.ysh.tool.aunit.annotation.Test;
import org.ysh.tool.aunit.error.ValidateException;


public class AUnitUtil {
	
	public static final boolean hasAnnotation(AnnotatedElement element, Class<? extends Annotation> annotationClass){
		return element != null && element.isAnnotationPresent(annotationClass);
	}
	
	public static final boolean isBeforClasseMethod(Method method){
		return hasAnnotation(method, BeforeClass.class);
	}
	
	public static final boolean isAfterClassMethod(Method method){
		return hasAnnotation(method, AfterClass.class);
	}
	
	public static final boolean isTestMethod(Method method){
		return hasAnnotation(method, Test.class);
	}
	
	public static final boolean isBeforeMethod(Method method){
		return hasAnnotation(method, Before.class);
	}
	
	public static final boolean isAfterMethod(Method method){
		return hasAnnotation(method, After.class);
	}
	
	public static final List<Method> findMethodsWithAnnotation(Class<?> btClass, Class<? extends Annotation> annotationClass, boolean includeMethodsFromSupperClass){
		List<Method> list = new ArrayList<Method>();
		
		if(btClass != null && annotationClass != null){
			Method[] methods = includeMethodsFromSupperClass ? btClass.getMethods() : btClass.getDeclaredMethods();
			for(Method method : methods){
				if(hasAnnotation(method, annotationClass)){
					list.add(method);
				}
			}
		}
		
		return list;
	}
	
	public static final List<Method> getBeforeClassMethodList(Class<?> btClass){
		return findMethodsWithAnnotation(btClass, BeforeClass.class, false);
	}
	
	public static final List<Method> getBeforeMethodList(Class<?> btClass){
		return findMethodsWithAnnotation(btClass, Before.class, false);
	}
	
	public static final List<Method> getTestMethodList(Class<?> btClass){
		return findMethodsWithAnnotation(btClass, Test.class, false);
	}
	
	public static final List<Method> getAfterMethodList(Class<?> btClass){
		return findMethodsWithAnnotation(btClass, After.class, false);
	}
	
	public static final List<Method> getAfterClassMethodList(Class<?> btClass){
		return findMethodsWithAnnotation(btClass, AfterClass.class, false);
	}
	
	public static final boolean isAUnitClass(Class<?> clz){
		return !AUnitUtil.findMethodsWithAnnotation(clz, Test.class, true).isEmpty();
	}

	
	public static final void validateTestClass(Class<?> btClass) throws ValidateException{
		if(btClass == null){
			throw new ValidateException("Class can not be null");
		}
		
		if(! Modifier.isPublic(btClass.getModifiers())){
			throw new ValidateException("Class " + btClass.getName() + " must be public");
		}
		
		if(Modifier.isAbstract(btClass.getModifiers())){
			throw new ValidateException("Class " + btClass.getName() + " must not be abstract");
		}
		
		boolean hasConstructorWithNoArg = false;
		for(Constructor<?> c : btClass.getConstructors()){
			if(c.getParameterTypes().length == 0){
				hasConstructorWithNoArg = true;
				break;
			}
		}
		
		if(!hasConstructorWithNoArg){
			throw new ValidateException("Class " + btClass.getName() + " must have one public constructor with no arguments");
		}
	}
	
	public static final void validateTestMethod(Method method) throws ValidateException{
		if(method == null){
			throw new NullPointerException("Method can not be null");
		}
		
		if(! Modifier.isPublic(method.getModifiers())){
			throw new ValidateException("Method " + method.getName() + " should be public");
		}
		
		if(method.getReturnType() != void.class){
			throw new ValidateException ("Method " + method.getName() + " should has not return parameter");
		}
		
		if(method.getParameterTypes().length != 0){
			throw new ValidateException("Method " + method.getName() + " should has no parameter");
		}
		
		if(Modifier.isStatic(method.getModifiers())){
			throw new ValidateException("Method " + method.getName() + " should not be static");
		}
		
		if(Modifier.isAbstract(method.getModifiers())){
			throw new ValidateException("Method " + method.getName() + " should not be abstract");
		}
	}
	
}
