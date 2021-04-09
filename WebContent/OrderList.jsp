<!-- 주문내역 페이지  -->

<!DOCTYPE>
<html lang="ko" xmlns="http://www.w3.org/1999/html">
<%@ page contentType="text/html; charset=UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import="db.info" %>
<%@ page import="db.recalldb" %>
<%@ page import="java.util.ArrayList" %>

<head>
	<title>리콜을 부탁해</title>
	<link rel="stylesheet" type="text/css" href="OrderList.css"/>

</head>

<body>

<!-- 필요한 변수들 생성 -->

<%!
	recalldb a = new recalldb();
	ArrayList<info> list = a.list();
%>
<%
	String[] name = new homeworkpro.GmarketLogin().getListname(request.getParameter("id"),request.getParameter("pw")).toString().split(",");
	String[] date = new homeworkpro.GmarketLogin().getListdate(request.getParameter("id"),request.getParameter("pw")).toString().split(",");
%>


<!-- 
	크롤링 데이터가 한 배열에 담겨 한꺼번에 넘어오기 때문에 배열 첫번째와 마지막에 생기는 []를 지워준다. 
-->

<%
	date[date.length-1] = date[date.length-1].replaceAll("[\\[\\]]", "");
	date[0] = date[0].replaceAll("[\\[\\]]", "");
	name[0] = name[0].replaceAll("[\\[\\]]", "");
	name[name.length-1] = name[name.length-1].replaceAll("[\\[\\]]", "");
%>



<h1>리콜을<br>부탁해</h1>
<div class="container">
	<p id="order">주문내역 불러오기</p>
	<button id="logout" onclick="location='index.html'">로그아웃</button>
</div>


<!-- 
	주문내역은 최근 10개까지만 제공된다.
	주문날짜와 품목명을 저장하고 있는 두 배열을 for문을 통해 값을 출력한다.
	마지막 비어있는 div의 경우는 리콜 제품으로 판명된 제품의 상세 정보를 제공하기 위한 링크를 제공할 것이다.	
 -->

<div id="panel">

	<% for (int i = 0; i<date.length; i++) {
		if(date.length > 10){%> <script>alert("최근 10개 품목만 제공됩니다.");</script><%}%>
	<div id = "row<%=i%>" class="list">
		<div class="date"><%=date[i]%></div>
		<div class="product"><%=name[i]%></div>
		<div id=linker<%=i%> class="detail"></div>
	</div>
	<% if(i>10)break;}

  /*
  	리콜 db와 주문내역의 상품을 비교한다.
  	이때 주문내역의 상품명에 리콜 db에 있는 회사명이나 제품명 둘 중 하나만 포함되어 있을 경우 해당 상풍표는 배경색이 주황색으로 변한다.
  	회사명과 제품명 둘 다 포함하고 있을 경우엔 빨강색으로 변한다.
  */

		for (db.info info : list) {
			for (int j = 0; j < date.length; j++) {
				if (name[j].contains(info.getname())
						|| name[j].contains(info.getcompany())) {%>
	<script>
		document.getElementById("row<%=j%>").style.backgroundColor = "orange";
	</script>

	<%
		} if (name[j].contains(info.getname())
			&& name[j].contains(info.getcompany())) {
	%>
	<script>
		document.getElementById("row<%=j%>").style.backgroundColor = "red";
		document.getElementById("linker<%=j%>").innerHTML += '<a id="detailbtn" href= http://<%=info.getlink()%> target="_blank">상세보기</a>';
	</script>

	<%

				}
			}
		}
	%>

</div>
<footer>
	<p class="footerfont">프로젝트 명 : 인설실 뿌수기</p>
	<p class="footerfont">제작자 : 임창민, 김대영, 김재훈, 심재서</p>
</footer>
</body>
</html>