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
	<title><fmt:message bundle="${loc}" key="label.games_table"/></title>
	<style>
  		<jsp:include page="/WEB-INF/css/table.css" />
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>
	
	
	<table>
		<caption align="left"><h3><fmt:message bundle="${loc}" key="label.games_list"/></h3></caption>
		<tr>
			<th><fmt:message bundle="${loc}" key="label.game_number"/></th>
			<th><fmt:message bundle="${loc}" key="label.game_status"/></th>
			<th><fmt:message bundle="${loc}" key="label.participants_list"/></th>
			<c:forEach var="betType" items="${betTypes}">
				<th><fmt:message bundle="${loc}" key="bet_type.${betType.localName}"/></th>
			</c:forEach>
		</tr>
		<c:forEach var="game" items="${gamesList}">
		<tr>
			<td rowspan="${fn:length(game.horses)}"><a style="color: black;" href="${pageContext.request.contextPath}/user/main/find_game_by_id?gameId=${game.id}"><c:out value="${game.id}"/></td>
			<c:choose>
				<c:when test="${game.gamePlayed==false}">
					<td rowspan="${fn:length(game.horses)}"><fmt:message bundle="${loc}" key="label.not_played"/></td>
				</c:when>
				<c:otherwise>
					<td rowspan="${fn:length(game.horses)}"><fmt:message bundle="${loc}" key="label.played"/></td>
				</c:otherwise>
			</c:choose>
			<c:set var="map" scope="request" value="${game.horseBetTypes}"/>
			<c:forEach var="elem" items="${map}">
				<c:set var="horse" scope="request" value="${elem.key}"/>
				<td><c:out value="${horse.id}. ${horse.name}"/></td>
				<c:forEach var="horseBet" items="${elem.value}">
					<td><c:out value="${horseBet.coefficient}"/></td>
				</c:forEach>
			</tr>
			</c:forEach>
		</tr>
		</c:forEach>
	</table><p/>
  
</body>
</html>