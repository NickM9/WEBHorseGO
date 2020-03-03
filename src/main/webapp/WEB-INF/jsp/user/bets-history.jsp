<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<html lang="en">
  <fmt:setLocale value="${sessionScope.local}"/>
  <fmt:setBundle basename="localization.local" var="loc"/>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title><fmt:message bundle="${loc}" key="label.bets_history"/></title>
  <style>
    <jsp:include page="/WEB-INF/css/header.css" />
    <jsp:include page="/WEB-INF/css/style.css" />
  </style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>
		
		<c:if test="${not empty betHistoryMap}">
			<jsp:include page="/WEB-INF/jsp/tables/bet-history-table.jsp"/><p/>

      <div class="pagination">
        <c:set var="pageId" value="${param.page}"/>
        <a href="${pageContext.request.contextPath}/user/main/show_bets_history?page=1">&laquo;</a>
        <c:forEach var = "i" begin = "1" end = "${paddingSize}">
            <c:choose>
                <c:when test="${i == pageId}">
                    <a class="active" href="${pageContext.request.contextPath}/user/main/show_bets_history?page=${i}"><c:out value="${i}"/></a>  
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/user/main/show_bets_history?page=${i}"><c:out value="${i}"/></a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <a href="${pageContext.request.contextPath}/user/main/show_bets_history?page=${paddingSize}">&raquo;</a>
    </div><p/>

		</c:if>
	

	<h2><c:out value="${betHistoryMessage}"/></h2>

	<a style="background-color: tomato;
  				color: white;
  				padding: 10px 20px;
  				border-radius: 4px;
  				text-align: center;
  				text-decoration: none;
  				display: inline-block;" 
	href="${pageContext.request.contextPath}/user/main/show_start_tables"><fmt:message bundle="${loc}" key="label.back"/></a><p/>

</body>
	<footer style="position: fixed; bottom: 0; width: 100%">
        <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
    </footer>
</html>