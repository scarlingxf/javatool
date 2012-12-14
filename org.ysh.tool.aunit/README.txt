There is a simple BT library.

Features: 1. Support five annotations: BeforeClass, Before, Test, After, AfterClass
          2. Support adding listeners to monitor the status of the BT runner
		  3. Support loading class using customer classloader.If you want to load class using customer classloader, just change the ThreadContextLoader to your own classloader.
		     But note that, the runner will first used the default classloader to load the test case, if faild, then use the customer classloader

There is some exmaple code in org.ysh.tool.aunit.example

Note: 1. Now the lib does not support test method inherited from super class
