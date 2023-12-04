<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajouter un produit</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<%
    boolean error = false;
    Object errorObject = request.getAttribute("error");
    request.removeAttribute("error");

    if(errorObject != null) {
        error = (boolean) errorObject;
    }
%>
<body>

<%@ include file="components/header.jsp" %>

<h1 class="text-5xl font-extrabold dark:text-black px-8 pt-5">Ajouter un produit</h1>

<form action="productManagement" method="get" class="px-8 pt-5">
    <button type="submit" class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded inline-flex items-center">
        Retourner à la page de gestion des produits
    </button>
</form>

<%
    if(error) {
%>

<p class="text-red-600 text-xl font-extrabold px-8 py-2">Il y a une ou plusieurs erreur(s) dans la saisie des informations du produit.</p>

<%
    }
%>

<div class="w-full max-w-md">
    <form method="post" action="addProduct-servlet" class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
        <label class="block text-gray-700 text-sm font-bold mb-2">Nom du produit :</label>
        <input type="text" name="nom" <c:if test="${not empty sessionScope.productInformation.get('nom')}">value="${sessionScope.productInformation.get('nom')}"</c:if> class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
        <br><br>

        <label class="block text-gray-700 text-sm font-bold mb-2">Prix du produit :</label>
        <input type="text" name="prix" <c:if test="${not empty sessionScope.productInformation.get('prix')}">value="${sessionScope.productInformation.get('prix')}"</c:if> class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
        <br><br>

        <label class="block text-gray-700 text-sm font-bold mb-2">Stock du produit :</label>
        <input type="text" name="stock" <c:if test="${not empty sessionScope.productInformation.get('stock')}">value="${sessionScope.productInformation.get('stock')}"</c:if> class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
        <br><br>

        <label class="block text-gray-700 text-sm font-bold mb-2">Image du produit :</label>
        <input type="text" name="image" <c:if test="${not empty sessionScope.productInformation.get('image')}">value="${sessionScope.productInformation.get('image')}"</c:if> class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
        <br><br>

        <button type="submit" class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded inline-flex items-center">
            Ajouter
        </button>
    </form>
</div>
</body>
</html>
