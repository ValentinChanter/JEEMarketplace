package com.cytech.marketplace.servlet;

import com.cytech.marketplace.dao.ArticlesDAO;
import com.cytech.marketplace.entity.Articles;
import com.cytech.marketplace.utils.CartUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;
import java.util.UUID;

import static java.lang.Math.abs;


@WebServlet(name = "addToCartServlet", value = "/addToCart-servlet")
public class AddToCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String productId = req.getParameter("productId");
        String productQty = req.getParameter("productQty");
        Map<Articles, Integer> cart = CartUtil.getCart(req);

        UUID productIdUUID = UUID.fromString(productId);
        int qty = Integer.parseInt(productQty);
        Articles product = ArticlesDAO.getArticle(productIdUUID);
        if (cart.containsKey(product)) {
            int qtyInCart = cart.get(product);
            int toPutInCart = qty + qtyInCart;
            if (product.getStock().compareTo(BigInteger.valueOf(toPutInCart)) < 0){
                int correctedQty = abs(qtyInCart - product.getStock().intValue());
                CartUtil.addArticleToCart(req, product, correctedQty);
            } else {
            CartUtil.addArticleToCart(req, product, qty);
            }
        } else {
            CartUtil.addArticleToCart(req, product, qty);
        }
        req.getRequestDispatcher("/WEB-INF/view/cart.jsp").forward(req, resp);
    }
}
