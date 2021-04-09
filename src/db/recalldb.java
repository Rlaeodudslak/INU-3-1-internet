package db;

import java.sql.*;
import java.util.ArrayList;

//recall 정보가 있는 db연결
public class recalldb {
		public static Connection getMySqlConnection(){
			Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				
				String dbURL ="jdbc:mysql://localhost:3306/testdb?&serverTimezone=UTC";
				String dbID = "root";
				String dbPassword = "root";
				
				conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
			}catch(ClassNotFoundException| SQLException e) {
				e.printStackTrace();
				System.out.println("db접속 실패");
			}
			return conn;
		}
	 
		
		/*
		 * 리콜 정보가 있는 db의 product,manufacture,url값을 
		 * 각각 info 클래스의 name,company,link로 넘겨준다.
		 * 이때,회사명과 제품명은 eraser 객체를 이용해 필요없는 글자들을 지운다.
		 */
		
		public ArrayList<info> list(){
			
			ArrayList<info> product = new ArrayList<>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = getMySqlConnection();
				String sql = "select * from recall_product";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				eraser er = new eraser();
				
				
				while(rs.next()){
					info a = new info();
					a.setname(er.erase(rs.getString("product")));
					a.setcompany(er.erase((rs.getString("manufacture"))));
					a.setlink(rs.getString("url"));
					product.add(a);
			}
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				try{
					if(rs!=null){rs.close();}
				}catch (Exception e2){
					e2.printStackTrace();
				}
				try{
					if(pstmt!=null){pstmt.close();}

				}catch(Exception e2){
					e2.printStackTrace();
				}

				try{
					if(conn!=null){conn.close();}

				}catch(Exception e2){
					e2.printStackTrace();
				}

			}
			return product;
			
		}


	}

