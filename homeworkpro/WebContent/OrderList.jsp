<!DOCTYPE html>
<%@page import="homeworkpro.Crawler"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%
request.setCharacterEncoding("utf-8"); 
%>

<html>

<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
<title>리콜을 부탁해</title>
<link rel="stylesheet" type="text/css" href="OrderList.css" />

</head>

<body>

<h1>주문내역</h1>
<%= Crawler.GetGmarketToken() %>
<%= request.getParameter("id")%>
<%= request.getParameter("pw")%>

<!-- 변수 정의 -->
<%!
int ListNum=5;	// 주문내역 갯수

%>



<div id="panel">

<% for (int i = 0; i<ListNum; i++) { %>
	<div class="list">
		<div class="date">20200202</div>
		<div class="manufacturer">asd</div>
		<div class="product">asd</div>
	</div>
<% } %>

</div>
</body>
</html>