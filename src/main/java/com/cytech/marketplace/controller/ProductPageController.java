package com.cytech.marketplace.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.cytech.marketplace.dao.ArticlesDAO;
import com.cytech.marketplace.entity.Articles;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "productPageController", value = "/productPage")
public class ProductPageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String productId = req.getParameter("id");

        if (productId != null && !productId.isEmpty()) {
            Articles product = ArticlesDAO.getArticle(UUID.fromString(productId));

            req.setAttribute("product", product);
            req.getRequestDispatcher("/WEB-INF/view/productPage.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/view/catalogue.jsp").forward(req, resp);
        }
    }
}
