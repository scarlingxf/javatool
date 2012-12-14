package org.ysh.tool.aunit;

import org.ysh.tool.aunit.error.AssertionFailedError;
import org.ysh.tool.aunit.error.CompareFailedError;

public final class Assert {
	public static final void assertTrue(boolean condition){
		if(!condition){
			fail(null);
		}
	}
	
	public static final void assertTrue(boolean condition, String messageOnError){
		if(!condition){
			fail(messageOnError);
		}
	}
	
	public static final void assertEquals(boolean actual, boolean expected, String messageWhenNotEqual){
		if(actual != expected){
			compareFail(actual, expected, messageWhenNotEqual);
		}
	}
	
	public static final void assertEquals(byte actual, byte expected, String messageWhenNotEqual){
		if(actual != expected){
			compareFail(actual, expected, messageWhenNotEqual);
		}
	}
	
	public static final void assertEquals(short actual, short expected, String messageWhenNotEqual){
		if(actual != expected){
			compareFail(actual, expected, messageWhenNotEqual);
		}
	}
	
	public static final void assertEquals(int actual, int expected, String messageWhenNotEqual){
		if(actual != expected){
			compareFail(actual, expected, messageWhenNotEqual);
		}
	}
	
	public static final void assertEquals(long actual, long expected, String messageWhenNotEqual){
		if(actual != expected){
			compareFail(actual, expected, messageWhenNotEqual);
		}
	}
	
	public static final void assertEquals(float actual, float expected, String messageWhenNotEqual){
		assertEquals(actual, expected, 0f, messageWhenNotEqual);
	}
	
	public static final void assertEquals(float actual, float expected, float delta, String messageWhenNotEqual){
		if(Math.abs(actual - expected) > Math.abs(delta)){
			compareFail(actual, expected, messageWhenNotEqual);
		}
	}
	
	public static final void assertEquals(double actual, double expected, String messageWhenNotEqual){
		assertEquals(actual, expected, 0f, messageWhenNotEqual);
	}
	
	public static final void assertEquals(double actual, double expected, double delta, String messageWhenNotEqual){
		if(Math.abs(actual - expected) > Math.abs(delta)){
			compareFail(actual, expected, messageWhenNotEqual);
		}
	}
	
	public static final void assertEquals(Object actual, Object expected, String messageWhenNotEqual){
		if(expected == null && actual != null){
			compareFail(actual, expected, messageWhenNotEqual);
		} else{
			if(actual == null || !actual.equals(expected)){
				compareFail(actual, expected, messageWhenNotEqual);
			}
		}
	}
	
	
	private static void compareFail(Object actual, Object expected, String msg){
		throw new CompareFailedError(actual, expected, msg);
	}
	
	private static void fail(String msg){
		throw new AssertionFailedError(msg);
	}
}
