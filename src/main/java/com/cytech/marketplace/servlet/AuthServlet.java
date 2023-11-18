package com.cytech.marketplace.servlet;

import com.cytech.marketplace.dao.UsersDAO;
import com.cytech.marketplace.entity.Articles;
import com.cytech.marketplace.entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "authServlet", value = "/auth-servlet")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (UsersDAO.login(req.getParameter("email"), req.getParameter("password"))) {
            Users users = UsersDAO.getUser(req.getParameter("email"));
            assert users != null;

            req.getSession().setAttribute("user", users);

            Map<Articles, Integer> cart = CartUtil.getCart(req);
            if (!cart.isEmpty() && !UsersDAO.getCart(users).isEmpty()) {
                CartUtil.mergeCart(req, users);
            } else if (!cart.isEmpty()) {
                UsersDAO.setCart(users, cart);
            } else if (!UsersDAO.getCart(users).isEmpty()) {
                req.getSession().setAttribute("cart", UsersDAO.getCart(users));
            }

            resp.sendRedirect(getServletContext().getContextPath() + "/home");
        } else {
            req.setAttribute("error", "Email ou mot de passe incorrect. Veuillez réessayer.");
            req.setAttribute("email", req.getParameter("email"));
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
        }
    }
}
