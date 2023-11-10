<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connexion</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <%@ include file="components/header.jsp" %>

    <div class="flex flex-col justify-center min-h-[calc(100vh-100px)]">
        <div class="flex flex-row justify-center">
            <div class="w-1/2 rounded-md p-8 shadow-md">
                <c:if test="${not empty error}">
                    <div class="mb-4 rounded-md bg-red-400 p-2.5 text-center text-sm font-medium text-white">${error}</div>
                </c:if>
                <form class="mb-10" action="auth-servlet" method="POST">
                    <div class="mb-6">
                        <label for="email" class="mb-2 block text-sm font-medium text-gray-900">Email</label>
                        <input name="email" id="email" type="email" <c:if test="${not empty error}">value="${email}"</c:if> class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-sm text-gray-900 focus:border-none" required />
                    </div>
                    <div class="mb-6">
                        <label for="password" class="mb-2 block text-sm font-medium text-gray-900">Mot de passe</label>
                        <input name="password" id="password" type="password" class="block w-full rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-sm text-gray-900 focus:border-none" required />
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
