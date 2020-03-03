<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Error</title>
<style>
        <jsp:include page="/WEB-INF/css/header.css" />
        <jsp:include page="/WEB-INF/css/style.css" />
    </style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>

    <h1>Exception: ${pageContext.errorData.statusCode}</h1><p/>
    Request from: ${pageContext.errorData.requestURI} is failed<br/>
    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        Servlet name or type: ${pageContext.errorData.servletName}<br/>
        Exception: ${pageContext.errorData.throwable}<br/>
    </c:if>

	<footer style="position: fixed; bottom: 0; width: 100%">
        <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
    </footer>
</html>