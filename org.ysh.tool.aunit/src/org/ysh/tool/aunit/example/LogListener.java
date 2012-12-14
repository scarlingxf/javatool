package org.ysh.tool.aunit.example;

import java.lang.reflect.Method;

import org.ysh.tool.aunit.runner.RunnerEvent;
import org.ysh.tool.aunit.runner.RunnerListener;
import org.ysh.tool.aunit.runner.RunnerEvent.AttrType;
import org.ysh.tool.aunit.runner.RunnerEvent.EventType;

public class LogListener implements RunnerListener{

	@Override
	public void fireEvent(RunnerEvent runnerEvent) {
		EventType type = runnerEvent.getEventType();
		Class<?> clz = (Class<?>)runnerEvent.getAttribute(AttrType.EXEC_CLASS);
		Object instance = runnerEvent.getAttribute(AttrType.EXEC_INST);
		Throwable t = (Throwable)runnerEvent.getAttribute(AttrType.EXCEPTION);
		Method m = (Method)runnerEvent.getAttribute(AttrType.EXEC_METHOD);
		
		switch(type){
		case START_RUN_CLASS:
			System.out.println("Start to run class [" + clz.getName()  + "] -> [" + instance + "]");
			break;
		case START_RUN_METHOD:
			System.out.println("Start to run class [" + clz.getCanonicalName()  + "] -> [" + instance + "]" + " -> [" + m.getName() + "]");
			break;
		case SUCCESS_RUN_METHOD:
			System.out.println("Success to run method [" + clz.getCanonicalName()  + "] -> [" + instance + "]" + " -> [" + m.getName() + "]");
			break;
		case FAIL_RUN_METHOD:
			System.err.println("Fail to run method [" + clz.getCanonicalName()  + "] -> [" + instance + "]" + " -> [" + m.getName() + "]");
			System.err.println("ErrorMessage:" + t.getCause().toString());
			break;
		case FINISH_RUN_CLASS:
			System.out.println("Finish to run class [" + clz.getName()  + "] -> [" + instance + "]");
		}
	}

}
