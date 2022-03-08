package com.goibibo.tests;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.goibibo.baseclass.Baseclass;
import com.goibibo.pom.Homepage;
import com.goibibo.pom.Hotelspage;
import com.goibibo.pom.Paymentpage;
import com.goibibo.utilities.XLUtility;

public class TS_002_Hotel_Booking extends Baseclass {
	Homepage homepage;
	Hotelspage hotelspage;
	Paymentpage paymentpage;

	public TS_002_Hotel_Booking() {
		homepage = new Homepage(driver);
		hotelspage = new Hotelspage(driver);
		paymentpage = new Paymentpage(driver);
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
	public void hotelBooking(String city, String checkIn, String checkOut, String rooms, String adults, String children,
			String hotelName, String guestTitle, String guestFirstName, String guestLastName, String emailAddress,
			String countryCodeValue, String mobileNumber, String cardNumber, String cardName, String expiryDate, String cvv) {

		clickElement(driver, getLocator(Locators.xpath, homepage.CLICKHOTEL));
		clickElement(driver, getLocator(Locators.xpath, homepage.INPUTWHERE));
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

		// Verifying hotel title
		
		hotelspage.verifyHotelTitle(hotelName.trim(),
				findElement(driver, getLocator(Locators.xpath, hotelspage.HOTELTITLE)).getText().trim());
		
		clickElement(driver, getLocator(Locators.xpath, hotelspage.ROOMOPTIONS));
		presenceWait(driver, 30, getLocator(Locators.xpath, hotelspage.SELECTROOM));
		moveToElement(driver, findElement(driver, getLocator(Locators.xpath, hotelspage.SELECTROOM)));
		clickJs(driver, findElement(driver, getLocator(Locators.xpath, hotelspage.SELECTROOM)));

		steadyState(driver);
		
		// Guest titles
		
		dropDownSelectionByText(driver, getLocator(Locators.xpath, hotelspage.SELECTTITLE), guestTitle);
		
		sendKeys(driver, getLocator(Locators.xpath, hotelspage.FIRSTNAME), guestFirstName);
		sendKeys(driver, getLocator(Locators.xpath, hotelspage.LASTNAME), guestLastName);
		sendKeys(driver, getLocator(Locators.xpath, hotelspage.EMAILADDRESS), emailAddress);
		
		dropDownSelectionByValue(driver, getLocator(Locators.xpath, hotelspage.COUNTRYCODE), countryCodeValue);
		sendKeys(driver, getLocator(Locators.xpath, hotelspage.MOBILENUMBER), mobileNumber);
		
		moveToElement(driver, findElement(driver, getLocator(Locators.xpath, hotelspage.PROCEEDTOPAYMENT)));
		clickElement(driver, getLocator(Locators.xpath, hotelspage.PROCEEDTOPAYMENT));
		
		// Payment details
		
		sendKeys(driver, getLocator(Locators.cssSelector, paymentpage.CARDNUMBER), cardNumber);
		sendKeys(driver, getLocator(Locators.xpath, paymentpage.CARDNAME), cardName);
		sendKeys(driver, getLocator(Locators.xpath, paymentpage.CARDEXPIRYDATE), expiryDate);
		sendKeys(driver, getLocator(Locators.xpath, paymentpage.CVV), cvv);
		
		moveToElement(driver, findElement(driver, getLocator(Locators.xpath, paymentpage.BUTTONPAY)));
		clickElement(driver, getLocator(Locators.xpath, paymentpage.BUTTONPAY));
		
		List<String> errors = paymentpage.validatePaymentDetails(driver, getLocator(Locators.xpath, paymentpage.VALIDATIONERROR));
		Assert.assertFalse(errors.size() > 0, "Valdiation errors are "	+errors);
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
		int rowNum = XLUtility.getRowCount(path, "Sheet2");
		int colNum = XLUtility.getCellCount(path, "Sheet2", rowNum);
		String data[][] = new String[rowNum][colNum];

		for (int i = 1; i <= rowNum; i++) {
			for (int j = 0; j < colNum; j++) {
				data[i - 1][j] = XLUtility.getCellData(path, "Sheet2", i, j);
			}
		}

		return data;
	}
}
