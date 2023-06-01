package Utilities;

/*import static org.junit.Assert.assertTrue;*/

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
/*import io.appium.java_client.AppiumDriver;*/
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServer {

	public AppiumDriverLocalService service;
	public AppiumServiceBuilder builder;
	public DesiredCapabilities cap;
	public DesiredCapabilities dc;
	/* AppiumDriver<MobileElement> driver; */
	AndroidDriver<MobileElement> driver;
	File root = new File(System.getProperty("user.dir"));
	File app = new File(root, "/src/test/api/app-qa.apk");
	/* File testLogFile = new File("/home/ninad/eclipse-workspace/log.txt"); */

	public AndroidDriver<MobileElement> startServer() throws Exception {

		/* testLogFile.createNewFile(); */

		// apk Capabilities
		dc = new DesiredCapabilities();
		dc.setCapability("BROWSER_NAME", "Android");
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
		dc.setCapability("platformName", "Android");
		dc.setCapability("deviceName", "nexus5");
		dc.setCapability("app", app.getAbsolutePath());
		dc.setCapability("appPackage", "com.aa.android.qa");
		dc.setCapability("appActivity", "com.aa.android.view.SplashActivity");

		// Appium Capabilities
		cap = new DesiredCapabilities();
		cap.setCapability("noReset", "false");

		// Build the Appium Service
		builder = new AppiumServiceBuilder();
		builder.usingDriverExecutable(new File("/home/ninad/.linuxbrew/bin/node"));
		builder.withAppiumJS(new File("/home/ninad/.linuxbrew/lib/node_modules/appium/build/lib/main.js"));
		builder.withIPAddress("0.0.0.0");
		builder.usingPort(4723);
		/* builder.usingAnyFreePort(); */
		builder.withCapabilities(cap);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");

		// Start the server with the builder
		try {
			/* service = builder.withLogFile(testLogFile).build(); */
			service = AppiumDriverLocalService.buildService(builder);
			service.start();
			/* assertTrue(testLogFile.exists()); */
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		/* System.out.println("Appium URL " + service.getUrl().toString()); */

		/* driver = new AppiumDriver<MobileElement>(service.getUrl(), dc); */
		driver = new AndroidDriver<MobileElement>(service.getUrl(), dc);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		return driver;
	}

	public void stopServer() {
		service.stop();
	}

	public boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}
}
