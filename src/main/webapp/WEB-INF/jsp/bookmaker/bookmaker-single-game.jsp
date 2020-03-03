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
	<title><fmt:message bundle="${loc}" key="label.game"/> ${gameId}</title>
	<style>
    	<jsp:include page="/WEB-INF/css/header.css" />
    	<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>

    <c:if test="${game != null}">
		<jsp:include page="/WEB-INF/jsp/tables/single-game-table.jsp"/>

		<h3><fmt:message bundle="${loc}" key="label.update_bets"/></h3>
		<div class="container" style="width: 50%;">
			<form name="createGameBetsForm" action="controller" method="post">
				<input type="hidden" name="command" value="bookmaker_update_game_bets">
				<input type="hidden" name="gameId" value="${game.id}">
				<div class="row">
      				<div class="col-25">
						<fmt:message bundle="${loc}" key="label.select_participant"/>
						<c:set var="map" scope="request" value="${game.horseBetTypes}"/>
					</div>
					<div class="col-75">
						<select style="width: 30%;" name="horseId">
							<c:forEach var="elem" items="${map}">
								<c:set var="horse" scope="request" value="${elem.key}"/>
								<option value="${horse.id}">${horse.id}. ${horse.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col-75">
						<table>
							<tr>
								<c:forEach var="betType" items="${betTypes}">
									<th><fmt:message bundle="${loc}" key="bet_type.${betType.localName}"/></th>
								</c:forEach>
							</tr>
							<tr>
								<c:forEach var="i" begin="1" end="${fn:length(betTypes)}">
									<td><input type="number" name="betCoefficient" min=0 step="0.01" required/></td>
								</c:forEach>
							</tr>
						</table>
					</div>
				</div><p/>
				<div class="row">
					<input type="reset" value="<fmt:message bundle='${loc}' key='label.reset'/>">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.update'/>">
				</div>
			</form>
		</div>

		<c:out value="${gameBetsCreateMessage}"/><p/>
		<c:remove var="gameBetsCreateMessage" scope="session"/>

	</c:if>

	<h1><c:out value="${singleGameMessage}"/></h1>

	<a style=" color: tomato;" 
		href="${pageContext.request.contextPath}/bookmaker/bookmaker-main/show_games?page=1"><fmt:message bundle="${loc}" key="label.back"/></a><p/>


</body>
<footer>
    <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
</footer>
</html>