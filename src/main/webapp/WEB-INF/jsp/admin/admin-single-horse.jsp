<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message bundle="${loc}" key="label.participant"/> ${horseId}</title>
    <style>
        <jsp:include page="/WEB-INF/css/header.css" />
        <jsp:include page="/WEB-INF/css/style.css" />
    </style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>

    <c:if test="${horse != null}">
    	<jsp:include page="/WEB-INF/jsp/tables/single-horse-table.jsp"/>

        <c:if test="${not empty horseGames}">
            <c:set var="gamesList" scope="request" value="${horseGames}"/>
            <jsp:include page="/WEB-INF/jsp/tables/games-table.jsp"/>
        </c:if>
        
        <div class="container" >
    	   <h3><fmt:message bundle="${loc}" key="label.update_participant"/></h3>
            <form name="updateHorseForm" action="controller" method="post">
                <input type="hidden" name="command" value="admin_update_horse">
                <input type="hidden" name="horseId" value="${horse.id}">
                <div class="row">
                    <div class="col-75">
                        <input type="text" name="horseName" placeholder="Имя" required>
                    </div>
                </div><p/>
                <div class="row">
                    <input type="reset" value="<fmt:message bundle='${loc}' key='label.reset'/>">
                    <input type="submit" value="<fmt:message bundle='${loc}' key='label.update'/>">
                </div>
            </form>

            <div class="row">
                <form name="deleteHorseForm" action="controller" method="post">
                    <input type="hidden" name="command" value="admin_delete_horse">
                    <input type="hidden" name="horseId" value="${horse.id}">
                    <input type="submit" value="<fmt:message bundle='${loc}' key='label.delete'/>">
                </form>
            </div>
        </div>

        <c:out value="${horseUpdateMessage}"/>
        <c:out value="${horseDeleteMessage}"/>
	</c:if>

    <h1><c:out value="${singleHorseMessage}"/></h1>

    <a style=" color: tomato;" href="${pageContext.request.contextPath}/admin/admin-horses/show_horses"><fmt:message bundle="${loc}" key="label.back"/></a><p/>

</body>
<footer>
    <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
</footer>
</html>