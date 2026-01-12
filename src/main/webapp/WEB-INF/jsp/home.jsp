<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<%
// セッションスコープからユーザー情報を取得
String loginUser = (String) session.getAttribute("loginUser");
String database = (String) session.getAttribute("database");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bluesky Data Tracker</title>
</head>
<body>
    <h1>Bluesky Data Tracker</h1>
    <a href="index.jsp">ログアウト</a>
</body>
</html>