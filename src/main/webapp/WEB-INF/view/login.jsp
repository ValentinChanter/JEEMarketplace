<%--
  Created by IntelliJ IDEA.
  User: cytech
  Date: 05/11/2023
  Time: 22:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connexion</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <div class="flex flex-col justify-center min-h-[100vh]">
        <div class="flex flex-row justify-center">
            <div class="w-1/2 rounded-md p-8 shadow-md">
                <form class="mb-10">
                    <div class="mb-6">
                        <label for="email" class="mb-2 block text-sm font-medium text-gray-900">Email</label>
                        <input id="email" type="email" class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-sm text-gray-900 focus:border-none" required />
                    </div>
                    <div class="mb-6">
                        <label for="password" class="mb-2 block text-sm font-medium text-gray-900">Password</label>
                        <input id="password" type="password" class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-sm text-gray-900 focus:border-none" required />
                    </div>
                    <button type="submit" class="w-full rounded-full bg-blue-400 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-blue-500 focus:outline-none">Se connecter</button>
                </form>
                <form>
                    <span class="mb-4 block text-sm font-medium text-gray-900">Pas encore inscrit ?</span>
                    <button type="submit" class="w-full rounded-full bg-blue-400 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-blue-500 focus:outline-none">S'inscrire</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
