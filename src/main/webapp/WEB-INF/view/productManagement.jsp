<%@ page import="java.util.List" %>
<%@ page import="com.cytech.marketplace.entity.Articles" %>
<%@ page import="com.cytech.marketplace.dao.ArticlesDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des produits</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<%
    List<Articles> articles = ArticlesDAO.getArticles();
%>
<body>

<%@ include file="components/header.jsp" %>

<form action="addProduct" method="get">
    <button type="submit">Ajouter un produit</button>
</form>
<br><br>

<%
    for(Articles a : articles) {
%>
<form action="modifydelete-servlet" method="post">
    Nom du produit : <input name="nom" type="text" value="<%= a.getName() %>" readonly>
    ID du produit : <input name="id" type="text" value="<%= a.getId() %>" readonly>
    <button type="submit" name="modifier">Modifier</button>
    <button type="submit" name="supprimer">Supprimer</button>
</form>
<br>

<%
    }
%>
</body>
</html>
