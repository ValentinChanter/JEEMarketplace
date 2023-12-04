package com.cytech.marketplace.servlet;

import com.cytech.marketplace.dao.UsersDAO;
import com.cytech.marketplace.entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Properties;

import javax.mail.*;

@WebServlet(name = "signupServlet", value = "/signup-servlet")
public class SignupServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("fullname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "Les mots de passe ne correspondent pas.");
            req.getRequestDispatcher("/WEB-INF/view/signup.jsp").forward(req, resp);
            return;
        }

        if (UsersDAO.getUser(email) != null) {
            req.setAttribute("error", "Un compte avec cet email existe déjà.");
            req.getRequestDispatcher("/WEB-INF/view/signup.jsp").forward(req, resp);
            return;
        }

        Users users = new Users();
        users.setName(fullName);
        users.setEmail(email);
        users.setPassword(password);
        users.setAdmin(false);
        users.setLoyaltyPoints(BigInteger.ZERO);
        UsersDAO.addUser(users);

        final String from = "marketplace.root@gmail.com";
        final String pw = "aibygnesrjnpgnbj";
        final String host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pw);
            }
        };

        Session session = Session.getDefaultInstance(properties, auth);
        EmailUtil.sendEmail(session, email, "Bienvenue sur WA'ER", "Bonjour " + fullName + ",\n\n" +
                "Nous vous souhaitons la bienvenue sur WA'ER. Vous pouvez dès à présent vous connecter à votre compte.\n\n" +
                "Cordialement,\n" +
                "L'équipe de WA'ER");

        req.getSession().setAttribute("user", UsersDAO.getUser(email));
        resp.sendRedirect(getServletContext().getContextPath() + "/home");
    }
}
