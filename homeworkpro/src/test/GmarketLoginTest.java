package test;

import org.junit.Assert;
import org.junit.Test;
import homeworkpro.GmarketLogin;

public class GmarketLoginTest {
	@Test
	public void test() {
		String id = "1";
		String password = "1!1";
		
		GmarketLogin object = new GmarketLogin();
		object.login(id,password);
		Assert.assertEquals(object.myg(), "123");
	}
}
