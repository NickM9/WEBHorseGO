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
	<title><fmt:message bundle="${loc}" key="label.bookmaker_main"/></title>
	<style>
  		<jsp:include page="/WEB-INF/css/header.css" />
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>

<div class="row">
	<div class="side">
		<div class="container" style="width: 100%; background-color: white;">
			<h3><fmt:message bundle="${loc}" key="label.search_game"/></h3>
			<form name="findGameById" action="find_game_by_id" method="get">
				<div class="row">
					<div class="col-75">
						<input type="search" placeholder="Id" name="gameId">
					</div>
				</div>
				<div class="row">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.search'/>">
				</div>
			</form>
		</div><p/>

		<div class="container" style="width: 100%; background-color: white;">
			<h3><fmt:message bundle="${loc}" key="label.update_bets"/></h3>
			<form name="createGameBetsForm" action="find_game_by_id" method="get">
				<fmt:message bundle="${loc}" key="label.select_game"/>
				<div class="row">
					<div class="col-75">
						<select style="width: 50%; background-color: #f2f2f2;" name="gameId">
							<c:forEach var="game" items="${gamesList}">
								<option value="${game.id}">${game.id}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.update'/>">
				</div>
			</form>
		</div>

		<c:out value="${gameBetsCreateMessage}"/><p/>
	</div>

	<div class="main">
		<c:if test="${not empty gamesList}">
			<jsp:include page="/WEB-INF/jsp/tables/games-table.jsp"/>

			<div class="pagination">
        		<c:set var="pageId" value="${param.page}"/>
        		<a href="${pageContext.request.contextPath}/bookmaker/bookmaker-main/show_games?page=1">&laquo;</a>
        		<c:forEach var = "i" begin = "1" end = "${paddingSize}">
            		<c:choose>
                		<c:when test="${i == pageId}">
                    		<a class="active" href="${pageContext.request.contextPath}/bookmaker/bookmaker-main/show_games?page=${i}"><c:out value="${i}"/></a>  
                		</c:when>
                		<c:otherwise>
                    		<a href="${pageContext.request.contextPath}/bookmaker/bookmaker-main/show_games?page=${i}"><c:out value="${i}"/></a>
                		</c:otherwise>
            		</c:choose>
       			 </c:forEach>
        		<a href="${pageContext.request.contextPath}/bookmaker/bookmaker-main/show_games?page=${paddingSize}">&raquo;</a>
    		</div><p/>

		</c:if>

		<a style="color: tomato;" href="${pageContext.request.contextPath}/bookmaker/bookmaker-main/show_games_history?page=1"><fmt:message bundle="${loc}" key="label.games_history"/></a><p/>

		<c:out value="${gamesTableMessage}"/><p/>
	</div>
</div>

</body>
<footer>
    <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
</footer>
</html>