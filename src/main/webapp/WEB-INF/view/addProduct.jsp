<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajouter un produit</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<%
    boolean error = false;
    Object errorObject = request.getAttribute("error");

    if(errorObject != null) {
        error = (boolean) errorObject;
    }
%>
<body>

<%@ include file="components/header.jsp" %>

<h1>Ajouter un produit</h1>
<%
    if(error) {
%>

<p style="color: red">Il y a une ou plusieurs erreur(s) dans la saisie des informations du produit.</p>

<%
    }
%>
<form method="post" action="addProduct-servlet">
    Nom du produit : <br>
    <input type="text" name="nom">
    <br><br>

    Prix du produit : <br>
    <input type="text" name="prix">
    <br><br>

    Stock du produit : <br>
    <input type="text" name="stock">
    <br><br>

    Image du produit : <br>
    <input type="text" name="image">
    <br><br>

    <input type="submit" value="Ajouter">
</form>
</body>
</html>
