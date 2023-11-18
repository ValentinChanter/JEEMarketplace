package com.cytech.marketplace.servlet;

import com.cytech.marketplace.entity.Articles;
import com.cytech.marketplace.entity.Users;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "logoutServlet", value = "/logout-servlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Users users = (Users) req.getSession().getAttribute("user");
        Map<Articles, Integer> cart = CartUtil.getCart(req);

        if (users != null) {
            if (!cart.isEmpty()) {
                req.getSession().removeAttribute("cart");
            }

            req.getSession().removeAttribute("user");
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/home");
    }
}
