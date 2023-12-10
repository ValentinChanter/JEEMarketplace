<%@ page import="java.util.List" %>
<%@ page import="com.cytech.marketplace.entity.Users" %>
<%@ page import="com.cytech.marketplace.dao.UsersDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestion des points de fidélité</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<%
    List<Users> users = UsersDAO.getAllUsers();
%>
<body>

<%@ include file="components/header.jsp" %>

<h1 class="text-5xl font-extrabold dark:text-black px-8 pt-5">Page de gestion des points de fidélité</h1>

<div class="w-4/5 bg-white shadow flex justify-between items-center py-5">
    <div class="h-full w-full flex items-center">
        <form action="productManagement" method="get">
            <button type="submit" class="ml-2 px-10 bg-blue-500 hover:bg-blue-600 text-white font-bold rounded">
                Gestion des produits
            </button>
        </form>
        <form action="fidelityPointsManagement" method="get">
            <button type="submit" class="ml-2 px-10 bg-gray-300 hover:bg-gray-400 text-white font-bold rounded" disabled>
                Gestion des points de fidélité
            </button>
        </form>
    </div>
</div>

<p class="dark:text-black px-8 pt-5">Vous pouvez modifier le nombre de points de fidélité des utilisateurs, puis appuyer sur le bouton Valider pour que les changements soient effectifs.</p>

<br>

<div class="flex flex-col">
    <div class="overflow-x-auto">
        <div class="inline-block min-w-full py-2 sm:px-30 lg:px-32">
            <div class="overflow-hidden">
                <table class="min-w-full text-left text-sm font-light">

                    <thead class="border-b font-medium dark:border-neutral-500">
                        <tr>
                            <th scope="col" class="px-6 py-4">Nom complet</th>
                            <th scope="col" class="px-6 py-4">ID</th>
                            <th scope="col" class="px-6 py-4">Adresse email</th>
                            <th scope="col" class="px-6 py-4">Points de fidélité</th>
                        </tr>
                    </thead>

                    <form action="updateFidelityPointsManagement-servlet" method="post">

                        <button type="submit" class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded inline-flex items-center">
                            Valider
                        </button>

                        <%
                            for(Users user : users) {
                        %>

                        <tr class="border-b dark:border-neutral-500">
                            <td class="whitespace-nowrap px-6 py-4"> <%= user.getName() %> </td>
                            <td class="whitespace-nowrap px-6 py-4"> <%= user.getId() %> </td>
                            <td class="whitespace-nowrap px-6 py-4"> <%= user.getEmail() %> </td>
                            <td class="whitespace-nowrap px-6 py-4">
                                <input
                                        type="number"
                                        class="block w-32 rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-sm text-gray-900 focus:border-none"
                                        name="<%= user.getId() %>"
                                        value="<%= user.getLoyaltyPoints() %>"
                                        step="1"
                                        min="0"
                                >
                            </td>
                        </tr>

                        <%
                            }
                        %>

                    </form>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
