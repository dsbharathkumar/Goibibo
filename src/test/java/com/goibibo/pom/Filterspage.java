package com.goibibo.pom;

import org.openqa.selenium.WebDriver;

import com.goibibo.library.DataHandlers;

public class Filterspage extends DataHandlers {
	WebDriver driver;

	public Filterspage(WebDriver driver) {
		this.driver = driver;
	}

	private static final String FILENAME = "object_repository/objectProperties.properties";

	// Getting object properties
	public final String UPTO2000 = getProperty(FILENAME, "checkUpto2000");
	public final String UPTO4000 = getProperty(FILENAME, "checkUpto4000");
	public final String UPTO6000 = getProperty(FILENAME, "checkUpto6000");
	public final String UPTO8000 = getProperty(FILENAME, "checkUpto8000");
	public final String ABOVE8000 = getProperty(FILENAME, "checkAbove8000");
	
	public final String RATING45 = getProperty(FILENAME, "rating4.5");
	public final String RATING4 = getProperty(FILENAME, "rating4");
	public final String RATING35 = getProperty(FILENAME, "rating3.5");
	public final String RATING3 = getProperty(FILENAME, "rating3");
	
	public final String CANCELLATIONAVAILABLE = getProperty(FILENAME, "checkCancellationAvailable");
	public final String PAYLATER = getProperty(FILENAME, "checkPayLater");
	public final String PAYHOTEL = getProperty(FILENAME, "checkPayAtHotel");
	public final String BREAKFAST = getProperty(FILENAME, "checkFreeBreakfast");
	public final String LOADER = getProperty(FILENAME, "loader");
}
