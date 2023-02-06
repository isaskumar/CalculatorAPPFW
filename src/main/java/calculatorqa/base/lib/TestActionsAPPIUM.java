package calculatorqa.base.lib;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TestActionsAPPIUM extends Base {
	private static int counter = 0;
	protected String aFilePath;
	// #Drives -----
	protected AppiumDriver<MobileElement> appiumD = null;
	protected String aScreenshotPath;
	// # Elements -----
	protected ITestContext c;
	// #Drives -----
	// # Elements -----
	protected MobileElement mobileEL = null;
	protected Properties p;
	protected int timeoutDefault = 30;

	/// ## -------- constructors----------##//
	public TestActionsAPPIUM(AppiumDriver<MobileElement> appiumD, ITestContext c, String propFile) throws IOException {
		super();
		this.appiumD = appiumD;
		this.c = c;
		String outDirStr = c.getOutputDirectory();
		createDir(outDirStr);
		aScreenshotPath = c.getOutputDirectory() + "/../html/" + Logs.relativeScreenshotPath;
		createDir(aScreenshotPath);
		aFilePath = c.getOutputDirectory() + "/../html/" + Logs.relativeFilesPath;
		createDir(aFilePath);
		p = new Properties();
		p.load(this.getClass().getResourceAsStream(propFile));
	}

	/// ## -------- constructors----------##//
	private void createDir(String DirPath) {
		File outDir = new File(DirPath);
		File dir = outDir.getParentFile();
		if (!dir.exists()) {
			Logs.debug(dir.getAbsolutePath() + " does not exist -> create directory...");
			dir.mkdirs();
		}
		if (!outDir.exists()) {
			Logs.debug(DirPath + " does not exist -> create directory...");
			outDir.mkdir();
		}
	}

	// ### -----GET DRIVER
	public AppiumDriver<MobileElement> getAppiumDriver() {
		return appiumD;
	}

	public Object getAttribute(String nomeAttribute) {
		return c.getAttribute(nomeAttribute);
	}

	private String getCounter() {
		counter++;
		if (counter < 10) {
			return "0" + counter;
		} else {
			return String.valueOf(counter);
		}
	}

	// ####-------------- GET OBJECT ---------------------------
	private MobileElement getObjectByXPath(String path, String el) {
		MobileElement me = null;
		try {
			me = appiumD.findElement(By.xpath(path));
		} catch (TimeoutException e) {
			fail("LOCATE THE OBJECT FAILED (getObjectByXPath): " + el + "->" + path);
		}
		return me;
	}

	// ####-------------- GET OBJECT FINISH ---------------------------
	// ####-------------- TEST ACTIONS START---------------------------

	public Object getProperty(String key) {
		String val = null;
		try {
			val = p.getProperty(key);
		} catch (Exception e) {
			val = p.getProperty(key);
		}
		if (val == null) {
			val = p.getProperty(key);
		}
		if (val == null) {
			Logs.printFail("ERROR --> key: " + key + " - value: " + val);
		}
		return val;
	}

	public List<MobileElement> listofElements(String locate) {
		return appiumD.findElements(By.className(locate));
	}

	public MobileElement findElementAll(String el) throws Exception {
		String locate = p.getProperty(el);
		if (locate == null) {
			locate = p.getProperty(el);
		}

		try {
			return appiumD.findElement(By.cssSelector(locate));
		} catch (WebDriverException e) {
		}
		try {
			return appiumD.findElement(By.xpath(locate));
		} catch (WebDriverException e) {
		}
		try {
			return (MobileElement) appiumD.findElements(By.xpath(locate));
		} catch (WebDriverException e) {
		}
		try {
			return appiumD.findElement(By.className(locate));
		} catch (WebDriverException e) {
		}
		try {
			return appiumD.findElement(By.id(locate));
		} catch (WebDriverException e) {
		}
		try {
			return appiumD.findElement(By.linkText(locate));
		} catch (WebDriverException e) {
		}
		try {
			return appiumD.findElement(By.tagName(locate));
		} catch (WebDriverException e) {
		}
		try {
			return appiumD.findElement(By.partialLinkText(locate));
		} catch (WebDriverException e) {
		}
		throw new Exception("Element Cannot Be Located");
	}

	public void click(String el) {
		String path = "";
		try {
			path = p.getProperty(el);
		} catch (Exception e) {
			Logs.printFail("It's not possible to find the element..");
		}

		MobileElement me = null;
		try {
			me = findElementAll(el);
			try {
				me.click();
			} catch (WebDriverException e) {
				String msg = e.getMessage();
				Logs.printError(msg);
				if (msg.contains("not clickable")) {

					Logs.printACT("CLICK", el + " (" + path + ")  is NOT clickable -> scroll the page");
					me.click();
					sleep(500);
				} else {

					fail("ERROR --> CLICK :" + el + "(path = " + path + "): " + e.toString());
					throw e;
				}
			}
			Logs.printACT("CLICK", el + " (" + path + ")");
		} catch (Exception e) {
			fail("ERROR -> CLICK " + el + " (path: " + path + ")", e);
		}
	}

	public boolean storeVisibility(String el, int timeout) {
		String xpath = "";
		try {
			xpath = p.getProperty(el);
		} catch (Exception e) {
			Logs.printFail("Element is not selectable!");
		}

		boolean ret = false;
		try {
			WebDriverWait wait = new WebDriverWait(appiumD, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			Logs.printACT("STORE VISIBILITY", el + " (" + xpath + ")" + " is visible");
			ret = true;
		} catch (TimeoutException e) {
			Logs.printACT("STORE VISIBILITY", el + " (" + xpath + ")" + " is NOT visible");
			ret = false;
		}
		return ret;
	}

	public boolean verifyVisibility(String el, boolean visibility, boolean enableTestVerification) {
		String xpath = (String) getProperty(el);
		WebElement we = null;
		boolean ret = false;
		boolean visible = false;
		boolean fail = false;
		StringBuilder failMessage = new StringBuilder();
		try {
			we = getObjectByXPath(xpath, el);
			if (we.isDisplayed() && we.isEnabled()) {
				visible = true;
			}
		} catch (TimeoutException e) {
		} catch (Exception e) {
			if (enableTestVerification) {
				addVerificationFailure(e);
				fail("Error During Verify Visibility of Element " + el + "(xpath = " + xpath + ")", e);
			} else {
				fail = true;
				failMessage.append(e.toString());
			}
		}
		if (visibility) {
			if (visible) {
				if (enableTestVerification) {
					Logs.printCHK(true, "Element " + el + "(xpath = " + xpath + ") is visible");
				} else {
					Logs.printACT("CHECK VISIBILITY", "Element " + el + "(xpath = " + xpath + ") is visible");
				}
				ret = true;
			} else {
				if (enableTestVerification) {
					Logs.printCHK(false, "Element " + el + "(xpath = " + xpath + ") is NOT visible");
					addVerificationFailure(new Throwable("Element " + el + "(xpath = " + xpath + ") is NOT visible"));
				} else if (fail) {
					Logs.printACT("CHECK VISIBILITY", "Error during verify Visibility of element " + el + "(xpath = "
							+ xpath + "): " + failMessage.toString());
				} else {
					Logs.printACT("CHECK VISIBILITY", "Element " + el + "(xpath = " + xpath + ") is NOT visible");
				}
				ret = false;
			}
		} else {
			if (visible) {
				if (enableTestVerification) {
					Logs.printCHK(false, "Element " + el + "(xpath = " + xpath + ") is visible");
					addVerificationFailure(new Throwable("Element " + el + "(xpath = " + xpath + ") is visible"));
				} else {
					Logs.printACT("CHECK VISIBILITY", "Element " + el + "(xpath = " + xpath + ") is visible");
				}
				ret = false;
			} else {
				if (enableTestVerification) {
					Logs.printCHK(true, "Element " + el + "(xpath = " + xpath + ") is NOT visible");
				} else if (fail) {
					Logs.printACT("CHECK VISIBILITY", "Error during verify Visibility of element " + el + "(xpath = "
							+ xpath + "): " + failMessage.toString());
				} else {
					Logs.printACT("CHECK VISIBILITY", "Element " + el + "(xpath = " + xpath + ") is NOT visible");
				}
				ret = true;
			}
		}
		return ret;
	}

	public boolean verifyVisibility(String el, boolean visibility) {
		return this.verifyVisibility(el, visibility, true);
	}

	// ####-------------- TEST ACTIONS END ---------------------------
	// Screenshot methods
	@Override
	public void getScreenshot(String screenshotName) {
		String fullScreenshotName = getCounter() + "_" + screenshotName.trim() + ".gif";
		String name = aScreenshotPath + fullScreenshotName;
		File screen = screenshot(name);
		if (screen != null) {
			Logs.printScreenshot(screenshotName, fullScreenshotName);
		}
	}

	public String getTestName() {
		return c.getName();
	}

	public void printConfiguration() {
		String platform = getAttribute("PLATFORM_NAME").toString();
		String platformVersion = getAttribute("PLATFORM_VERSION").toString();
		String deviceName = (String) getAttribute("DEVICE_NAME");
		switch (platform) {
		case Constants.NATIVE_APP:
			Logs.print(" PLATFORM: " + platform + " " + platformVersion + " - DEVICE: " + deviceName);
			break;
		case Constants.IOS_NATIVE:
			Logs.print(" PLATFORM: " + platform + " " + platformVersion + " - DEVICE: " + deviceName);
			break;
		default:
			Logs.print(" PLATFORM: " + platform + " Version:" + platformVersion);
			break;
		}
	}

	private File screenshot(String name) {
		System.out.println("Taking screenshot...");
		File scrFile = null;
		if (appiumD != null)
			scrFile = ((TakesScreenshot) appiumD).getScreenshotAs(OutputType.FILE);
		try {
			File testScreenshot = new File(name);
			FileUtils.copyFile(scrFile, testScreenshot);
			Logs.print("Screenshot stored to " + aScreenshotPath);
			return testScreenshot;
		} catch (IOException e) {
			Logs.printWarning("UNABLE TO TAKE SCREENSHOT: " + e.getMessage());
		}
		return null;
	}
}