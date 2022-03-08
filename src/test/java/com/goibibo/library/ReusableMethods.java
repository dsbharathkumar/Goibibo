package com.goibibo.library;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class ReusableMethods {

	private static final long STANDARD_TIME_OUT = 30;

	public static enum Locators {
		xpath, id, name, classname, paritallinktext, linktext, tagname, cssSelector
	};

	/**
	 * This function is for locating web elements
	 * 
	 * @param locators
	 * @param elementLocator
	 * @return By
	 */
	public By getLocator(Locators locators, String elementLocator) {
		By byElement = null;
		switch (locators) {
		case name:
			byElement = By.name(elementLocator);
			break;

		case id:
			byElement = By.id(elementLocator);
			break;

		case xpath:
			byElement = By.xpath(elementLocator);
			break;

		case classname:
			byElement = By.className(elementLocator);
			break;

		case linktext:
			byElement = By.linkText(elementLocator);
			break;

		case paritallinktext:
			byElement = By.partialLinkText(elementLocator);
			break;

		case tagname:
			byElement = By.tagName(elementLocator);
			break;

		case cssSelector:
			byElement = By.cssSelector(elementLocator);
			break;

		}
		return byElement;
	}

	/**
	 * This function is for opening an URL.
	 * 
	 * @param driver
	 * @param url
	 */
	public void getUrl(WebDriver driver, String url) {
		try {
			driver.get(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This function is for maximizing the current window.
	 * @param driver
	 */
	public void maximizeWindow(WebDriver driver) {
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This function is for implicitly wait.
	 * @param driver
	 * @param time
	 * @param timeUnit
	 */
	public void implicitWait(WebDriver driver, long time, TimeUnit timeUnit) {
		try {
			driver.manage().timeouts().implicitlyWait(time, timeUnit);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This function is for clicking element on the web page.
	 * @param driver
	 * @param by
	 */
	public void clickElement(WebDriver driver, By by) {
		try {
			clickableWait(driver, STANDARD_TIME_OUT, by);
			findElement(driver, by).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This function is for sending text to the input field on web page.
	 * @param driver
	 * @param by
	 * @param text
	 */
	public void sendKeys(WebDriver driver, By by, String text) {
		try {
			presenceWait(driver, STANDARD_TIME_OUT, by);
			findElement(driver, by).sendKeys(text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This function is for fetching text.
	 * @param driver
	 * @param by
	 * @return
	 */
	public String fetchText(WebDriver driver, By by) {
		String text = null;
		try {
			text = findElement(driver, by).getText();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	
	/**
	 * This function is for explicit wait until the element is visible.
	 * @param driver
	 * @param timeOutInSeconds
	 * @param by
	 */
	public void visibleWait(WebDriver driver, long timeOutInSeconds, By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This function is for finding a web element.
	 * @param driver
	 * @param by
	 * @return WebElement
	 */
	public WebElement findElement(WebDriver driver, By by) {
		visibleWait(driver, STANDARD_TIME_OUT, by);
		WebElement element = driver.findElement(by);
		return element;
	}

	/**
	 * This function is for finding a list of web elements.
	 * @param driver
	 * @param by
	 * @return List<WebElement>
	 */
	public List<WebElement> findElements(WebDriver driver, By by) {
		List<WebElement> elements = driver.findElements(by);
		return elements;
	}
	
	/**
	 * This function is for explicit wait until the element is present.
	 * @param driver
	 * @param timeOutInSeconds
	 * @param by
	 */
	public void presenceWait(WebDriver driver, long timeOutInSeconds, By by) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	/**
	 * This function is for scrolling the bar to its maximum height.
	 * @param driver
	 */
	public void scrollInfinitely(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	/**
	 * This function is for waiting until the page loads completely.
	 * @param driver
	 */
	public void pageLoadTimeOut(WebDriver driver) {
		driver.manage().timeouts().pageLoadTimeout(STANDARD_TIME_OUT, TimeUnit.SECONDS);
	}

	/**
	 * This function is for confirming steady state of application using Javascript.
	 * @param driver
	 */
	public void steadyState(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("return document.readyState").toString().equals("complete");
	}
	
	/**
	 * This function is for waiting until all elements gets visible.
	 * @param driver
	 * @param timeOutInSeconds
	 * @param by
	 */
	public void visibleAllElementsWait(WebDriver driver, long timeOutInSeconds, By by) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
	}

	/**
	 * This function is for waiting until an element is clickable.
	 * @param driver
	 * @param timeOutInSeconds
	 * @param by
	 */
	public void clickableWait(WebDriver driver, long timeOutInSeconds, By by) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	/**
	 * This function is for scrolling the nav bar until all elements gets loaded.
	 * @param driver
	 * @param by
	 */
	public void scrollDynamicWebPage(WebDriver driver, By by) {
		try {
			Object lastHeight = ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");

			while (true) {
				scrollInfinitely(driver);
				steadyState(driver);
				Thread.sleep(3000);

				Object newHeight = ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
				if (newHeight.equals(lastHeight)) {
					break;
				}
				lastHeight = newHeight;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function is for moving focus to a particular element.
	 * @param driver
	 * @param element
	 */
	public void moveToElement(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/**
	 * This function is used for clicking an element using Javascript.
	 * @param driver
	 * @param element
	 */
	public void clickJs(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	// Methods to select a drop down element
	/**
	 * This method is for simple dropdown selection by visibleText
	 * 
	 * @param driver
	 * @param dropDownID-This    is the unique attribute to find an dropdownelement
	 * @param dropDownValue-This is the text to search in dropdown
	 */
	public static void dropDownSelectionByText(WebDriver driver, By dropDownID, String dropDownValue) {
		try {
			WebElement element = null;
			new WebDriverWait(driver, 5).ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(dropDownID));
			element = driver.findElement(dropDownID);
			Select dropDown = new Select(element);
			dropDown.selectByVisibleText(dropDownValue);
		} catch (StaleElementReferenceException ex) {
			System.out.println("Exception while selecting a value from dropdown" + ex.getMessage());
		}
	}

	/**
	 * This method is for simple dropdown selection by value
	 * 
	 * @param driver
	 * @param dropDownID-This    is the unique attribute to find an dropdownelement
	 * @param dropDownValue-This is the text to search in dropdown
	 */
	public static void dropDownSelectionByValue(WebDriver driver, By dropDownID, String dropDownValue) {
		try {
			WebElement element = null;
			new WebDriverWait(driver, 5).ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(dropDownID));
			element = driver.findElement(dropDownID);
			Select dropDown = new Select(element);
			dropDown.selectByValue(dropDownValue);
		} catch (StaleElementReferenceException ex) {
			System.out.println("Exception while selecting a value from dropdown" + ex.getMessage());
		}
	}

	/**
	 * This method is for simple dropdown selection by index
	 * 
	 * @param driver
	 * @param dropDownID-This    is the unique attribute to find an dropdownelement
	 * @param dropDownValue-This is the text to search in dropdown
	 */
	public static void dropDownSelectionByIndex(WebDriver driver, By dropDownID, int dropDownValue) {
		try {
			WebElement element = null;
			new WebDriverWait(driver, 5).ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(dropDownID));
			element = driver.findElement(dropDownID);
			Select dropDown = new Select(element);
			dropDown.selectByIndex(dropDownValue);
		} catch (StaleElementReferenceException ex) {
			System.out.println("Exception while selecting a value from dropdown" + ex.getMessage());
		}
	}

	/**
	 * This function is for switching to next tab.
	 * 
	 * @param driver
	 * @param tabNumber
	 */
	public void switchToNextTab(WebDriver driver) {
		String currentTab = driver.getWindowHandle();
		for (String tab : driver.getWindowHandles()) {
		    if (!tab.equals(currentTab)) {
		        driver.switchTo().window(tab); 
		    }       
		}
	}

	/**
	 * This function is for closing a new tab.
	 * @param driver
	 */
	public void closeNewTab(WebDriver driver) {
		closeBrowser(driver);
		ArrayList<String> allTabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(allTabs.get(0));
		steadyState(driver);
	}
	
	public void takeScreenshot(WebDriver driver) {
		// Take screenshot and store as a file format after steady state
		steadyState(driver);
		sleep(3000);
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File output = new File("./screenshots/" + "Screenshot" + "_" + System.currentTimeMillis() + ".png");
		try {
			// now copy the screenshot to desired location using copyFile //method
			FileUtils.copyFile(src, output);
			Reporter.log("<a href='"+output.getAbsolutePath()+"'> <img src='"+output.getAbsolutePath()+"' height='500' width='500'/></a> ");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function is for thread to sleep.
	 * @param timeInMilliSeconds
	 */
	public void sleep(long timeInMilliSeconds) {
		try {
			Thread.sleep(timeInMilliSeconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This function is for waiting till loader to disappear.
	 * @param driver
	 * @param locator
	 */
	public void waitForLoaderToDisable(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		while (true) {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			} catch (Exception e) {
				break;
			}
		}
	}
	
	/**
	 * This function is for closing the current browser.
	 * @param driver
	 */
	public void closeBrowser(WebDriver driver) {
		try {
			driver.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This function is for quitting all the opened browsers.
	 * @param driver
	 */
	public void quitBrowser(WebDriver driver) {
		try {
			driver.quit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
