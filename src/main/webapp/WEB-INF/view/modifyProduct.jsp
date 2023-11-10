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

<h1>Modifier un produit</h1>
<%
    if(error) {
%>

<p style="color: red">Il y a une ou plusieurs erreur(s) dans la saisie des informations du produit.</p>

<%
    }
%>
<form method="post" action="modifyProduct-servlet">
    Nom du produit : <br>
    <input type="text" name="nom" value="<%= produit.getName() %>">
    <br><br>

    Prix du produit : <br>
    <input type="text" name="prix" value="<%= produit.getPrice() %>">
    <br><br>

    Stock du produit : <br>
    <input type="text" name="stock" value="<%= produit.getStock() %>">
    <br><br>

    Image du produit : <br>
    <input type="text" name="image" value="<%= produit.getImage() %>">
    <br><br>

    ID du produit : <br>
    <input type="text" name="id" value="<%= produit.getId() %>" readonly>
    <br><br>

    <input type="submit" value="Modifier">
</form>
</body>
</html>
