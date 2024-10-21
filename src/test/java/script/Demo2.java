package script;

import org.testng.Assert;
import org.testng.annotations.Test;

import generic.BaseTest;

public class Demo2 extends BaseTest{

	@Test
	public void testB()
	{

		String title = driver.getTitle();
		test.info("testB"+title);
		//Assert.fail();
	}
}
