<%@ page import="java.util.List" %>
<%@ page import="sun.security.util.ArrayUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: nxg
  Date: 2022/3/6
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>top.jsp</h1>

<%
    //EL表达式如果想要读取变量，则该变量一定要在作用域中：pageContext、request、session、application
    int a=30;
    request.setAttribute("a",a);

    List list = new ArrayList();
    list.add("absd");
    pageContext.setAttribute("a2",list);

    Map map = new HashMap();
    map.put("k1","helloValue");
    pageContext.setAttribute("a3",map);

    //如果在相同的key的前提下，不同的作用域，el表达式会选择展示哪个值？
    //从低到高的顺序进行选择

    pageContext.setAttribute("a4","pageContext内容");
    request.setAttribute("a4","request");
    session.setAttribute("a4","session");
    application.setAttribute("a4","application");

%>
${8+10}<br>
${8>10}<br>
${6<10?"yes":"NO"}<br>

a=${a}<br>

a2=${a2[0]}<br>
k1=${a3.k1}<br>
选择最低的=${a4}<br>

session = ${sessionScope.a4}<br>
request=${requestScope.a4}<br>
application = ${applicationScope.a4}<br>

</body>
</html>
