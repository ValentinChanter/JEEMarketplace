package com.cytech.marketplace.dao;

import com.cytech.marketplace.entity.Articles;
import com.cytech.marketplace.entity.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.*;

public class UsersDAO {
    public static void addUser(Users user) {
        EntityManager em = PersistenceUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(user);
        transaction.commit();
        em.close();
    }

    public static void updateUser(Users user) {
        EntityManager em = PersistenceUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.merge(user);
        transaction.commit();
        em.close();
    }

    public static void deleteUser(Users user) {
        EntityManager em = PersistenceUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.remove(em.contains(user) ? user : em.merge(user));
        transaction.commit();
        em.close();
    }

    public static void deleteUser(String email) {
        deleteUser(getUser(email));
    }

    public static void deleteUser(UUID uuid) {
        deleteUser(getUser(uuid));
    }

    public static Users getUser(UUID uuid) {
        EntityManager em = PersistenceUtil.getEmf().createEntityManager();
        Users user = em.find(Users.class, uuid);
        em.close();
        return user;
    }

    public static Users getUser(String email) {
        EntityManager em = PersistenceUtil.getEmf().createEntityManager();
        List<Users> users = em.createQuery("FROM Users WHERE email = :email", Users.class).setParameter("email", email).getResultList();
        if (users.isEmpty()) {
            return null;
        }

        Users user = users.get(0);
        em.close();
        return user;
    }

    public static List<Users> getUsersByName(String name) {
        EntityManager em = PersistenceUtil.getEmf().createEntityManager();
        List<Users> users = em.createQuery("FROM Users WHERE name = :name", Users.class).setParameter("name", name).getResultList();
        em.close();
        return users;
    }

    public static Users getFirstUserByName(String name) {
        List<Users> users = getUsersByName(name);
        return users.isEmpty() ? null : users.get(0);
    }

    public static boolean login(String email, String password) {
        Users user = getUser(email);
        if (user == null) {
            return false;
        }
        return BCrypt.checkpw(password, user.getPassword());
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

    public static Map<Articles, Integer> getCart(Users user) {
        return stringToCart(user.getCart());
    }

    public static void setCart(Users user, Map<Articles, Integer> cart) {
        user.setCart(cartToString(cart));
        updateUser(user);
    }

    /**
     * Add an article to the database cart. If it already exists, sums the quantities.
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
}
