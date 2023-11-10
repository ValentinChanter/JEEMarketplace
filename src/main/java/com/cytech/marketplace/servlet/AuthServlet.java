package com.cytech.marketplace.servlet;

import com.cytech.marketplace.dao.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "authServlet", value = "/auth-servlet")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (UsersDAO.login(req.getParameter("email"), req.getParameter("password"))) {
            req.getSession().setAttribute("user", UsersDAO.getUser(req.getParameter("email")));
            resp.sendRedirect(getServletContext().getContextPath() + "/home");
        } else {
            req.setAttribute("error", "Email ou mot de passe incorrect. Veuillez réessayer.");
            req.setAttribute("email", req.getParameter("email"));
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
        }
    }
}
