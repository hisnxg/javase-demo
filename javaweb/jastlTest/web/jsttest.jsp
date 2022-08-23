<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: nxg
  Date: 2022/3/6
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:set var="uname" value="zhangsan"></c:set>
<c:out value="${sessionScope.uname}"></c:out>
<c:if test="${uname=='zhangsan'}">
这是张三
</c:if>

<c:choose>
  <c:when test="uanme=='zhangsan'">
      c-choose-zhansan
  </c:when>
    <c:otherwise>
        c-choose-不是张三
    </c:otherwise>
</c:choose>
<br>
<%
    List list = new ArrayList();
    list.add("aaa李四");
    list.add("bbb战三");
    list.add("ccc王五");
    list.add("ddd西斯");
    pageContext.setAttribute("ulist",list);
%>
<c:forEach items="ulist" var="user" varStatus="stu">
    ${stu.count}---${stu.index}--------${user}<br>
</c:forEach>
<br>
<%
    pageContext.setAttribute("date1",new Date());

%>

    当前时间：<fmt:formatDate value="${date1}" pattern="yyyy-MM-dd hh:mm:ss"></fmt:formatDate>

</body>
</html>
