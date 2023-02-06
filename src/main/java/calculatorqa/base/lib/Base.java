package calculatorqa.base.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Base {
	private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();

	protected static void addVerificationFailure(Throwable e) {
		List<Throwable> verificationFailures = getVerificationFailures();
		verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
		verificationFailures.add(e);
	}

	public static void checkVerificationErrors() {
		ITestResult result = Reporter.getCurrentTestResult();
		List<Throwable> verificationFailures = getVerificationFailures();
		// if there are verification failures...
		if (verificationFailures.size() > 0) {
			// set the test to failed
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(new Throwable("Verification errors"));
		}
	}

	public static List<Throwable> getVerificationFailures() {
		List<Throwable> verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<Throwable>() : verificationFailures;
	}

	// fail method to print logs and screenshot

	public void fail(String line) {
		Logs.printFail(line);
		getScreenshot("Failure_Screenshot");
		Assert.fail(line);
	}

	// fail method to print logs and screenshot along with message

	public void fail(String line, Exception e) {
		Logs.printFail(line + " Exception: " + e.getMessage());
		getScreenshot("Failure_Screenshot");
		Assert.fail(line + " Exception: " + e.getMessage());
	}

	public void getScreenshot(String name) {
	}

	public void sleep(int millisec) {
		try {
			Thread.sleep(millisec);
		} catch (Exception e) {
			Logs.printError("Error during Sleep process....");
		}
	}
}
