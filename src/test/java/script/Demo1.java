package script;

import org.testng.annotations.Test;

import generic.BaseTest;
import generic.Utility;

public class Demo1 extends BaseTest{

	@Test
	public void testA()
	{
		String data = Utility.getXLCellData(XL_PATH, "sheet1", 0, 0);
		test.info("Data from excel:"+data);
		String title = driver.getTitle();
		test.info("testA"+title);
	}
}
