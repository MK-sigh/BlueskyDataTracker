<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bluesky Data Tracker</title>
    <h2>ログイン</h2>

    <!-- メッセージがある場合だけ表示する -->
    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>

    <form action="login" method="post">
        データベース: <input type="text" name="dataSource"><br>
        ユーザー名: <input type="text" name="name"><br>
        パスワード: <input type="password" name="pass"><br>
        <button type="submit">ログイン</button>
    </form>

    <a href="register">ユーザー登録</a>

</body>
</html>