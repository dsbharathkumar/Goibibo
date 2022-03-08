package com.goibibo.tests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.goibibo.baseclass.Baseclass;
import com.goibibo.pom.Homepage;
import com.goibibo.utilities.XLUtility;

@Listeners(com.goibibo.utilities.Listeners.class)
public class TS_001_Hotel_Search extends Baseclass {
	
	Homepage homepage;
	
	public TS_001_Hotel_Search() {
		homepage = new Homepage(driver);
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
	public void hotelSearch(String city, String checkIn, String checkOut, String rooms, String adults, String children) {
		
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
	}
	
	@AfterTest
	public void afterTest() {
		closeBrowser(driver);
	}
	
	 @DataProvider(name="DataProvider")
		String [][] getData() throws IOException{
			
			// Read Data from Excel
			String path = System.getProperty("user.dir")+ homepage.DATASHEET;
			int rowNum = XLUtility.getRowCount(path, "Sheet1");
			int colNum = XLUtility.getCellCount(path, "Sheet1", rowNum);
			String data[][] = new String[rowNum][colNum];
			
			for(int i =1; i <= rowNum ; i++) {
				for(int j=0; j < colNum ; j++) {
					data[i-1][j] =XLUtility.getCellData(path, "Sheet1", i, j);
				}
			}
			
			return data;
		}
}
