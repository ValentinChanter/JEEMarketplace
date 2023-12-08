package com.cytech.marketplace.controller;

import com.cytech.marketplace.utils.CartUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.cytech.marketplace.dao.ArticlesDAO;
import com.cytech.marketplace.entity.Articles;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "productPageController", value = "/productPage")
public class ProductPageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String productId = req.getParameter("id");
        Map<Articles, Integer> cart = CartUtil.getCart(req);


        if (productId != null && !productId.isEmpty()) {
            Articles product = ArticlesDAO.getArticle(UUID.fromString(productId));

            if (cart.containsKey(product)) {
                int qtyInCart = cart.get(product);
                req.setAttribute("qty", qtyInCart);
            } else {
                req.setAttribute("qty", 0);
            }

            req.setAttribute("product", product);
            req.getRequestDispatcher("/WEB-INF/view/productPage.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/view/catalogue.jsp").forward(req, resp);
        }
    }
}
