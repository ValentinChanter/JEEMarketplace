<%@ page import="com.cytech.marketplace.entity.Articles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Détails du Produit</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>

<%Articles product = ((Articles)request.getAttribute("product"));%>

<body>
    <%@ include file="components/header.jsp" %>

    <div class="mx-5 my-10 ">
        <div class="w-full flex items-start">
            <img src="<%= product.getImage() %>" alt="product image" class="w-1/3 mr-4">
            <div class="flex flex-col justify-start bg-blue-50 p-4">
                <h2 class="text-xl font-semibold mb-4"><%=product.getName() %></h2>
                <div class="flex items-center">
                    <p class="text-l font-bold mt-4"><%= product.getPrice() %>€</p>
                    <div class="ml-80">
                        <!--Bouton à relier-->
                        <button class="rounded-full bg-blue-400 px-4 py-3 text-white hover:bg-blue-500 focus:outline-none" type="submit">
                            Ajouter au panier
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>


</body>
</html>


