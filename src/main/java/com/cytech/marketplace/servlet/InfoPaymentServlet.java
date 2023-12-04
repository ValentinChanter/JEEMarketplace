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

    private String cartRecapString(Map<Articles, Integer> cart) {
        String cartRecap = "";
        cartRecap += "Récapitulatif du panier :\n";
        cartRecap += "Article | Quantité | Prix unitaire\n";
        double prixTotal = 0;

        for (Map.Entry<Articles, Integer> article : cart.entrySet()) {
            Articles currentArticle = article.getKey();
            int articleQuantite = article.getValue();

            cartRecap += currentArticle.getName() + " | " + articleQuantite + " | " + currentArticle.getPrice() + "\n";

            prixTotal += currentArticle.getPrice().doubleValue() * articleQuantite;
        }

        // TODO : enlever la réduction des points de fidélité
        cartRecap += "\nPrix total : " + prixTotal + "€";

        return cartRecap;
    }

    private String shippingInformation(Map<String, String> personnalInformation) {
        String shippingInformationString = "";
        shippingInformationString += "Informations de livraison :\n";
        shippingInformationString += "Nom complet : " + personnalInformation.get("nomComplet") + "\n";
        shippingInformationString += "Adresse de livraison : " + personnalInformation.get("adresse") + "\n";
        shippingInformationString += "Numéro de téléphone : " + personnalInformation.get("telephone") + "\n";

        return shippingInformationString;
    }

    private String bodyEmail(Users user, Map<Articles, Integer> cart, Map<String, String> personnalInformation) {
        String body = "Bonjour " + user.getName() + ",\n\n" +
                "Nous vous remercions pour votre achat. Voici un récapitulatif de celui-ci :\n" +
                "\n" +
                shippingInformation(personnalInformation) +
                "\n" +
                cartRecapString(cart) + "\n\n" +
                "Cordialement,\n" +
                "L'équipe WA'ER";

        return body;
    }

    private void sendRecapMail(Users user, Map<Articles, Integer> cart, Map<String, String> personnalInformation) {
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
        EmailUtil.sendEmail(session, user.getEmail(), "Récapitulatif de paiement", bodyEmail(user, cart, personnalInformation));
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
            if (usePoints) {
                total = total.subtract(new BigDecimal(user.getLoyaltyPoints()).divide(new BigDecimal(100)));
                UsersUtil.removeLoyaltyPoints(user, user.getLoyaltyPoints().subtract(total.toBigInteger()));
            } else {
                UsersUtil.addLoyaltyPoints(user, total.intValue());
            }

            // Envoie de l'email récapitulatif
            sendRecapMail(user, cart, personnalInformation);

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
