package db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException; // 예외타입
import java.sql.Connection; // DB 연동 객체
import java.sql.DriverManager; // JDBC driver 검색
import java.sql.SQLException; // 쿼리문 예외타입
import java.sql.PreparedStatement;

 
public class getrecallproduct {
	public static void insertDB(String Name, String Manu,String Url) {
		
		Connection con =null;
		PreparedStatement pstmt=null;
		
		//1.drive class
		String driver ="com.mysql.jdbc.Driver";
		
		//2, db connect info
		String url= "jdbc:mysql://localhost:3306/testdb?&serverTimezone=UTC";
		String user = "root";
		String pw="coldplay96";
		
		String SQL="INSERT INTO `testdb`.`recall_product` (`product`, `manufacture`, `url`) "
				+ "VALUES ('"+Name+"','"+Manu+"','"+Url+"');";
		
		try {
			Class.forName(driver);
			
			con=DriverManager.getConnection(url,user,pw);
			
			pstmt=con.prepareStatement(SQL);
				
			//date type 4.
			
			//5, 결과리턴
			int r=pstmt.executeUpdate();
			
			System.out.println("변경된 row: "+r);
		}catch(SQLException e) {
			System.out.println("SQL Error: "+e.getMessage()+"]");
		}catch (ClassNotFoundException e1) {
			System.out.println("JDBC Connector Driver 오류: "+e1.getMessage()+"]");
		}finally {
			//db접속 종료
			if(pstmt !=null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
    public static void main(String[] args) {
        BufferedReader br = null;
        //DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        try {
            //OpenApi호출
            String urlstr = "https://www.consumer.go.kr/openapi/contents/index.do?serviceKey=CUIF7B0GHP&pageNo=1&cntPerPage=10&cntntsId=0201";
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //응답 읽기
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim();// result = URL로 XML을 읽은 값
            }
            
            // xml 파싱하기
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            // XPathExpression expr = xpath.compile("/response/body/items/item");
            XPathExpression expr = xpath.compile("//channel/return/content");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                String product_name = null,product_manufacture=null,product_url=null;
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
//                    System.out.println("현재 노드 이름 : " + node.getNodeName());
//                    System.out.println("현재 노드 타입 : " + node.getNodeType());
//                    System.out.println("현재 노드 값 : " + node.getTextContent());
//                    System.out.println("현재 노드 네임스페이스 : " + node.getPrefix());
//                    System.out.println("현재 노드의 다음 노드 : " + node.getNextSibling());
//                    System.out.println("");
                    if(node.getNodeName()=="infoSj") {
                    	//문자열 분해 함수 추가해야함
                    	String tmp=node.getTextContent();
                    	product_manufacture=tmp.substring(tmp.indexOf("[")+1,tmp.indexOf("]"));
                    	product_name=tmp.substring(tmp.indexOf("]")+2,tmp.indexOf("-"));
                    }
                    else if(node.getNodeName()=="infoUrl") {
                    	product_url=node.getTextContent();
                    	insertDB(product_name,product_manufacture,product_url);
                    }
                    
                    
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

