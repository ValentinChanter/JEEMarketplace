package com.cytech.marketplace.utils;

import com.cytech.marketplace.dao.UsersDAO;
import com.cytech.marketplace.entity.Articles;
import com.cytech.marketplace.entity.Users;

import java.math.BigInteger;
import java.util.Map;

import static com.cytech.marketplace.utils.CartUtil.cartToString;
import static com.cytech.marketplace.utils.CartUtil.stringToCart;

public class UsersUtil {
    public static void setCart(Users user, Map<Articles, Integer> cart) {
        user.setCart(cart == null ? null : cartToString(cart));
        UsersDAO.updateUser(user);
    }

    public static Map<Articles, Integer> getCart(Users user) {
        return stringToCart(user.getCart());
    }

    public static void emptyCart(Users user) {
        setCart(user, null);
    }

    /**
     * Add an article to the cart and updates the one in the database. If it already exists, sums the quantities.
     * @param user      The user to add the article to the cart.
     * @param article   The article to add to the cart.
     * @param quantity  The quantity of the article to add to the cart.
     */
    public static void addArticleToCart(Users user, Articles article, int quantity) {
        Map<Articles, Integer> cart = getCart(user);
        if (cart.containsKey(article)) {
            cart.put(article, quantity + cart.get(article));
        } else {
            cart.put(article, quantity);
        }
        setCart(user, cart);
    }

    public static void addLoyaltyPoints(Users user, int points) {
        user.setLoyaltyPoints(user.getLoyaltyPoints().add(BigInteger.valueOf(points)));
        UsersDAO.updateUser(user);
    }

    public static void removeLoyaltyPoints(Users user, BigInteger points) {
        user.setLoyaltyPoints(user.getLoyaltyPoints().subtract(points));
        UsersDAO.updateUser(user);
    }
}
