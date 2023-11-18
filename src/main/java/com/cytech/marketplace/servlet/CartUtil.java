package com.cytech.marketplace.servlet;

import com.cytech.marketplace.dao.UsersDAO;
import com.cytech.marketplace.entity.Articles;
import com.cytech.marketplace.entity.Users;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class CartUtil {
    /**
     * Get the cart from the session.
     * @param req   The request
     * @return      The cart
     */
    public static Map<Articles, Integer> getCart(HttpServletRequest req) {
        Object cartObject = req.getSession().getAttribute("cart");
        return cartObject == null ? new HashMap<>() : (HashMap<Articles, Integer>) cartObject;
    }

    /**
     * Add an article to the session cart. If the user is logged in, also add the article to the database cart.
     * @param req       The request
     * @param article   The article to add
     * @param quantity  The quantity of the article to add
     */
    public static void addArticleToCart(HttpServletRequest req, Articles article, int quantity) {
        Map<Articles, Integer> cart = getCart(req);
        if (cart.containsKey(article)) {
            cart.put(article, cart.get(article) + quantity);
        } else {
            cart.put(article, quantity);
        }
        req.getSession().setAttribute("cart", cart);

        Users users = (Users) req.getSession().getAttribute("user");
        if (users != null) {
            UsersDAO.addArticleToCart(users, article, quantity);
        }
    }

    /**
     * Merge cart from the session and the database.
     * @param req       The request
     * @param users     The user
     */
    public static void mergeCart(HttpServletRequest req, Users users) {
        Map<Articles, Integer> sessionCart = getCart(req);
        Map<Articles, Integer> databaseCart = UsersDAO.getCart(users);

        for (Map.Entry<Articles, Integer> entry : sessionCart.entrySet()) {
            if (databaseCart.containsKey(entry.getKey())) {
                databaseCart.put(entry.getKey(), entry.getValue() + databaseCart.get(entry.getKey()));
            } else {
                databaseCart.put(entry.getKey(), entry.getValue());
            }
        }

        UsersDAO.setCart(users, databaseCart);
        req.getSession().setAttribute("cart", databaseCart);
    }
}
