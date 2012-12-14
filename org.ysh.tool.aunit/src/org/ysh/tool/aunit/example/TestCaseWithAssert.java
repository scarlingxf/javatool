package org.ysh.tool.aunit.example;

import org.ysh.tool.aunit.Assert;
import org.ysh.tool.aunit.annotation.After;
import org.ysh.tool.aunit.annotation.AfterClass;
import org.ysh.tool.aunit.annotation.Before;
import org.ysh.tool.aunit.annotation.BeforeClass;
import org.ysh.tool.aunit.annotation.Test;

public class TestCaseWithAssert{
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
		Assert.assertTrue(true);
	}
	
	@Test
	public void test2(){
		//System.out.println("------------ test2() -----------");
		Assert.assertTrue(false, "Some error message");
	}
	
	@After
	public void tearDown(){
		//System.out.println("------------ tearDown() -----------");
	}
	
	@AfterClass
	public void tearDownClass(){
		//System.out.println("------------ tearDownClass() -----------");
	}
	
	@Test
	public void test3(){
		//System.out.println("------------ test3() -----------");
		Assert.assertEquals(1, 2, "does not equals");
	}
}
