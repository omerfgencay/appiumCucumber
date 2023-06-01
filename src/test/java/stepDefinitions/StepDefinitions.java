package stepDefinitions;

import org.openqa.selenium.By;
import Utilities.AppiumServer;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
/*import io.appium.java_client.AppiumDriver;*/
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.MobileElement;

public class StepDefinitions {

	AppiumServer appiumServer = new AppiumServer();
	/* AppiumDriver<MobileElement> driver; */
	AndroidDriver<MobileElement> driver;

	@Before("@setup")
	public void setUp() throws Throwable {

		int port = 4723;
		try {
			if (!appiumServer.checkIfServerIsRunnning(port)) {
				appiumServer.startServer();
				appiumServer.stopServer();
			} else {
				System.out.println("Appium Server already running on Port - " + port);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		driver = appiumServer.startServer();
	}

	@After("@destroy")
	public void tearDown() throws Throwable {
		driver.close();
		appiumServer.stopServer();
	}

	@Given("^I log into the app$")
	public void logIn() throws Throwable {
		driver.findElement(By.id("com.aa.android.qa:id/username")).setValue("5X78V88");
		driver.findElement(By.id("com.aa.android.qa:id/lastname")).setValue("Points");
		driver.findElement(By.id("com.aa.android.qa:id/password")).setValue("testing");
		driver.findElement(By.id("com.aa.android.qa:id/login_button")).click();
	}
}
