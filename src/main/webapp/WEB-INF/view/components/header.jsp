<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<div class="flex h-[100px] w-full flex-row justify-between p-4 shadow-xl">
    <div class="flex w-48 flex-col justify-center bg-gray-400 text-center">Logo</div>
    <form class="flex h-[calc(100px-2*1rem)] w-[150px] flex-col justify-center" action=
        <c:choose>
            <c:when test="${empty user}">
                "login"
            </c:when>
            <c:otherwise>
                "logout-servlet"
            </c:otherwise>
        </c:choose>
    method="post">
    <button class="rounded-full bg-blue-400 px-4 py-3 text-white hover:bg-blue-500 focus:outline-none" type="submit">
        <c:choose>
            <c:when test="${empty user}">
                Connexion
            </c:when>
            <c:otherwise>
                Déconnexion
            </c:otherwise>
        </c:choose>
    </button>
    </form>
</div>
</body>
</html>