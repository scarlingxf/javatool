package org.ysh.tool.aunit.runner;

public interface Runner {
	public void run(String btClzName) throws Throwable;
	
	public void addListener(RunnerListener listener);
	
	public void removeListener(RunnerListener listener);
}
