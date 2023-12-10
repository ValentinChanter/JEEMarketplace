package com.cytech.marketplace.servlet;

import com.cytech.marketplace.dao.ArticlesDAO;
import com.cytech.marketplace.entity.Articles;
import com.cytech.marketplace.entity.Users;
import com.cytech.marketplace.utils.CartUtil;
import com.cytech.marketplace.utils.EmailUtil;
import com.cytech.marketplace.utils.UsersUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Properties;

@WebServlet(name = "infoPaymentServlet", value = "/infoPayment-servlet")
public class InfoPaymentServlet extends HttpServlet {

    private boolean checkEmpty(String nomCarte, String numeroCarte, String dateExpiration, String codeCarte, Object personnalInformationObject) {
        if((nomCarte == null || nomCarte.isEmpty()) ||
                (numeroCarte == null || numeroCarte.isEmpty()) ||
                (dateExpiration == null || dateExpiration.isEmpty()) ||
                (codeCarte == null || codeCarte.isEmpty()) ||
                (personnalInformationObject == null)) {
            return true;
        }
        return false;
    }

    private boolean checkLuhn(String numeroCarte) {
        String numeroCarteSansEspace = numeroCarte.replaceAll("\\s+", "");
        int somme = 0;
        boolean pair = false;
        for (int i = numeroCarteSansEspace.length() - 1; i >= 0; i--) {
            int chiffre = Integer.parseInt(numeroCarteSansEspace.substring(i, i + 1));
            if (pair) {
                chiffre *= 2;
                if (chiffre > 9) {
                    chiffre -= 9;
                }
            }
            somme += chiffre;
            pair = !pair;
        }
        return (somme % 10 == 0);
    }

    private boolean checkValues(String nomCarte, String numeroCarte, String dateExpiration, String codeCarte, Object personnalInformationObject) {
        return !checkEmpty(nomCarte, numeroCarte, dateExpiration, codeCarte, personnalInformationObject) && checkLuhn(numeroCarte);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // On récupère les champs du formulaire avec les informations de paiement
        String nomCarte = req.getParameter("nomCarte");
        String numeroCarte = req.getParameter("numeroCarte");
        String dateExpiration = req.getParameter("dateExpiration");
        String codeCarte = req.getParameter("codeCarte");
        String usePointsString = req.getParameter("usePoints");

        // On récupère l'attribut permettant de savoir si l'utilisateur a utilisé ses points ou non
        boolean usePoints = false;
        if(usePointsString != null) {
            usePoints = usePointsString.equals("on");
        }

        // On récupère les informations entrées par l'utilisateur sur la page infopersonnal.jsp
        Object personnalInformationObject = req.getSession().getAttribute("personnalInformation");

        // On vérifie si l'ensemble des informations récupérées sont correctes ou non
        boolean correctValues = checkValues(nomCarte, numeroCarte, dateExpiration, codeCarte, personnalInformationObject);

        if(correctValues) {
            Users user = (Users) req.getSession().getAttribute("user");
            Map<String, String> personnalInformation = (Map<String, String>) personnalInformationObject;
            Map<Articles, Integer> cart = CartUtil.getCart(req);

            // Pour chaque article, on modifie son stock
            for (Map.Entry<Articles, Integer> article : cart.entrySet()) {
                Articles modifiedArticle = article.getKey();
                modifiedArticle.setStock(BigInteger.valueOf(modifiedArticle.getStock().intValue() - article.getValue()));
                ArticlesDAO.updateArticle(modifiedArticle);
            }

            BigDecimal total = (BigDecimal) req.getSession().getAttribute("total");
            BigInteger loyaltyPoints = new BigInteger(String.valueOf(0));
            if (usePoints) {
                loyaltyPoints = user.getLoyaltyPoints();
                total = total.subtract(new BigDecimal(user.getLoyaltyPoints()).divide(new BigDecimal(100)));
                UsersUtil.removeLoyaltyPoints(user, user.getLoyaltyPoints().subtract(total.toBigInteger()));
            } else {
                UsersUtil.addLoyaltyPoints(user, total.intValue());
            }

            // Envoie de l'email récapitulatif
            EmailUtil.sendRecapMail(user, cart, personnalInformation, total, loyaltyPoints);

            // Supprimer toutes les variables de session qui ne sont plus utiles
            CartUtil.emptyCart(req);
            req.getSession().removeAttribute("total");
            req.getSession().removeAttribute("personnalInformation");
            req.getSession().removeAttribute("error");

            req.getRequestDispatcher("/WEB-INF/view/confirmationPayment.jsp").forward(req, resp);
        }
        else {
            if (!checkLuhn(numeroCarte)) {
                req.setAttribute("error", "Votre numéro de carte n'est pas valide");
            } else {
                req.setAttribute("error", "Veuillez remplir tous les champs");
            }
            req.getRequestDispatcher("/WEB-INF/view/infopayment.jsp").forward(req, resp);
        }
    }
}
