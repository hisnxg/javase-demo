<%--
  Created by IntelliJ IDEA.
  User: nxg
  Date: 2022/3/6
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
isErrorPage="true" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

request=<%=request.getAttribute("a1")%><br>


session=<%=session.getAttribute("b1")%><br>

application= <%=application.getAttribute("c1")%><br>

pageContext=<%=pageContext.getAttribute("d1")%><br>
<%out.print("hello world");%>
<%out.print("<script>alert('success')</script>");%>


exception=<%=exception.getMessage()%>

</body>
</html>
