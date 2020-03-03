<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" uri="/WEB-INF/tld/hello.tld" %>

<html lang="en">
	<head>
		<fmt:setLocale value="${sessionScope.local}"/>
		<fmt:setBundle basename="localization.local" var="loc"/>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>HorseGo</title>
		<style>
  			<jsp:include page="/WEB-INF/css/header.css" />
  			<jsp:include page="/WEB-INF/css/style.css" />
		</style>
	</head>
	<body>

		<div class="header">
			<h1><fmt:message bundle="${loc}" key="label.welcome_to"/> Horse<span>GO!</span></h1>
			<h3><tag:hello/></h3>
		</div>
		<ul>
			<li><a href="log-in"><fmt:message bundle="${loc}" key="label.log_in"/></a></li>
			<li><a href="sign-up"><fmt:message bundle="${loc}" key="label.sign_up"/></a></li>
		</ul>

		<jsp:include page="/WEB-INF/jsp/tables/horses-table.jsp"/>
		<jsp:include page="/WEB-INF/jsp/tables/games-table.jsp"/>
	
	</body>
	<div class="footer">
    	<footer>
        	<jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
    	</footer>
	</div>
</html>