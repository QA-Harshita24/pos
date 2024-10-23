package script;

import org.testng.Assert;
import org.testng.annotations.Test;

import generic.BaseTest;
import generic.Utility;
import page.LoginPage;

public class InvalidLogin extends BaseTest
{
	@Test(priority = 2)
	public void testInvalidLogin()
	{
//		Get Test Data 
		String un=Utility.getXLCellData(XL_PATH, "InvalidLogin",1, 0);
		String pw=Utility.getXLCellData(XL_PATH, "InvalidLogin",1, 1);
		
//		1. enter invalid user name
		test.info("enter invalid user name: "+un);
		LoginPage loginPage=new LoginPage(driver);
		loginPage.setUserName("abc");
		
//		2. enter invalid password
		test.info("enter invalid password: "+pw);
		loginPage.setPassword("xyz");
		
//		3. click on go button
		test.info("click on go button");
		loginPage.clickGoButton();
		
//		4. should display error message
		test.info("should display error message");
		boolean result = loginPage.verifyErrMsgIsDisplayed(wait);
		Assert.assertTrue(result);
		
	}
}
