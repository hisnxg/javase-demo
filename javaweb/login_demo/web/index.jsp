<%--
  Created by IntelliJ IDEA.
  User: nxg
  Date: 2022/3/6
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Cookie[] cookies = request.getCookies();
    String value = "";
    if(cookies!=null||cookies.length>0){
        for (Cookie cook : cookies){
            String name = cook.getName();
            if(name.equals("uname")){
                value = cook.getValue();
                break;
            }
        }
    }
    pageContext.setAttribute("unameCookie",value);
%>
    <form action="login" method="post">
        usernae：<input type="text" name="username" value="${unameCookie}"><br>
        password: <input type="password" name="pass"><br>
        <input type="submit" value="login">
    </form>
</body>
</html>
