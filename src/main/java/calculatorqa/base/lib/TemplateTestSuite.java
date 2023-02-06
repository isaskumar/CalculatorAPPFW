package calculatorqa.base.lib;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class TemplateTestSuite extends Base {
	protected static ThreadLocal<AppiumDriver<MobileElement>> appdr = new ThreadLocal<AppiumDriver<MobileElement>>();
	protected static ThreadLocal<IOSDriver<IOSElement>> iosdr = new ThreadLocal<IOSDriver<IOSElement>>();
	// #Drives -----
	protected AppiumDriver<MobileElement> appiumD;
	protected ITestContext c;
	protected Map<String, String> envvar;
	protected IOSDriver<IOSElement> iosD;
	protected Process p;
	// #Drives -----
	protected TestActionsAPPIUM TestActionsAPPIUM;

	public TemplateTestSuite() {
	}

	// Runs BEFORE ALL test and Runs only one..
	@BeforeTest(alwaysRun = true)
	@Parameters({ "executionEnvironment", "platform", "device", "udid" })

	public void before(@Optional String executionEnvironment, @Optional String platform, @Optional String device,
			ITestContext c, @Optional String udid) {
		this.c = c;
		envvar = System.getenv();
		if (executionEnvironment == null) {
			executionEnvironment = envvar.get("EXECUTION_ENVIRONMENT"); // .toString();
		}
		if (platform == null) {
			platform = envvar.get("PLATFORM");
		}
		if (device == null) {
			device = envvar.get("DEVICE");
		}
		if (udid == null) {
			udid = envvar.get("UDID");
		}
	}

	// --------
	// Runs AFTER any single test
	@AfterMethod(alwaysRun = true)

	public void close() {
		// RPINO - Quit driver after test
		if (getAppiumdDriver() != null)
			getAppiumdDriver().quit();
		else if (getIOSDriver() != null) {
			System.out.println("\r\n" + "IOS Driver");
		} else
			fail("Error during Driver initialize Cannot Close Driver, Driver not supported !!!");
	}

	// RPINO -added afterTest method to close the connection at the end of all tests
	@AfterTest

	public void closeTest() {
		System.out.println("RPINO-CloseTest");
	}

	// RPINO Get Drivers
	public AppiumDriver<MobileElement> getAppiumdDriver() {
		return appdr.get();
	}

	public IOSDriver<IOSElement> getIOSDriver() {
		return iosdr.get();
	}

	public void postset() {
		checkVerificationErrors();
		Logs.close();
	}

	@AfterSuite

	public void postsuite() {
	}

	public void preset() {
		Logs.start();
		if (getAppiumdDriver() != null)
			TestActionsAPPIUM.printConfiguration();
		else
			System.out.println();
	}

	//Set Drivers
	// ##--------APPIUM
	public void setAppiumDriver(AppiumDriver<MobileElement> driver) {
		appdr.set(driver);
	}

	// ##-------- IOS
	public void setIOSDriver(IOSDriver<IOSElement> driver) {
		iosdr.set(driver);
	}

	// Runs BEFORE any test
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "executionEnvironment", "platform", "device", "version", "serverUrl", "propFile", "udid", "appPath",
			"apkName" })

	public void setup(@Optional String executionEnvironment, @Optional String platform, @Optional String device,
			@Optional String version, @Optional String serverUrl, @Optional String propFile, @Optional String udid,
			Method method, @Optional String appPath, @Optional String apkName) throws IOException {
		String testName = method.getName();
		String compressedDate = (new SimpleDateFormat("ddMMyyyyHHmmss")).format(new Date());
		String testId = testName.replace(' ', '_') + "_" + compressedDate;
		MutableCapabilities cap = null;
		Capabilities caps = null;
		envvar = System.getenv();
		if (platform == null) {
			platform = envvar.get("PLATFORM"); // the Operating System
		}
		// String startURL = envvar.get("START_URL"); // the Start Url
		if (executionEnvironment == null) {
			executionEnvironment = envvar.get("EXECUTION_ENVIRONMENT");
		}
		if (device == null) {
			device = envvar.get("DEVICE");
		}
		if (version == null) {
			version = envvar.get("VERSION");
		}
		if (serverUrl == null) {
			serverUrl = envvar.get("SERVER_URL");
		}
		if (propFile == null) {
			propFile = envvar.get("PROP_FILE");
		}
		if (udid == null) {
			udid = envvar.get("UDID");
		}

		switch (executionEnvironment) {
		case Constants.REMOTE_ENVIRONMENT:
			switch (platform) {
			case Constants.NATIVE_APP:
				cap = new MutableCapabilities();
				cap.setCapability("deviceName", device);
				cap.setCapability("platformName", "Android");
				cap.setCapability("uiautomator2ServerInstallTimeout", 10000);
				cap.setCapability("appPackage", "felipecastrosales.calculator");
				cap.setCapability("appActivity", "felipecastrosales.calculator.MainActivity");
				// SERVER
				appiumD = new AppiumDriver<MobileElement>(new URL(serverUrl + "/wd/hub"), cap);
				caps = appiumD.getCapabilities();
				c.setAttribute("PLATFORM_NAME", cap.getCapability("platformName"));
				c.setAttribute("PLATFORM_VERSION", caps.getCapability("platformVersion"));
				c.setAttribute("BROWSER_NAME", caps.getBrowserName());
				c.setAttribute("DEVICE_NAME", caps.getCapability("deviceName"));
				break;
			case Constants.IOS_NATIVE:
				break;
			default:
				fail("platform " + platform + " not supported");
				break;
			}
			break;
		default:
			fail("ExecutionEnvironment " + executionEnvironment + " not supported");
			break;
		}
		if (appiumD != null)
			setAppiumDriver(appiumD);
		else if (iosD != null)
			setIOSDriver(iosD);
		else
			fail("Error during setDriver operation.....!");
		c.setAttribute("ENVIRONMENT", executionEnvironment);
		if (appiumD != null) {
			TestActionsAPPIUM = new TestActionsAPPIUM(getAppiumdDriver(), c, propFile);
		} else if (iosD != null) {
		} else {
			fail("Driver is not initialized, please check your driver used for this suite");
		}
	}
}
