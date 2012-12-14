package org.ysh.tool.aunit.example;

import java.util.Arrays;
import java.util.List;

import org.ysh.tool.aunit.runner.DefaultRunner;
import org.ysh.tool.aunit.runner.Runner;

public class RunnerWithDefaultClassLoader {
	protected List<String> testCaseList(){
		return Arrays.<String>asList("org.ysh.tool.aunit.example.TestCaseWithAssert", "org.ysh.tool.aunit.example.TestCaseWithNoAssert");
	}
	
	public final void run() throws Throwable{
		Runner runner = new DefaultRunner();
		runner.addListener(new LogListener());
		
		for(String btz : this.testCaseList()){
			runner.run(btz);
			System.out.println("********************************************************************************************************************");
		}
	}
	
	public static void main(String[] args) throws Throwable{
		new RunnerWithDefaultClassLoader().run();
	}
}
