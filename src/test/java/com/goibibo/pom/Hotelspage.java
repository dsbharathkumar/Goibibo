package com.goibibo.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.goibibo.library.DataHandlers;

public class Hotelspage extends DataHandlers{

	WebDriver driver;
	public Hotelspage(WebDriver driver) {
		this.driver = driver;
	}

	private static final String FILENAME = "object_repository/objectProperties.properties";
	
	// Getting object properties
	public final String HOTELTITLE = getProperty(FILENAME, "verifyHotelTitle");
	public final String SELECTROOM = getProperty(FILENAME, "buttonSelectRoom");
	public final String PROCEEDTOPAYMENT = getProperty(FILENAME, "buttonProceedPayment");
	public final String ROOMOPTIONS = getProperty(FILENAME, "tabRoomOptions");
	public final String SELECTTITLE = getProperty(FILENAME, "selectTitle");
	public final String FIRSTNAME = getProperty(FILENAME, "inputFirstName");
	public final String LASTNAME = getProperty(FILENAME, "inputLastName");
	public final String EMAILADDRESS = getProperty(FILENAME, "inputEmailAddress");
	public final String COUNTRYCODE = getProperty(FILENAME, "selectCountryCode");
	public final String MOBILENUMBER = getProperty(FILENAME, "inputphoneNumber");
	public final String AMENITIES = getProperty(FILENAME, "tabAmenities");
	public final String GUESTREVIEWS = getProperty(FILENAME, "tabGuestReviews");
	public final String HOTELPOLICIES = getProperty(FILENAME, "tabHotelPolicies");
	public final String LOCATION = getProperty(FILENAME, "tabLocation");
	public final String ABOUTPROPERTY = getProperty(FILENAME, "tabAboutProperty");
	public final String SIMILARHOTELS = getProperty(FILENAME, "tabSimilarHotels");
	public final String QUESTIONANSWERS = getProperty(FILENAME, "tabQuestionsAnswers");
	public final String IMAGEROOMTYPE = getProperty(FILENAME, "imageRoomType");
	public final String MAPLOCATION = getProperty(FILENAME, "mapLocation");
	public final String REVIEW = getProperty(FILENAME, "reviewByUser");
	public final String QUESTIONSECTION = getProperty(FILENAME, "questionsSection");
	public final String POLICIESSECTION = getProperty(FILENAME, "policiesSection");
	
	// Re-usable methods
	
	/**
	 * This function is for verifying the hotel name.
	 * @param expectedTitle
	 * @param actualTitle
	 */
	public void verifyHotelTitle(String expectedTitle, String actualTitle) {
		Assert.assertEquals(expectedTitle, actualTitle);
	}
	
	/**
	 * This function for fetching locator using hotel name.
	 * @param hotel
	 * @return
	 */
	public By locatorHotel(Locators locators, String hotel) {
		By location = getLocator(locators,"//h4[@itemprop='name'][contains(text(),'"+hotel+"')]");
		return location;
	}
}
