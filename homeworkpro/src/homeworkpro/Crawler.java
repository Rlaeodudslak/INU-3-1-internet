package homeworkpro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Connection;

public class Crawler {
	public static List<String> GetGmarketToken() {
				List<String> response = new ArrayList<>();
				String url = "https://signinssl.gmarket.co.kr/login/login"; //크롤링할 url지정
				Document doc = null;        //Document에는 페이지의 전체 소스가 저장된다
				String cookieToken = "";
				String inputToken = "";
				try {
					Connection.Response jsoupResponse = Jsoup.connect(url).method(Connection.Method.GET).execute();
					doc = jsoupResponse.parse();
					cookieToken = jsoupResponse.cookie("__RequestVerificationToken");
					response.add(cookieToken);
				} catch (IOException e) {
					e.printStackTrace();
				}

				//Iterator을 사용하여 하나씩 값 가져오기
				Iterator<Element> ie1 = doc.select("input[name=\"__RequestVerificationToken\"]").iterator();

				while (ie1.hasNext()) {
					inputToken = ie1.next().val();
					response.add(inputToken);
				}
				return response;
				
	}
}
