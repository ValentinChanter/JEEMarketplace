package com.cytech.marketplace.utils;

import com.cytech.marketplace.dao.ArticlesDAO;
import com.cytech.marketplace.entity.Articles;
import com.cytech.marketplace.entity.Users;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
            UsersUtil.addArticleToCart(users, article, quantity);
        }
    }

    /**
     * Merge cart from the session and the database.
     * @param req       The request
     * @param users     The user
     */
    public static void mergeCart(HttpServletRequest req, Users users) {
        Map<Articles, Integer> sessionCart = getCart(req);
        Map<Articles, Integer> databaseCart = UsersUtil.getCart(users);

        for (Map.Entry<Articles, Integer> entry : sessionCart.entrySet()) {
            if (databaseCart.containsKey(entry.getKey())) {
                databaseCart.put(entry.getKey(), entry.getValue() + databaseCart.get(entry.getKey()));
            } else {
                databaseCart.put(entry.getKey(), entry.getValue());
            }
        }

        UsersUtil.setCart(users, databaseCart);
        req.getSession().setAttribute("cart", databaseCart);
    }

    public static void emptyCart(HttpServletRequest req) {
        req.getSession().removeAttribute("cart");
        Users users = (Users) req.getSession().getAttribute("user");
        if (users != null) {
            UsersUtil.emptyCart(users);
        }
    }

    public static String cartToString(Map<Articles, Integer> cart) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Articles, Integer> entry : cart.entrySet()) {
            sb.append(entry.getKey().getId().toString());
            sb.append(":");
            sb.append(entry.getValue());
            sb.append(",");
        }
        return sb.toString();
    }

    public static Map<Articles, Integer> stringToCart(String cart) {
        Map<Articles, Integer> cartMap = new HashMap<>();

        if (cart == null || cart.isEmpty()) {
            return cartMap;
        }

        String[] cartArray = cart.split(",");
        for (String cartItem : cartArray) {
            String[] cartItemArray = cartItem.split(":");
            Articles article = ArticlesDAO.getArticle(UUID.fromString(cartItemArray[0]));
            int quantity = Integer.parseInt(cartItemArray[1]);
            cartMap.put(article, quantity);
        }
        return cartMap;
    }
}
