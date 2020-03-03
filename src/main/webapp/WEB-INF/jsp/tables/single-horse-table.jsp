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
	<title><fmt:message bundle="${loc}" key="label.participant"/> ${horseId}</title>
	<style>
  		<jsp:include page="/WEB-INF/css/table.css" />
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>

    <table>
    	<caption align="left"><h3><fmt:message bundle="${loc}" key="label.participant"/> № ${horseId}</h3></caption>
    	<tr>
			<th><fmt:message bundle="${loc}" key="label.participant_number"/></th>
			<th><fmt:message bundle="${loc}" key="label.participant_name"/></th>
			<th><fmt:message bundle="${loc}" key="label.participant_games"/></th>
		</tr>
		<tr>
			<td><c:out value="${horse.id}"/></td>
			<td><c:out value="${horse.name}"/></td>
			<td>
				<c:forEach var="game" items="${horseGames}">
					<c:out value="${game.id}"/>
				</c:forEach>
			</td>
		</tr>
    </table>


    
	
</body>
</html>