<%--
  Created by IntelliJ IDEA.
  User: User-TL
  Date: 2019/4/6
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/login.action" method="post">
    用户名：<input type="text" name="username" value="">
    密  码：<input type="password" name="userpass" value="123">
    <input type="submit" value="提交">&nbsp;&nbsp;${fail}
</form>
</body>
</html>
