package com.cytech.marketplace.controller;

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

@WebServlet(name = "cartController", value = "/cart")
public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/WEB-INF/view/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Object cartObject = req.getSession().getAttribute("cart");
        Map<Articles, Integer> cart = cartObject == null ? new HashMap<>() : (HashMap<Articles, Integer>) cartObject;
        Articles vittel = ArticlesDAO.getArticle("Bouteille 1L Vittel");
        cart.put(vittel, 5);
        Articles evian = ArticlesDAO.getArticle("Bouteille 1L Evian");
        cart.put(evian, 5);
        req.getSession().setAttribute("cart", cart);

        req.getRequestDispatcher("/WEB-INF/view/cart.jsp").forward(req, resp);
    }
}
