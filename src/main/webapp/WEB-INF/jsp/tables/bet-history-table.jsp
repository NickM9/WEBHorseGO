<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
	<fmt:setLocale value="${sessionScope.local}"/>
	<fmt:setBundle basename="localization.local" var="loc"/>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title><fmt:message bundle="${loc}" key="label.bets_history"/></title>
	<style>
  		<jsp:include page="/WEB-INF/css/table.css" />
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>

		<table>
			<caption align="left"><h3><fmt:message bundle="${loc}" key="label.bets_history"/></h3></caption>
			<tr>
				<th><fmt:message bundle="${loc}" key="label.bet_number"/></th>
				<th><fmt:message bundle="${loc}" key="label.user_number"/></th>
				<th><fmt:message bundle="${loc}" key="label.participant_number"/></th>
				<th><fmt:message bundle="${loc}" key="label.game_number"/></th>
				<th><fmt:message bundle="${loc}" key="label.bet_type"/></th>
				<th><fmt:message bundle="${loc}" key="label.coefficient"/></th>
				<th><fmt:message bundle="${loc}" key="label.bet_amount"/></th>
				<th><fmt:message bundle="${loc}" key="label.game_status"/></th>
				<th><fmt:message bundle="${loc}" key="label.bet_result"/></th>
			</tr>
			<c:forEach var="elem" items="${betHistoryMap}">
				<c:set var="bet" scope="request" value="${elem.key}"/>
				<c:set var="betGame" scope="request" value="${elem.value}"/>
				<tr>
					<td><c:out value="${bet.id}"/></td>
					<td><c:out value="${bet.userId}"/></td>
					<td><c:out value="${bet.horseId}"/></td>
					<td><c:out value="${bet.gameId}"/></td>
					<td><fmt:message bundle="${loc}" key="bet_type.${bet.betType.type.localName}"/></td>
					<td><c:out value="${bet.betType.coefficient}"/></td>
					<td><c:out value="${bet.betAmount}"/></td>
					<c:choose>
						<c:when test="${betGame.gamePlayed==false && bet.userWin==false}">
							<td><fmt:message bundle="${loc}" key="label.not_played"/></td>
							<td><fmt:message bundle="${loc}" key="label.not_played"/></td>
						</c:when>
						<c:when test="${betGame.gamePlayed==true && bet.userWin==false}">
							<td><fmt:message bundle="${loc}" key="label.played"/></td>
							<td><fmt:message bundle="${loc}" key="label.defeat"/></td>
						</c:when>
						<c:otherwise>
							<td><fmt:message bundle="${loc}" key="label.played"/></td>
							<td><fmt:message bundle="${loc}" key="label.win"/></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</table>

</body>
</html>