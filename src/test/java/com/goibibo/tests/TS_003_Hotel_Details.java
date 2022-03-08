package com.goibibo.tests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.goibibo.baseclass.Baseclass;
import com.goibibo.pom.Homepage;
import com.goibibo.pom.Hotelspage;
import com.goibibo.utilities.XLUtility;

public class TS_003_Hotel_Details extends Baseclass{
	Homepage homepage;
	Hotelspage hotelspage;

	public TS_003_Hotel_Details() {
		homepage = new Homepage(driver);
		hotelspage = new Hotelspage(driver);
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
	public void hotelDetails(String city, String checkIn, String checkOut, String rooms, String adults, String children,
			String hotelName) {

		clickElement(driver, getLocator(Locators.xpath, homepage.CLICKHOTEL));
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
		
		presenceWait(driver, 30, hotelspage.locatorHotel(Locators.xpath, hotelName.trim()));
		moveToElement(driver,
				findElement(driver, hotelspage.locatorHotel(Locators.xpath, hotelName.trim())));
		clickableWait(driver, 30, hotelspage.locatorHotel(Locators.xpath, hotelName.trim()));
		clickJs(driver, findElement(driver, hotelspage.locatorHotel(Locators.xpath, hotelName.trim())));

		switchToNextTab(driver);
		steadyState(driver);
		pageLoadTimeOut(driver);

		hotelspage.verifyHotelTitle(hotelName.trim(),
				findElement(driver, getLocator(Locators.xpath, hotelspage.HOTELTITLE)).getText().trim());
		
		// Verify room options
		
		moveToElement(driver, findElement(driver, getLocator(Locators.xpath, hotelspage.ROOMOPTIONS)));
		findElement(driver, getLocator(Locators.xpath, hotelspage.ROOMOPTIONS)).click();
		
		moveToElement(driver, findElement(driver, getLocator(Locators.xpath, hotelspage.IMAGEROOMTYPE)));
		takeScreenshot(driver);
		
		// Verify location
		
		moveToElement(driver, findElement(driver, getLocator(Locators.xpath, hotelspage.LOCATION)));
		clickElement(driver, getLocator(Locators.xpath, hotelspage.LOCATION));
		
		visibleWait(driver, 30, getLocator(Locators.xpath, hotelspage.MAPLOCATION));
		Assert.assertTrue(findElement(driver, getLocator(Locators.xpath, hotelspage.MAPLOCATION)).isDisplayed(), "VERIFY LOCATION");
		
		// Verify guest reviews
		
		moveToElement(driver, findElement(driver, getLocator(Locators.xpath, hotelspage.GUESTREVIEWS)));
		clickElement(driver, getLocator(Locators.xpath, hotelspage.GUESTREVIEWS));
		
		visibleWait(driver, 30, getLocator(Locators.xpath, hotelspage.REVIEW));
		Assert.assertTrue(findElement(driver, getLocator(Locators.xpath, hotelspage.REVIEW)).isDisplayed(), "VERIFY GUEST REVIEW");
		
		// Verify Questions & Answers
		
		moveToElement(driver, findElement(driver, getLocator(Locators.xpath, hotelspage.QUESTIONANSWERS)));
		clickElement(driver, getLocator(Locators.xpath, hotelspage.QUESTIONANSWERS));

		visibleWait(driver, 30, getLocator(Locators.xpath, hotelspage.QUESTIONSECTION));
		Assert.assertTrue(findElement(driver, getLocator(Locators.xpath, hotelspage.QUESTIONSECTION)).isDisplayed(), "VERIFY QUESTIONS & ANSWERS");
		
		// Verify hotel policies
		
		moveToElement(driver, findElement(driver, getLocator(Locators.xpath, hotelspage.HOTELPOLICIES)));
		clickElement(driver, getLocator(Locators.xpath, hotelspage.HOTELPOLICIES));
		
		visibleWait(driver, 30, getLocator(Locators.xpath, hotelspage.POLICIESSECTION));
		Assert.assertTrue(findElement(driver, getLocator(Locators.xpath, hotelspage.POLICIESSECTION)).isDisplayed(), "VERIFY QUESTIONS & ANSWERS");
	
	}
	
	@AfterTest
	public void afterTest() {
		closeNewTab(driver);
		closeBrowser(driver);
	}

	@DataProvider(name = "DataProvider")
	String[][] getData() throws IOException {

		// Read Data from Excel
		String path = System.getProperty("user.dir") + homepage.DATASHEET;
		int rowNum = XLUtility.getRowCount(path, "Sheet3");
		int colNum = XLUtility.getCellCount(path, "Sheet3", rowNum);
		String data[][] = new String[rowNum][colNum];

		for (int i = 1; i <= rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				data[i - 1][j] = XLUtility.getCellData(path, "Sheet3", i, j);
			}
		}

		return data;
	}
}

