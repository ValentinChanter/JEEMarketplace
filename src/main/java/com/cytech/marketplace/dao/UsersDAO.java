package com.cytech.marketplace.dao;

import com.cytech.marketplace.entity.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

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
}
