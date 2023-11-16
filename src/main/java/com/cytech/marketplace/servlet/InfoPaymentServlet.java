package com.cytech.marketplace.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "infoPaymentServlet", value = "/infoPayment-servlet")
public class InfoPaymentServlet extends HttpServlet {

    private boolean checkEmpty(String nomCarte, String numeroCarte, String dateExpiration, String codeCarte) {
        if((nomCarte == null || nomCarte.isEmpty()) ||
                (numeroCarte == null || numeroCarte.isEmpty()) ||
                (dateExpiration == null || dateExpiration.isEmpty()) ||
                (codeCarte == null || codeCarte.isEmpty())) {
            return true;
        }
        return false;
    }

    private boolean checkValues(String nomCarte, String numeroCarte, String dateExpiration, String codeCarte) {
        return !checkEmpty(nomCarte, numeroCarte, dateExpiration, codeCarte);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nomCarte = req.getParameter("nomCarte");
        String numeroCarte = req.getParameter("numeroCarte");
        String dateExpiration = req.getParameter("dateExpiration");
        String codeCarte = req.getParameter("codeCarte");

        boolean correctValues = checkValues(nomCarte, numeroCarte, dateExpiration, codeCarte);

        //TODO: mettre à jour les stocks des produits commandés

        if(correctValues) {
            req.getRequestDispatcher("/WEB-INF/view/confirmationPayment.jsp").forward(req, resp);
        }
        else {
            req.setAttribute("error", true);
            req.getRequestDispatcher("/WEB-INF/view/infopayment.jsp").forward(req, resp);
        }
    }
}
