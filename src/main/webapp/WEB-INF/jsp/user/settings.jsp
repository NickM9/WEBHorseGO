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
    <title><fmt:message bundle="${loc}" key="label.settings"/></title>
    <style>
  	 <jsp:include page="/WEB-INF/css/header.css" />
  	 <jsp:include page="/WEB-INF/css/style.css" />
    </style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>

	<h3><fmt:message bundle="${loc}" key="label.user_settings"/></h3>

	<fmt:message bundle="${loc}" key="label.your_login"/>: <c:out value="${user.login}"/>
	| <a style="color: tomato;" href="${pageContext.request.contextPath}/user/main/login-settings"><fmt:message bundle="${loc}" key="label.change_login"/></a><p/>

	<fmt:message bundle="${loc}" key="label.your_name"/>: <c:out value="${user.name} ${user.surname}"/>
	| <a style="color: tomato;" href="${pageContext.request.contextPath}/user/main/name-settings"><fmt:message bundle="${loc}" key="label.change_name"/></a><p/>

	<fmt:message bundle="${loc}" key="label.password"/> | <a style="color: tomato;" href="${pageContext.request.contextPath}/user/main/password-settings"><fmt:message bundle="${loc}" key="label.change_password"/></a><p/>

	 <a style="background-color: tomato;
                color: white;
                padding: 10px 20px;
                border-radius: 4px;
                text-align: center;
                text-decoration: none;
                display: inline-block;" 
         href="${pageContext.request.contextPath}/user/main/show_start_tables"><fmt:message bundle="${loc}" key="label.back"/></a>



</body>
 <footer style="position: fixed; bottom: 0; width: 100%">
        <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
    </footer>
</html>