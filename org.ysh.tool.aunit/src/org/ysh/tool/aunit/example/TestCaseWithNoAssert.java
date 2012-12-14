package org.ysh.tool.aunit.example;

import org.ysh.tool.aunit.annotation.After;
import org.ysh.tool.aunit.annotation.AfterClass;
import org.ysh.tool.aunit.annotation.Before;
import org.ysh.tool.aunit.annotation.BeforeClass;
import org.ysh.tool.aunit.annotation.Test;

public class TestCaseWithNoAssert{
	@BeforeClass
	public void setUpClass(){
		//System.out.println("------------ setUpClass() -----------");
	}
	
	@Before
	public void setUp(){
		//System.out.println("------------ setUp() -----------");
	}
	
	@Test
	public void test1(){
		//System.out.println("------------ test1() -----------");
	}
	
	@Test
	public void test2(){
		//System.out.println("------------ test2() -----------");
	}
	
	@After
	public void tearDown(){
		//System.out.println("------------ tearDown() -----------");
	}
	
	@AfterClass
	public void tearDownClass(){
		//System.out.println("------------ tearDownClass() -----------");
	}
}
