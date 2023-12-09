package com.cytech.marketplace.servlet;

import com.cytech.marketplace.dao.ArticlesDAO;
import com.cytech.marketplace.entity.Articles;
import com.cytech.marketplace.utils.CheckIntFloat;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@WebServlet(name = "modifyProductServlet", value = "/modifyProduct-servlet")
public class ModifyProductServlet extends HttpServlet {

    private boolean checkEmpty(String nom, String prix, String stock, String image) {
        if((nom == null || nom.isEmpty()) ||
                (prix == null || prix.isEmpty()) ||
                (stock == null || stock.isEmpty()) ||
                (image == null || image.isEmpty())) {
            return true;
        }
        return false;
    }

    private boolean checkValues(String nom, String prix, String stock, String image) {
        return !checkEmpty(nom, prix, stock, image) && CheckIntFloat.checkInt(stock) && CheckIntFloat.checkFloat(prix);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nom = req.getParameter("nom");
        String prix = req.getParameter("prix");
        String stock = req.getParameter("stock");
        String image = req.getParameter("image");
        String id = req.getParameter("id");

        boolean correctValues = checkValues(nom, prix, stock, image);

        if(correctValues) {
            Articles modifiedProduct = new Articles(nom, new BigDecimal(prix), new BigInteger(stock), image);
            modifiedProduct.setId(UUID.fromString(id));
            ArticlesDAO.updateArticle(modifiedProduct);
            req.getRequestDispatcher("/WEB-INF/view/productManagement.jsp").forward(req, resp);
        }
        else {
            Articles modifiedProduct = ArticlesDAO.getArticle(UUID.fromString(id));
            req.setAttribute("produit", modifiedProduct);
            req.setAttribute("error", true);
            req.getRequestDispatcher("/WEB-INF/view/modifyProduct.jsp").forward(req, resp);
        }
    }
}
