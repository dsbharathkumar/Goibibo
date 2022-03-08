package com.goibibo.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.goibibo.library.DataHandlers;

public class Homepage extends DataHandlers{
	WebDriver driver;
	public Homepage(WebDriver driver) {
		this.driver = driver;
	}

	private static final String FILENAME = "object_repository/objectProperties.properties";
	private static final String CONFIGNAME = "object_repository/config.properties";
	
	// Getting config properties
	public final String BASEURL = getProperty(CONFIGNAME, "baseUrl");
	public final String DATASHEET = getProperty(CONFIGNAME, "dataSheet");
	
	// Getting object properties
	public final String CLICKHOTEL = getProperty(FILENAME, "linkHotels");
	public final String INPUTWHERE = getProperty(FILENAME, "inputWhere");
	public final String GUESTSROOMS = getProperty(FILENAME, "clickGuestAndRooms");
	public final String NOOFROOMS = getProperty(FILENAME, "noOfRooms");
	public final String NOOFADULTS = getProperty(FILENAME, "noOfAdults");
	public final String NOOFCHILDREN = getProperty(FILENAME, "noOfChildren");
	public final String AUTOSUGGEST = getProperty(FILENAME, "autoSuggest");
	public final String PLUSROOMS = getProperty(FILENAME, "plusRooms");
	public final String MINUSROOMS = getProperty(FILENAME, "minusRooms");
	public final String PLUSADULTS = getProperty(FILENAME, "plusAdults");
	public final String MINUSADULTS = getProperty(FILENAME, "minusAdults");
	public final String PLUSCHILDREN = getProperty(FILENAME, "plusChildren");
	public final String MINUSCHILDREN = getProperty(FILENAME, "minusChildren");
	public final String BUTTONDONE = getProperty(FILENAME, "buttonDone");
	public final String SEARCHHOTELS = getProperty(FILENAME, "buttonSearchHotels");
	public final String HOTELNAMES = getProperty(FILENAME, "allHotelNames");
	
	// Re-usable methods
	
	/**
	 * This function is for selecting guests and rooms.
	 * @param existing
	 * @param required
	 * @param minusButton
	 * @param plusButton
	 * @param section
	 */
	public void selectGuestsAndRooms(int existing, int required, WebElement minusButton, WebElement plusButton, String section) {
		if (required > existing) {
			for (int i = 0; i < (required - existing); i++) {
				plusButton.click();
			}
			Reporter.log("Plus button at " +section+" section is clicked "+ (required-existing) +" times");
		} else if (required < existing) {
			for (int i = 0; i < (existing - required); i++) {
				minusButton.click();
			}
			Reporter.log("Minus button at " +section+" section is clicked "+ (existing-required) +" times");
		} else {
			Reporter.log("Existing rooms " + existing + "and Required rooms " + required + " are same.");
		}
	}
	
	/**
	 * This function is for fetching all hotel names.
	 * @param locator
	 */
	public void fetchAllHotels(WebDriver driver, By locator) {
		List<WebElement> elements = findElements(driver, locator);
		Reporter.log("The Total Number of Hotels are "+elements.size(), true);
		Reporter.log("Please find the Hotel names below:  ", true);
		int i = 1;
		for(WebElement element: elements) {
			Reporter.log(i+"."+element.getText(), true);
			i++;
		}
	}
}
	
