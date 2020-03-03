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
    <title><fmt:message bundle="${loc}" key="label.wallet"/></title>
    <style>
        <jsp:include page="/WEB-INF/css/header.css" />
        <jsp:include page="/WEB-INF/css/style.css" />
    </style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>

    <h3><fmt:message bundle="${loc}" key="label.wallet_balance"/>:<c:out value="${user.wallet}"/></h3><p/>

    <div class="container" style="width: 18%;">
        <form name="walletIncreaseForm" action="controller" method="post">
    	    <input type="hidden" name="command" value="increase_wallet">
    	   <fmt:message bundle="${loc}" key="label.increase_amount"/>:
           <div class="row">
                <div class="col-75">
    	           <input type="number" name="sum" min=0 step="0.01" required>
               </div>
    	       <input type="submit" value="<fmt:message bundle='${loc}' key='label.ok'/>">
            </div>
        </form><p/>
    

        <form name="walletDecreaseForm" action="controller" method="post">
            <input type="hidden" name="command" value="decrease_wallet">
            <fmt:message bundle="${loc}" key="label.decrease_amount"/>:
            <div class="row">
                <div class="col-75">
                    <input type="number" name="sum" min=0 step="0.01" required>
                </div>
                <input type="submit" value="<fmt:message bundle='${loc}' key='label.ok'/>">
            </div>
        </form>
    </div>
    
    <c:out value="${walletMessage}"/><p/>
    <c:remove var="walletMessage" scope="session"/>

    <a style=" color: tomato;" href="${pageContext.request.contextPath}/user/main/show_start_tables"><fmt:message bundle="${loc}" key="label.back"/></a>

    
    <footer style="position: fixed; bottom: 0; width: 100%">
        <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
    </footer>
</body>

</html>