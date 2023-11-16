<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Panier</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>

    </script>
</head>
<body>
<%@ include file="components/header.jsp" %>

<div class="flex flex-row justify-center pt-8">
    <div class="w-5/6 p-8">
        <h2 class="text-2xl font-semibold mb-2">Récapitulatif de vos achats</h2>
        <div class="mb-8 flex w-full flex-col">
            <c:choose>
            <c:when test="${empty sessionScope.cart || sessionScope.cart.size() == 0}">
                <div class="flex flex-col justify-center text-lg font-semibold">Votre panier est vide</div>
            </c:when>
            <c:otherwise>
                <c:forEach items="${sessionScope.cart}" var="item">
                    <div class="flex flex-row justify-between mb-2 shadow-md p-4 rounded-md">
                        <div class="flex flex-row">
                            <img class="mr-8 max-h-[100px]" src="${item.key.getImage()}" alt="${item.key.getName()}" />
                            <div class="flex flex-col justify-center text-lg font-semibold">${item.key.getName()}</div>
                         </div>
                        <div class="flex flex-row">
                            <div class="mx-4 flex flex-col justify-center">
                                <input
                                    id="${item.key.getId()}-input"
                                    class="block w-32 rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-sm text-gray-900 focus:border-none"
                                    value="${item.value}"
                                    type="number"
                                    step="1"
                                    min="0"
                                    max="${item.key.getStock()}"
                                    onkeyup="if(this.value > ${item.key.getStock()}) this.value = ${item.key.getStock()}; if(this.value < 0) this.value = 0;"
                                />
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </div>
        <form action="infopersonnal" method="post">
          <button type="submit" class="w-full rounded-full bg-blue-400 px-5 py-3 text-center text-md font-medium text-white hover:bg-blue-500 focus:outline-none">Procéder au paiement</button>
        </form>
    </div>
</div>
</body>
</html>
