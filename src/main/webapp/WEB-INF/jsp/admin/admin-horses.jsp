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
    <title><fmt:message bundle="${loc}" key="label.admin_participants"/><fmt:message bundle="${loc}" key="label.search_game"/></title>
    <style>
        <jsp:include page="/WEB-INF/css/header.css" />
        <jsp:include page="/WEB-INF/css/style.css" />
    </style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header><p/>

<div class="row">
    <div class="side">

        <div class="container" style="width: 100%; background-color: white;">
            <h3><fmt:message bundle="${loc}" key="label.search_participant"/></h3>
	       <form name="findHorseById" action="find_horse_by_id" method="get">
                <div class="row">
                    <div class="col-75">
                        <input type="search" placeholder="Id" name="horseId">
                    </div>
                </div><p/>
                <div class="row">
                    <input type="submit" value="<fmt:message bundle='${loc}' key='label.search'/>">
                </div>
	       </form>
        </div><p/>

        <div class="container" style="width: 100%; background-color: white;">
            <h3><fmt:message bundle="${loc}" key="label.create_participant"/></h3>
            <form name="createHorseForm" action="controller" method="post">
                <input type="hidden" name="command" value="admin_create_horse">
                <div class="row">
                    <div class="col-25">
                        <input type="text" name="horseName" placeholder="<fmt:message bundle='${loc}' key='label.name'/>" required>
                    </div>
                </div>
                <div class="row">
                    <div class="col-75">
                        <input type="reset" value="<fmt:message bundle='${loc}' key='label.reset'/>">
                        <input type="submit" value="<fmt:message bundle='${loc}' key='label.create'/>">
                    </div>
                </div>
            </form>
        </div>

        <c:out value="${horseCreateMessage}"/><p/>
        <c:remove var="horseCreateMessage" scope="session"/>

        <div class="container" style="width: 100%; background-color: white;">
            <h3><fmt:message bundle="${loc}" key="label.update_participant"/></h3>
            <form name="updateHorseForm" action="controller" method="post">
                <input type="hidden" name="command" value="admin_update_horse">
                <div class="row">
                    <div class="col-75">
                        <fmt:message bundle="${loc}" key="label.select_participant"/>
                        <select style="width: 30%; background-color: #f2f2f2;" name="horseId">
                            <c:forEach var="elem" items="${horsesMap}">
                                <c:set var="horse" scope="request" value="${elem.key}"/>
                                <option value="${horse.id}">${horse.id}</option>
                            </c:forEach>
                        </select><br/>
                    </div>
                </div><p/>
                <div class="row">
                    <div class="col-75">
                        <input type="text" name="horseName" placeholder="<fmt:message bundle='${loc}' key='label.name'/>" required>
                    </div>
                </div><p/>
                <div class="row">
                    <input type="reset" value="<fmt:message bundle='${loc}' key='label.reset'/>">
                    <input type="submit" value="<fmt:message bundle='${loc}' key='label.update'/>">
                </div>
            </form>
        </div><p/>

        <c:out value="${horseUpdateMessage}"/>
        <c:remove var="horseUpdateMessage" scope="session"/>

        <div class="container" style="width: 100%; background-color: white;">
            <h3><fmt:message bundle="${loc}" key="label.delete_participant"/></h3>
            <form name="deleteHorseForm" action="controller" method="post">
                <input type="hidden" name="command" value="admin_delete_horse">
                <div class="row">
                    <div class="col-75">
                        <fmt:message bundle="${loc}" key="label.select_participant"/>
                        <select style="width: 30%; background-color: #f2f2f2;" name="horseId">
                            <c:forEach var="elem" items="${horsesMap}">
                                <c:set var="horse" scope="request" value="${elem.key}"/>
                                <option value="${horse.id}">${horse.id}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div><p/>
                <div class="row">
                    <input type="submit" value="<fmt:message bundle='${loc}' key='label.delete'/>">
                </div>
            </form>
        </div>

        <c:out value="${horseDeleteMessage}"/>
        <c:remove var="horseDeleteMessage" scope="session"/>

    </div>

    <div class="main">
        <c:if test="${not empty horsesMap}">
    	   <jsp:include page="/WEB-INF/jsp/tables/horses-table.jsp"/>
        </c:if>
        <c:out value="${horsesTableMessage}"/><p/>
    </div>
</div>

</body>
<footer>
    <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
</footer>
</html>