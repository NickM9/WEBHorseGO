<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>HorseGo</title>
	</head>
	<body>
		<c:if test="${empty sessionScope.local}">
			<c:set var="local" value="en" scope="session" />
		</c:if>
		<fmt:setLocale value="en" scope="session"/>
		<jsp:forward page="/home/show_start_tables"/>
	</body>
</html>