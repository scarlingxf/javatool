package org.ysh.tool.aunit.error;

public class AssertionFailedError extends AssertionError {
	private static final long serialVersionUID = 4978429766264111448L;

	public AssertionFailedError(){
		super();
	}
	
	public AssertionFailedError(String message){
		super(defaultMessage(message));
	}
	
	private static final String defaultMessage(String message){
		return message == null ? "" : message;
	}
}
