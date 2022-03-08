package com.goibibo.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.goibibo.library.DataHandlers;

public class Paymentpage extends DataHandlers {

	WebDriver driver;
	public Paymentpage(WebDriver driver) {
		this.driver = driver;
	}

	private static final String FILENAME = "object_repository/objectProperties.properties";
	
	// Getting object properties
	public final String CARDNUMBER = getProperty(FILENAME, "inputCardNumber");
	public final String CARDNAME = getProperty(FILENAME, "inputCardName");
	public final String CARDEXPIRYDATE = getProperty(FILENAME, "inputExpiryDate");
	public final String CVV = getProperty(FILENAME, "inputCvv");
	public final String BUTTONPAY = getProperty(FILENAME, "buttonPayCreditCard");
	public final String VALIDATIONERROR = getProperty(FILENAME, "validationError");
	
	// Re-usable methods
	/**
	 * This function is for validating payment details.
	 * @param driver
	 * @param by
	 * @return List<String>
	 */
	public List<String> validatePaymentDetails(WebDriver driver, By by) {
		List<WebElement> validationErrors = findElements(driver, by);
		List<String> errorText = new ArrayList<String>();
		try {
			for(WebElement error: validationErrors) {
				if(error.getText() != null && error.getText() != "" && !error.getText().isEmpty()) {
					errorText.add(error.getText());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorText;
	}
}
