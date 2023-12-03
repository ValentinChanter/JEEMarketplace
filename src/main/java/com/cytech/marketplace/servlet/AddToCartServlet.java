package com.cytech.marketplace.servlet;

import com.cytech.marketplace.dao.ArticlesDAO;
import com.cytech.marketplace.entity.Articles;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

import static com.cytech.marketplace.servlet.CartUtil.addArticleToCart;

@WebServlet(name = "addToCartServlet", value = "/addToCart-servlet")
public class AddToCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String productId = req.getParameter("productId");
        String productQty = req.getParameter("productQty");

        UUID productIdUUID = UUID.fromString(productId);
        int qty = Integer.parseInt(productQty);
        Articles product = ArticlesDAO.getArticle(productIdUUID);

        CartUtil.addArticleToCart(req, product, qty);
        req.getRequestDispatcher("/WEB-INF/view/cart.jsp").forward(req, resp);
    }
}
