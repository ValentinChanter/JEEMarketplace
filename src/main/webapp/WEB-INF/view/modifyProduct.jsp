<%@ page import="com.cytech.marketplace.entity.Articles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modifier un produit</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<%
    boolean error = false;
    Object errorObject = request.getAttribute("error");

    if(errorObject != null) {
        error = (boolean) errorObject;
    }

    Object produitObjet = request.getAttribute("produit");
    Articles produit = null;

    if(produitObjet != null) {
        produit = (Articles) produitObjet;
    }
%>
<body>

<%@ include file="components/header.jsp" %>

<h1 class="text-5xl font-extrabold dark:text-black px-8 pt-5">Modifier un produit</h1>

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
    <form method="post" action="modifyProduct-servlet" class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
        <label class="block text-gray-700 text-sm font-bold mb-2">Nom du produit :</label>
        <input type="text" name="nom" value="<%= produit.getName() %>" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
        <br><br>

        <label class="block text-gray-700 text-sm font-bold mb-2">Prix du produit :</label>
        <input type="text" name="prix" value="<%= produit.getPrice() %>" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
        <br><br>

        <label class="block text-gray-700 text-sm font-bold mb-2">Stock du produit :</label>
        <input type="text" name="stock" value="<%= produit.getStock() %>" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
        <br><br>

        <label class="block text-gray-700 text-sm font-bold mb-2">Image du produit :</label>
        <input type="text" name="image" value="<%= produit.getImage() %>" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
        <br><br>

        <label class="block text-gray-700 text-sm font-bold mb-2">ID du produit :</label>
        <input type="text" name="id" value="<%= produit.getId() %>" readonly class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline bg-slate-400">
        <br><br>

        <button type="submit" class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded inline-flex items-center">
            Modifier
        </button>
    </form>
</div>
</body>
</html>
