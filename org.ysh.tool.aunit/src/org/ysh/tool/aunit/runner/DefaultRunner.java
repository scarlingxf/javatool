package org.ysh.tool.aunit.runner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.ysh.tool.aunit.AUnitUtil;
import org.ysh.tool.aunit.error.ValidateException;
import org.ysh.tool.aunit.runner.RunnerEvent.AttrType;
import org.ysh.tool.aunit.runner.RunnerEvent.EventType;

public class DefaultRunner implements Runner {
	private List<RunnerListener> runnerListeners;

	private Object eventLock = new Object();

	public DefaultRunner() {
		runnerListeners = new ArrayList<RunnerListener>();
		registerInternalListener();
	}
	
	@Override
	public void run(String btClzName) throws Throwable {
		if (btClzName != null) {
			Class<?> clz = null;
			Object instance = null;
			
			/*Init the test class*/
			try{
				/* Construct the class to be run */
				clz = initClass(btClzName);

				/* Create instance */
				instance = createInstance(clz);
			}catch(Throwable t){
				throw t;
			}
			
			/*Run the test class*/
			try{
				/* Fire start event */
				fireStartTestEvent(clz, instance);

				/* execute methods with annotation BeforeClass */
				execteBeforeClassTestMethods(clz, instance);

				/* execute methods with annotation Test */
				executeTestMethods(clz, instance);

				/* execute methods with annotation AfterClass */
				executeAfterClassTestMethods(clz, instance);
			}catch(Throwable t){
				throw t;
			}finally{
				/* fire finish event */
				fireFinishTestEvent(clz, instance);
			}
		}
	}

	protected Class<?> initClass(String clzName) throws Throwable {
		Class<?> clz = null;
		try{
			clz = Class.forName(clzName);
		}catch(ClassNotFoundException e){
			clz = Thread.currentThread().getContextClassLoader().loadClass(clzName);
		}
		AUnitUtil.validateTestClass(clz);
		
		return clz;
	}

	protected Object createInstance(Class<?> clz) throws Throwable{
		// Create instance
		Object instance = clz.newInstance();
		
		return instance;
	}
	protected void execteBeforeClassTestMethods(Class<?> clz, Object instance) throws Throwable {
		for (Method method : AUnitUtil.getBeforeClassMethodList(clz)) {
			executeMethod(clz, instance, method);
		}
	}

	protected void execteBeforeTestMethods(Class<?> clz, Object instance) throws Throwable {
		for (Method method : AUnitUtil.getBeforeMethodList(clz)) {
			executeMethod(clz, instance, method);
		}
	}

	protected void executeAfterTestMethods(Class<?> clz, Object instance) throws Throwable {
		for (Method method : AUnitUtil.getAfterMethodList(clz)) {
			executeMethod(clz, instance, method);
		}
	}

	protected void executeTestMethods(Class<?> clz, Object instance) throws Throwable {
		for (Method method : AUnitUtil.getTestMethodList(clz)) {
			execteBeforeTestMethods(clz, instance);
			executeMethod(clz, instance, method);
			executeAfterTestMethods(clz, instance);
		}
	}

	protected void executeAfterClassTestMethods(Class<?> clz, Object instance) throws Throwable {
		for (Method method : AUnitUtil.getAfterClassMethodList(clz)) {
			executeMethod(clz, instance, method);
		}
	}

	protected void executeMethod(Class<?> clz, Object instance, Method method) throws Throwable {
		try {
			AUnitUtil.validateTestMethod(method);
			method.invoke(instance);
		} catch (AssertionError error) {
			this.fireFailRunTestMethodEvent(clz, instance, method, error);
			return;
		} catch (ValidateException ve) {
			this.fireFailRunTestMethodEvent(clz, instance, method, ve);
			return;
		} catch (Throwable t) {
			this.fireFailRunTestMethodEvent(clz, instance, method, t);
			return;
		}

		this.fireSuccessRunTestMethodEvent(clz, instance, method);
	}

	private RunnerEvent createEvent(EventType eventType, Class<?> clz,
			Object instance, Method method, Throwable t) {
		RunnerEvent event = new RunnerEvent();

		event.setEventType(eventType);
		event.addAttribute(AttrType.EXEC_CLASS, clz);
		event.addAttribute(AttrType.EXEC_INST, instance);
		event.addAttribute(AttrType.EXEC_METHOD, method);
		event.addAttribute(AttrType.EXCEPTION, t);

		return event;

	}

	protected void fireFinishTestEvent(Class<?> clz, Object instance) {
		RunnerEvent event = createEvent(EventType.FINISH_RUN_CLASS, clz, instance, null, null);
		fireEvent(event);
	}

	protected void fireStartTestEvent(Class<?> clz, Object instance) {
		RunnerEvent event = createEvent(EventType.START_RUN_CLASS, clz,
				instance, null, null);
		fireEvent(event);
	}

	protected void fireStartTestMethodEvent(Class<?> clz, Object instance, Method method) {
		RunnerEvent event = createEvent(EventType.START_RUN_METHOD, clz, instance, method, null);
		fireEvent(event);
	}

	protected void fireSuccessRunTestMethodEvent(Class<?> clz, Object instance, Method method) {
		RunnerEvent event = createEvent(EventType.SUCCESS_RUN_METHOD, clz, instance, method, null);
		fireEvent(event);
	}

	protected void fireFailRunTestMethodEvent(Class<?> clz, Object instance, Method method, Throwable t) {
		RunnerEvent event = createEvent(EventType.FAIL_RUN_METHOD, clz, instance, method, t);
		fireEvent(event);
	}

	protected void fireEvent(RunnerEvent event) {
		synchronized (eventLock) {
			for (RunnerListener listener : this.runnerListeners) {
				try {
					listener.fireEvent(event);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}

	@Override
	public void addListener(RunnerListener listener) {
		if (listener != null) {
			runnerListeners.add(listener);
		}
	}

	@Override
	public void removeListener(RunnerListener listener) {
		if (listener != null) {
			runnerListeners.remove(listener);
		}
	}
	
	protected void registerInternalListener(){
		
	}
	
	public static final void runTestCase(String clzName) throws Throwable{
		new DefaultRunner().run(clzName);
	}
}
