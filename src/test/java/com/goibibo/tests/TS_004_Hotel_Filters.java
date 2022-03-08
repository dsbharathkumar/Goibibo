package com.goibibo.tests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.goibibo.baseclass.Baseclass;
import com.goibibo.pom.Filterspage;
import com.goibibo.pom.Homepage;
import com.goibibo.pom.Hotelspage;
import com.goibibo.utilities.XLUtility;

public class TS_004_Hotel_Filters extends Baseclass{
	Homepage homepage;
	Hotelspage hotelspage;
	Filterspage filterspage;

	public TS_004_Hotel_Filters() {
		homepage = new Homepage(driver);
		hotelspage = new Hotelspage(driver);
		filterspage = new Filterspage(driver);
	}

	@BeforeTest
	public void beforeTest() {
		getUrl(driver, homepage.BASEURL);
		maximizeWindow(driver);
		implicitWait(driver, 30, TimeUnit.SECONDS);
		pageLoadTimeOut(driver);
		steadyState(driver);
	}

	@Test(dataProvider = "DataProvider")
	public void hotelFilters(String city, String checkIn, String checkOut, String rooms, String adults, String children,
			String hotelName, String popularFilters, String rating, String priceRange) {

		waitForLoaderToDisable(driver, getLocator(Locators.xpath, filterspage.LOADER));
		visibleWait(driver, 30, getLocator(Locators.xpath, homepage.CLICKHOTEL));
		clickJs(driver, findElement(driver, getLocator(Locators.xpath, homepage.CLICKHOTEL)));
		waitForLoaderToDisable(driver, getLocator(Locators.xpath, filterspage.LOADER));
		sendKeys(driver, getLocator(Locators.xpath, homepage.INPUTWHERE), city);
		clickElement(driver, getLocator(Locators.xpath, homepage.AUTOSUGGEST));
		clickElement(driver, getLocator(Locators.xpath, homepage.GUESTSROOMS));

		String noOfRooms = fetchText(driver, getLocator(Locators.xpath, homepage.NOOFROOMS));
		String noOfAdults = fetchText(driver, getLocator(Locators.xpath, homepage.NOOFADULTS));
		String noOfChildren = fetchText(driver, getLocator(Locators.xpath, homepage.NOOFCHILDREN));
		
		// selecting rooms

		homepage.selectGuestsAndRooms(Integer.parseInt(noOfRooms), Integer.parseInt(rooms),
				findElement(driver, getLocator(Locators.xpath, homepage.MINUSROOMS)),
				findElement(driver, getLocator(Locators.xpath, homepage.PLUSROOMS)), "rooms");

		// selecting Adults

		homepage.selectGuestsAndRooms(Integer.parseInt(noOfAdults), Integer.parseInt(adults),
				findElement(driver, getLocator(Locators.xpath, homepage.MINUSADULTS)),
				findElement(driver, getLocator(Locators.xpath, homepage.PLUSADULTS)), "adults");

		// selecting children

		homepage.selectGuestsAndRooms(Integer.parseInt(noOfChildren), Integer.parseInt(children),
				findElement(driver, getLocator(Locators.xpath, homepage.MINUSCHILDREN)),
				findElement(driver, getLocator(Locators.xpath, homepage.PLUSCHILDREN)), "children");

		clickElement(driver, getLocator(Locators.xpath, homepage.BUTTONDONE));
		clickElement(driver, getLocator(Locators.xpath, homepage.SEARCHHOTELS));
		
		scrollDynamicWebPage(driver, getLocator(Locators.xpath, homepage.HOTELNAMES));
		
		homepage.fetchAllHotels(driver, getLocator(Locators.xpath, homepage.HOTELNAMES));
		
		// check popular filter
		
		pageLoadTimeOut(driver);
		steadyState(driver);
		moveToElement(driver, popularFilter(popularFilters.trim()));
		clickJs(driver, popularFilter(popularFilters));
		waitForLoaderToDisable(driver, getLocator(Locators.xpath, filterspage.LOADER));
		
		// price range
		
		priceRangeFilter(priceRange.trim());
		waitForLoaderToDisable(driver, getLocator(Locators.xpath, filterspage.LOADER));
		
		// rating
		
		selectCustomerRatings(rating);
		waitForLoaderToDisable(driver, getLocator(Locators.xpath, filterspage.LOADER));
		takeScreenshot(driver);
	}

	/**
	 * This function if for selecting popular filter check-box.
	 * @param popularFilter
	 * @return WebElement
	 */
	public WebElement popularFilter(String popularFilter) {
		WebElement element = findElement(driver, By.xpath("//span[text()='"+popularFilter+"']"));
		return element;
	}
	
	/**
	 * This function is for selecting price range filter check-box.
	 * @param price
	 */
	public void priceRangeFilter(String price) {
		int rate = Integer.parseInt(price);
		if(rate > 0 && rate < 2001) {
			findElement(driver, getLocator(Locators.xpath, filterspage.UPTO2000)).click();
		} else if(rate > 2000 && rate < 4001) {
			findElement(driver, getLocator(Locators.xpath, filterspage.UPTO4000)).click();
		} else if(rate > 4000 && rate < 6001) {
			findElement(driver, getLocator(Locators.xpath, filterspage.UPTO6000)).click();
		} else if(rate > 6000 && rate < 8001) {
			findElement(driver, getLocator(Locators.xpath, filterspage.UPTO6000)).click();
		} else if(rate > 8000) {
			findElement(driver, getLocator(Locators.xpath, filterspage.ABOVE8000)).click();
		} else {
			Reporter.log("Provided price is Invalid "+price, true);
		}
	}
	
	/**
	 * This function is for selecting customer ratings check-box.
	 * @param rating
	 */
	public void selectCustomerRatings(String rating) {
		if(rating.trim().equals("4.5")) {
			findElement(driver, getLocator(Locators.xpath, filterspage.RATING45)).click();
		} else if(rating.trim().equals("4")) {
			findElement(driver, getLocator(Locators.xpath, filterspage.RATING4)).click();
		} else if(rating.trim().equals("3.5")) {
			findElement(driver, getLocator(Locators.xpath, filterspage.RATING35)).click();
		} else if(rating.trim().equals("3")) {
			findElement(driver, getLocator(Locators.xpath, filterspage.RATING3)).click();
		} else {
			Reporter.log("Customer rating provided as Invalid "+rating, true);
		}
	}
	
	@AfterTest
	public void afterTest() {
		closeBrowser(driver);
	}

	@DataProvider(name = "DataProvider")
	String[][] getData() throws IOException {

		// Read Data from Excel
		String path = System.getProperty("user.dir") + "\\src\\test\\resources\\External_Data\\Sample-excel.xls";
		int rowNum = XLUtility.getRowCount(path, "Sheet4");
		int colNum = XLUtility.getCellCount(path, "Sheet4", rowNum);
		String data[][] = new String[rowNum][colNum];

		for (int i = 1; i <= rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				data[i - 1][j] = XLUtility.getCellData(path, "Sheet4", i, j);
			}
		}

		return data;
	}

}
