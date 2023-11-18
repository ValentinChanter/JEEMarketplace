package com.cytech.marketplace.servlet;

import com.cytech.marketplace.dao.ArticlesDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@WebServlet(name = "checkStockPostCartServlet", value = "/checkStockPostCart-servlet")
public class CheckStockPostCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<String> parameterNames = req.getParameterMap().keySet();

        for (String parameterName : parameterNames) {
            String value = req.getParameter(parameterName);
            String name = parameterName.substring(0, parameterName.indexOf("-input"));
            if (!ArticlesDAO.checkStock(UUID.fromString(name), Integer.parseInt(value))) {
                req.setAttribute("error", true);
                req.getRequestDispatcher("/WEB-INF/view/cart.jsp").forward(req, resp);
                return;
            }
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/infopersonnal");
    }
}
