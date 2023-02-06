package calculator.qa.common.pages;

import calculatorqa.base.lib.Base;
import calculatorqa.base.lib.Logs;
import calculatorqa.base.lib.TestActionsAPPIUM;

public class SmartMobileAccess extends Base {
	public TestActionsAPPIUM t;

	public SmartMobileAccess(TestActionsAPPIUM t) {
		this.t = t;
	}

	public void MTC_001_Sum_of_two_Numbers() {
		Logs.step("START: MTC_001_Sum_of_two_Numbers");
		t.storeVisibility("smartmobile.home.number.five.btn", 5);
		t.verifyVisibility("smartmobile.home.number.five.btn", true);
		t.click("smartmobile.home.number.five.btn");
		t.verifyVisibility("smartmobile.home.action.plus.btn", true);
		t.click("smartmobile.home.action.plus.btn");
		t.verifyVisibility("smartmobile.home.number.five.btn", true);
		t.click("smartmobile.home.number.five.btn");
		t.verifyVisibility("smartmobile.home.action.equal.btn", true);
		t.click("smartmobile.home.action.equal.btn");
		Logs.print("MTC_001_Sum_of_two_Numbers: FINISHED");
		t.getScreenshot("MTC_001_Sum_of_two_Numbers");
	}

	public void MTC_001_subtraction_of_two_Numbers() {
		Logs.step("START: MTC_001_subtraction_of_two_Numbers");
		t.storeVisibility("smartmobile.home.number.five.btn", 5);
		t.verifyVisibility("smartmobile.home.number.five.btn", true);
		t.click("smartmobile.home.number.five.btn");
		t.verifyVisibility("smartmobile.home.action.plus.btn", true);
		t.click("smartmobile.home.action.minus.btn");
		t.verifyVisibility("smartmobile.home.number.five.btn", true);
		t.click("smartmobile.home.number.five.btn");
		t.verifyVisibility("smartmobile.home.action.equal.btn", true);
		t.click("smartmobile.home.action.equal.btn");
		Logs.print("MTC_001_Sum_of_two_Numbers: FINISHED");
		t.getScreenshot("MTC_001_subtraction_of_two_Numbers");
	}

	public void MTC_001_Multiplication_of_two_Numbers() {
		Logs.step("START: MTC_001_Multiplication_of_two_Numbers");
		t.storeVisibility("smartmobile.home.number.five.btn", 5);
		t.verifyVisibility("smartmobile.home.number.five.btn", true);
		t.click("smartmobile.home.number.five.btn");
		t.verifyVisibility("smartmobile.home.action.plus.btn", true);
		t.click("smartmobile.home.action.multiply.btn");
		t.verifyVisibility("smartmobile.home.number.five.btn", true);
		t.click("smartmobile.home.number.five.btn");
		t.verifyVisibility("smartmobile.home.action.equal.btn", true);
		t.click("smartmobile.home.action.equal.btn");
		Logs.print("MTC_001_Sum_of_two_Numbers: FINISHED");
		t.getScreenshot("MTC_001_Multiplication_of_two_Numbers");
	}
	
	public void MTC_001_Division_of_two_Numbers() {
		Logs.step("START: MTC_001_Division_of_two_Numbers");
		t.storeVisibility("smartmobile.home.number.nine.btn", 5);
		t.verifyVisibility("smartmobile.home.number.nine.btn", true);
		t.click("smartmobile.home.number.nine.btn");
		t.verifyVisibility("smartmobile.home.action.plus.btn", true);
		t.click("smartmobile.home.action.division.btn");
		t.verifyVisibility("smartmobile.home.number.three.btn", true);
		t.click("smartmobile.home.number.three.btn");
		t.verifyVisibility("smartmobile.home.action.equal.btn", true);
		t.click("smartmobile.home.action.equal.btn");
		Logs.print("MTC_001_Division_of_two_Numbers: FINISHED");
		t.getScreenshot("MTC_001_Division_of_two_Numbers");
	}
}