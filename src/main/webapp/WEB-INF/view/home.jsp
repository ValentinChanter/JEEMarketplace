<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <div class="flex h-[100px] w-full flex-row justify-between p-4 shadow-xl">
        <div class="flex w-48 flex-col justify-center bg-gray-400 text-center">Logo</div>
        <form class="rounded-full bg-blue-400 text-white my-2 px-4 flex flex-col justify-center" action="login-controller">
            <button type="submit">Connexion</button>
        </form>
    </div>
</body>
</html>
