package com.cytech.marketplace.servlet;

import com.cytech.marketplace.dao.ArticlesDAO;
import com.cytech.marketplace.dao.UsersDAO;
import com.cytech.marketplace.entity.Articles;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "modifyDeleteServlet", value = "/modifydelete-servlet")
public class ModifyDeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object boutonModifier = req.getParameter("modifier");
        Object boutonSupprimer = req.getParameter("supprimer");

        String id = req.getParameter("id");
        Articles produit = ArticlesDAO.getArticle(UUID.fromString(id));

        if(!(boutonModifier == null)) {
            req.setAttribute("produit", produit);
            req.getRequestDispatcher("/WEB-INF/view/modifyProduct.jsp").forward(req, resp);
        }
        if(!(boutonSupprimer == null)) {
            ArticlesDAO.deleteArticle(produit);
            req.getRequestDispatcher("/WEB-INF/view/productManagement.jsp").forward(req, resp);
        }
    }
}
