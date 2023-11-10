<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Accueil</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <%@ include file="components/header.jsp" %>
    <form action="productManagement" method="get">
    <button class="rounded-full bg-blue-400 px-4 py-3 text-white hover:bg-blue-500 focus:outline-none" type="submit">
        Gestion des produits
    </button>
    </form>
</body>
</html>
