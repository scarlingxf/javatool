package org.ysh.tool.aunit.error;

public class CompareFailedError extends AssertionError{
	private static final long serialVersionUID = -8373632606906712868L;
	
	private Object actual;
	private Object expected;
	private String additionMsg;
	
	public CompareFailedError(Object actual, Object expected, String additionMsg){
		super(errorMessage(actual, expected, additionMsg));
		this.actual = actual;
		this.expected = expected;
		this.additionMsg = additionMsg;
	}
	
	
	public Object getActual() {
		return actual;
	}

	public Object getExpected() {
		return expected;
	}

	public String getAdditionMsg() {
		return additionMsg;
	}

	private static String errorMessage(Object actual, Object expected, String additionMsg){
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("Expected value=" + obj2Str(expected) + "," + "Actual value=" + obj2Str(actual));
		buffer.append("\n");
		buffer.append(additionMsg);
		
		return buffer.toString();
	}
	
	private static final String obj2Str(Object obj){
		return obj == null ? "" : obj.toString();
	}
}
