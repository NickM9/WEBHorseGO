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
	<title><fmt:message bundle="${loc}" key="label.create_bet"/></title>
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
		<h3><fmt:message bundle="${loc}" key="label.create_bet"/></h3>
		<jsp:include page="/WEB-INF/jsp/tables/single-game-table.jsp"/>

		<div class="container" style="width: 30%;">
			<form name="createBetForm" action="controller" method="post">
				<h3><fmt:message bundle="${loc}" key="label.create_bet"/></h3>
				<input type="hidden" name="command" value="create_bet">
				<input type="hidden" name="gameId" value="${param.gameId}">
				<div class="row">
      				<div class="col-25">
						<fmt:message bundle="${loc}" key="label.select_participant"/>
						<c:set var="map" scope="request" value="${game.horseBetTypes}"/>
					</div>
					<div class="col-75">
						<select style="width: 50%;" name="horseId">
							<c:forEach var="elem" items="${map}">
								<c:set var="horse" scope="request" value="${elem.key}"/>
								<option value="${horse.id}">${horse.id}. ${horse.name}</option>
							</c:forEach>
						</select>
					</div>
				</div><p/>
				<div class="row">
					<div class="col-75">
						<table style="width: 120%;">
							<tr>
								<c:forEach var="betType" items="${betTypes}">
								<th><fmt:message bundle="${loc}" key="bet_type.${betType.localName}"/></th>
								</c:forEach>
							</tr>
							<tr>
								<c:forEach var="betType" items="${betTypes}">
									<td style="background-color: white;"><input type="radio" name="betType" value="${betType}" checked /></td>
								</c:forEach>
							</tr>
						</table>
					</div>
				</div><p/>
				<div class="row">
					<input type="number" placeholder="<fmt:message bundle='${loc}' key='label.bet_amount'/>" name="betAmount" min=0 step="0.01" required/><p/>
				</div>
				<div class="row">
					<input type="reset" value="<fmt:message bundle='${loc}' key='label.reset'/>">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.change'/>">
				</div>
			</form>
		</div>
	</c:if>

	<h1><c:out value="${singleGameMessage}"/></h1>

	<a style="background-color: tomato;
                color: white;
                padding: 10px 20px;
                border-radius: 4px;
                text-align: center;
                text-decoration: none;
                display: inline-block;"
                 href="${pageContext.request.contextPath}/user/main/show_start_tables"><fmt:message bundle="${loc}" key="label.back"/></a><p/>

</body>
	<footer>
        <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
    </footer>
</html>