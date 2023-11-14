package com.cytech.marketplace.servlet;

import com.cytech.marketplace.dao.ArticlesDAO;
import com.cytech.marketplace.entity.Articles;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "updateCartServlet", value = "/update-cart-servlet")
public class UpdateCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String articleId = id.substring(0, id.lastIndexOf("-"));
        int quantity = Integer.parseInt(req.getParameter("count"));

        System.out.println(articleId);
        System.out.println(quantity);

        if (ArticlesDAO.checkStock(UUID.fromString(articleId), quantity)) {
            Object cartObject = req.getSession().getAttribute("cart");
            Map<Articles, Integer> cart = cartObject == null ? new HashMap<>() : (HashMap<Articles, Integer>) cartObject;
            Articles article = ArticlesDAO.getArticle(UUID.fromString(articleId));
            cart.put(article, quantity);
            req.getSession().setAttribute("cart", cart);

            resp.setStatus(200);
        } else resp.setStatus(400);
    }
}
