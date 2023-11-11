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

<h1 class="text-5xl font-extrabold dark:text-black px-8 pt-5">Page de gestion des produits</h1>

<br>
<form action="addProduct" method="get" class="px-8">
    <button type="submit" class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-4 px-8 text-xl rounded inline-flex items-center">Ajouter un produit</button>
</form>

<div class="flex flex-col">
    <div class="overflow-x-auto">
        <div class="inline-block min-w-full py-2 sm:px-6 lg:px-8">
            <div class="overflow-hidden">
                <table class="min-w-full text-left text-sm font-light">

                    <thead class="border-b font-medium dark:border-neutral-500">
                        <tr>
                <%--            <th scope="col" class="px-6 py-4">Image</th>--%>
                            <th scope="col" class="px-6 py-4">Nom</th>
                            <th scope="col" class="px-6 py-4">ID</th>
                            <th scope="col" class="px-6 py-4">Prix</th>
                            <th scope="col" class="px-6 py-4">Stock</th>
                            <th scope="col" class="px-6 py-4">Modifier produit</th>
                            <th scope="col" class="px-6 py-4">Supprimer produit</th>
                        </tr>
                    </thead>

                    <%
                        for(Articles a : articles) {
                    %>

                    <form action="modifydelete-servlet" method="post">
                        <tr class="border-b dark:border-neutral-500">
                <%--            <td class="whitespace-nowrap px-6 py-4"> <%= a.getImage() %> </td>--%>
                            <td class="whitespace-nowrap px-6 py-4"> <input name="nom" type="text" value='<%= a.getName() %>' readonly> </td>
                            <td class="whitespace-nowrap px-6 py-4"> <input name="id" type="text" value="<%= a.getId() %>" readonly size="30"> </td>
                            <td class="whitespace-nowrap px-6 py-4"> <%= a.getPrice() %>€ </td>
                            <td class="whitespace-nowrap px-6 py-4"> <%= a.getStock() %> </td>
                            <td class="whitespace-nowrap px-6 py-4"><button type="submit" name="modifier" class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded inline-flex items-center">Modifier</button></td>
                            <td class="whitespace-nowrap px-6 py-4"><button type="submit" name="supprimer" class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded inline-flex items-center">Supprimer</button></td>
                        </tr>
                    </form>

                    <%
                        }
                    %>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
