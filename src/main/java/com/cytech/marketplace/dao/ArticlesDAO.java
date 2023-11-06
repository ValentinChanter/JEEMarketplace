package com.cytech.marketplace.dao;

import com.cytech.marketplace.entity.Articles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.UUID;

public class ArticlesDAO {
    public static void addArticle(Articles article) {
        EntityManager em = PersistenceUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(article);
        transaction.commit();
        em.close();
    }

    public static void updateArticle(Articles article) {
        EntityManager em = PersistenceUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.merge(article);
        transaction.commit();
        em.close();
    }

    public static void deleteArticle(Articles article) {
        EntityManager em = PersistenceUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.remove(em.contains(article) ? article : em.merge(article));
        transaction.commit();
        em.close();
    }

    public static void deleteArticle(UUID uuid) {
        deleteArticle(getArticle(uuid));
    }

    public static void deleteArticle(String name) {
        deleteArticle(getArticle(name));
    }

    public static Articles getArticle(UUID uuid) {
        EntityManager em = PersistenceUtil.getEmf().createEntityManager();
        Articles article = em.find(Articles.class, uuid);
        em.close();
        return article;
    }

    public static Articles getArticle(String name) {
        EntityManager em = PersistenceUtil.getEmf().createEntityManager();
        Articles article = em.createQuery("FROM Articles WHERE name = :name", Articles.class).setParameter("name", name).getSingleResult();
        em.close();
        return article;
    }

    public static List<Articles> getArticles() {
        EntityManager em = PersistenceUtil.getEmf().createEntityManager();
        List<Articles> articles = em.createQuery("FROM Articles", Articles.class).getResultList();
        em.close();
        return articles;
    }
}
