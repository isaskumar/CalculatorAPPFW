package calulator.qa.test;

import org.testng.annotations.Test;

import calculator.qa.common.pages.SmartMobileAccess;
import calculatorqa.base.lib.TemplateTestSuite;

public class CalculatorHomePageTest extends TemplateTestSuite {

	@Test
	public void BTC_001_Check_addition_for_two_numbers() {
		preset();
		try {
			SmartMobileAccess smartMobile = new SmartMobileAccess(TestActionsAPPIUM);
			smartMobile.MTC_001_Sum_of_two_Numbers();
		} catch (Exception e) {
			TestActionsAPPIUM.fail(e.toString());
		} finally {
			postset();
		}
	}

	@Test
	public void BTC_002_Check_subtraction_for_two_numbers() {
		preset();
		try {
			SmartMobileAccess smartMobile = new SmartMobileAccess(TestActionsAPPIUM);
			smartMobile.MTC_001_subtraction_of_two_Numbers();
		} catch (Exception e) {
			TestActionsAPPIUM.fail(e.toString());
		} finally {
			postset();
		}
	}

	@Test
	public void BTC_001_Check_multiplication_for_two_numbers() {
		preset();
		try {
			SmartMobileAccess smartMobile = new SmartMobileAccess(TestActionsAPPIUM);
			smartMobile.MTC_001_Multiplication_of_two_Numbers();
		} catch (Exception e) {
			TestActionsAPPIUM.fail(e.toString());
		} finally {
			postset();
		}
	}
	
	@Test
	public void BTC_004_Check_divison_for_two_numbers() {
		preset();
		try {
			SmartMobileAccess smartMobile = new SmartMobileAccess(TestActionsAPPIUM);
			smartMobile.MTC_001_Division_of_two_Numbers();
		} catch (Exception e) {
			TestActionsAPPIUM.fail(e.toString());
		} finally {
			postset();
		}
	}
}
