<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Page not found</title>
<style>
  		<jsp:include page="/WEB-INF/css/header.css" />
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>

    <h1>Exception: ${pageContext.errorData.statusCode} Not found</h1><p/>
    Request from: ${pageContext.errorData.requestURI} is failed<br/>

	<footer style="position: fixed; bottom: 0; width: 100%">
        <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
    </footer>
</html>