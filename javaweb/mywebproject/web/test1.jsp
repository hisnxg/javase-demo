<%--
  Created by IntelliJ IDEA.
  User: nxg
  Date: 2022/3/6
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="test2.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% request.setAttribute("a1","a1");%>
request=<%=request.getAttribute("a1")%><br>

<% session.setAttribute("b1","b1");%>
session=<%=session.getAttribute("b1")%><br>
<%application.setAttribute("c1","c1");%>
application= <%=application.getAttribute("c1")%><br>
<%pageContext.setAttribute("d1","d1");%>
pageContext=<%=pageContext.getAttribute("d1")%><br>


<a href="test2.jsp">test2.jsp</a>

<%=8/0%>

</body>
</html>
