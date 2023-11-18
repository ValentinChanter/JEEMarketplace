package com.cytech.marketplace.servlet;

import com.cytech.marketplace.dao.ArticlesDAO;
import com.cytech.marketplace.dao.UsersDAO;
import com.cytech.marketplace.entity.Articles;
import com.cytech.marketplace.entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@WebServlet(name = "checkStockPostCartServlet", value = "/checkStockPostCart-servlet")
public class CheckStockPostCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<String> parameterNames = req.getParameterMap().keySet();
        Users users = (Users) req.getSession().getAttribute("user");
        Map<Articles, Integer> cart = CartUtil.getCart(req);

        for (String parameterName : parameterNames) {
            String value = req.getParameter(parameterName);
            String name = parameterName.substring(0, parameterName.indexOf("-input"));
            Articles article = ArticlesDAO.getArticle(UUID.fromString(name));

            if (Integer.parseInt(value) == 0) {
                cart.remove(article);
                if (cart.isEmpty()) {
                    if (users != null) {
                        UsersDAO.setCart(users, null);
                    }
                    req.getRequestDispatcher("/WEB-INF/view/cart.jsp").forward(req, resp);
                    return;
                }

                continue;
            }

            if (!ArticlesDAO.checkStock(UUID.fromString(name), Integer.parseInt(value))) {
                req.setAttribute("error", "Une erreur de stock est survenue. Veuillez réessayer.");
                req.getRequestDispatcher("/WEB-INF/view/cart.jsp").forward(req, resp);
                return;
            }

            for (Map.Entry<Articles, Integer> entry : cart.entrySet()) {
                if (entry.getKey().equals(article)) {
                    entry.setValue(Integer.parseInt(value));
                }
            }
        }

        if (users != null) {
            UsersDAO.setCart(users, cart);
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/infopersonnal");
    }
}
