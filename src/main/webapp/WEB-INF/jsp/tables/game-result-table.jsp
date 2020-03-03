<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
	<fmt:setLocale value="${sessionScope.local}"/>
	<fmt:setBundle basename="localization.local" var="loc"/>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Games table</title>
	<style>
  		<jsp:include page="/WEB-INF/css/table.css" />
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>
	
	<table>
		<caption align="left"><h3><fmt:message bundle="${loc}" key="label.game_result"/></h3></caption>
		<tr>
			<th><fmt:message bundle="${loc}" key="label.position"/></th>
			<th><fmt:message bundle="${loc}" key="label.participant"/></th>			
		</tr>
		<c:set var="map" scope="request" value="${gameResult}"/>
		<c:forEach var="elem" items="${map}">
			<c:set var="position" scope="request" value="${elem.key}"/>
			<c:set var="horse" scope="request" value="${elem.value}"/>
		<tr>
			<td><c:out value="${position}"/></td>
			<td><c:out value="${horse.id}. ${horse.name}"/></td>
		</tr>
		</c:forEach>
	</table><p/>
  
</body>
</html>