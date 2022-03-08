package com.goibibo.baseclass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.goibibo.library.ReusableMethods;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Baseclass extends ReusableMethods{
	public static WebDriver driver;
	
	@BeforeSuite
	public void setupApplication() {
		Reporter.log("===== Browser Session Started =====", true);
		//setup the chrome driver using WebDriverManager
	    WebDriverManager.chromedriver().setup();

	    //Create Chrome Options
	    ChromeOptions option = new ChromeOptions();
	    option.addArguments("--test-type");
	    option.addArguments("--disable-popup-bloacking");
	    DesiredCapabilities chrome = DesiredCapabilities.chrome();
	    chrome.setJavascriptEnabled(true);
	    chrome.setCapability(ChromeOptions.CAPABILITY, option);

	    //Create driver object for Chrome
	    driver = new ChromeDriver(option);
	    Reporter.log("=====Application Started=====", true);
	}

	 @AfterSuite
	 public void afterTest() {
		 quitBrowser(driver);
		 Reporter.log("=====Application Finished=====", true);
	  }
	
}
