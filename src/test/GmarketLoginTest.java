package test;



import homeworkpro.GmarketLogin;
import org.junit.jupiter.api.Test;

public class GmarketLoginTest {
	@Test
	public void test() {
		String id = "1";
		String password = "1!1";
		
		GmarketLogin object = new GmarketLogin();
		object.login(id,password);
		String myresult = object.myg();
		
		object.crawll(myresult);
		
	}
}
