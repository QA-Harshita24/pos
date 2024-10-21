package script;

import org.testng.annotations.Test;

import generic.BaseTest;

public class Demo3 extends BaseTest{

	@Test
	public void testC()
	{

		String title = driver.getTitle();
		test.info("testC"+title);
	}
}
