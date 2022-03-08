package com.goibibo.utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Listeners implements ITestListener {

	public void onFinish(ITestContext arg0) {
		System.out.println("****************SUITE FINISHED*************");
	}

	public void onStart(ITestContext arg0) {
		System.out.println("****************SUITE STARTED*************");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		System.out.println("****************TEST FAILED WITH SUCCESS PERCENTAGE*************");
	}

	public void onTestFailure(ITestResult arg0) {
		Reporter.log("The name of the testcase failed is: "+arg0.getName().toUpperCase(), true);
	}

	public void onTestSkipped(ITestResult arg0) {
		Reporter.log("The name of the testcase Skipped is: "+arg0.getName().toUpperCase(), true);
	}

	public void onTestStart(ITestResult arg0) {
		Reporter.log("Test case started: "+ arg0.getName().toUpperCase(), true);	
	}

	public void onTestSuccess(ITestResult arg0) {
		Reporter.log("The name of the testcase passed is: "+arg0.getName().toUpperCase(), true);
	}

}
