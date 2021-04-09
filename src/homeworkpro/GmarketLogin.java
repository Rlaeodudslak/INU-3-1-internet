package homeworkpro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jsoup.Jsoup; 
import org.jsoup.nodes.Document; 
import org.jsoup.nodes.Element; 
import javax.net.ssl.HttpsURLConnection;

public class GmarketLogin {

	private String myCookies = "";

	public String login(String id, String password) {// g마켓로그인에 토큰이 필요하므로 그 값들을 가져와서 보낸다음 로그인을 함
		URL obj;

		String myResult = "";
		try {
			HashMap<String, String> pList = new HashMap<String, String>(); // hashmap = http post 로 보낼 파라미터값 저장
			List<String> tokens = Crawler.GetGmarketToken();
			String cookieToken = tokens.get(0);
			String htmlToken = tokens.get(1);
			
			pList.put("id", id);
			pList.put("pwd", password);
			pList.put("member_type", "MEM");
			pList.put("__RequestVerificationToken", htmlToken);
			pList.put("url", "https://myg.gmarket.co.kr");
			pList.put("command", "login");

			obj = new URL("https://signinssl.gmarket.co.kr/LogIn/LogInProc");
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			String cookie = "";
			// cookie +=
			// "connect.sid=s%3A35IOsKeAn9EuAemR7zR_hPfSfNOkMMjZ.RHOKSDnLSoj2UX7gX6eSPyuHjr9nzJyA3eSiIwXCUMU;";

			cookie += "pguid=21591797224265001632010000;";
			cookie += "__RequestVerificationToken=" + cookieToken + ";";
			cookie += "ssguid=315917972242650016322830000;";
			cookie += "sguid=31591797224265001632283000;";
			cookie += "cguid=11591797224265001632000000;";
			cookie += "pcidx=E46E2E360405306169D8F0D6E46BF537839A8D58FEF63D8F8EB1610F8D11754D;";
			cookie += "shipnation=KR;";
			cookie += "charset=enUS;";
			con.setRequestProperty("Connection", "keep-alive");
			con.setRequestProperty("Cookie", cookie);
			con.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			StringBuffer buffer = new StringBuffer();

			// HashMap으로 전달받은 파라미터가 null이 아닌경우 버퍼에 넣어준다
			if (pList != null) {

				Set key = pList.keySet();

				for (Iterator iterator = key.iterator(); iterator.hasNext();) {
					String keyName = (String) iterator.next();
					String valueName = pList.get(keyName);
					buffer.append(keyName).append("=").append(valueName);
					buffer.append("&");
				}
			}
			buffer.deleteCharAt(buffer.length() - 1);

			con.setDoOutput(true);
			OutputStreamWriter outStream = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// --------------------------
			// Response Code
			// --------------------------
			// http.getResponseCode();

			String Set_Cookie = "Set-Cookie";
			if(con.getHeaderFields().containsKey(Set_Cookie)) {
				myCookies="";
				List<String> cookieTemp = con.getHeaderFields().get(Set_Cookie);
				for(int i=0;i<cookieTemp.size();i++) {
					myCookies +=cookieTemp.get(i) + ";";
					
				}
			}

			// --------------------------
			// 서버에서 전송받기
			// --------------------------
			InputStreamReader tmp = new InputStreamReader(con.getInputStream(), "UTF-8");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null) {
				builder.append(str + "\n");
			}
			myResult = builder.toString();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(myResult);
		return myResult;
	}

	public String myg() {// 로그인 성공후 자신의 주문내역페이지로 간다음 그 html 코드를 myresult에 저장
		URL obj;

		String myResult = "";
		try {

			obj = new URL("https://myg.gmarket.co.kr/");
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestProperty("Connection", "keep-alive");
			con.setRequestProperty("Cookie", myCookies);
			//System.out.println(myCookies);
			StringBuffer buffer = new StringBuffer();

			con.setDoOutput(true);
			OutputStreamWriter outStream = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// --------------------------
			// Response Code
			// --------------------------
			// http.getResponseCode();

			// --------------------------
			// 서버에서 전송받기
			// --------------------------
			InputStreamReader tmp = new InputStreamReader(con.getInputStream(), "UTF-8");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null) {
				builder.append(str + "\n");
			}
			myResult = builder.toString();
			//System.out.print(myResult);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return myResult;
	}
	
	public List<String> crawll( String htmltext ) {// getlistdate과 같이 이용해서 주문내역 페이지 코드중 상품의 주문 날짜만 따로 크롤링함.
		List<String> response = new ArrayList<String>();
		Document doc = null; 
		
		doc = Jsoup.parse(htmltext); 
		
		


		for(Element e : doc.select("li.tit_info").select("a")) {
			String text = e.text();
			if(!text.equals("문의하기")) {
				response.add(text);
				System.out.println(text);
			}
		}
		return response;
	}
	
	public List<String> getList(String id,String password){
		this.login(id,password);
		return this.crawll(this.myg());
	}
	
	
	public List<String> crawlldate( String htmltext ) {// getlistname과 같이 이용해서 주문내역 페이지 코드중 상품의 이름만 따로 크롤링함.
		List<String> response = new ArrayList<String>();
		Document doc = null; 
		
		doc = Jsoup.parse(htmltext); 
		
		


		for(Element e : doc.select("div.td_detail")) {
			String text = e.text();
			if(!text.contains("기본 배송지")) {
				response.add(text.substring(0,10));
				System.out.println(text);
			}
		}
		return response;
	}
	
	public List<String> getListdate(String id,String password){
		this.login(id,password);
		return this.crawlldate(this.myg());
	}
	
	
	public List<String> crawllname( String htmltext ) {
		List<String> response = new ArrayList<String>();
		Document doc = null; 
		
		doc = Jsoup.parse(htmltext); 
		
		


		for(Element e : doc.select("li.tit_info").select("a")) {
			String text = e.text();
			if(!text.contains("기본 배송지")) {
				response.add(text);
				System.out.println(text);
			}
		}
		return response;
	}
	
	public List<String> getListname(String id,String password){
		this.login(id,password);
		return this.crawllname(this.myg());
	}
	
}
