package com.rbestardpino.cryptotracker.utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class PersistenceManager {

    private static PersistenceManager instance = null;

    private static EntityManagerFactory emf;

    private EntityManager em;

    public void createEntityManager() {
        em = emf.createEntityManager();
    }

    public void persist(Object entity) {
        em.persist(entity);
    }

    public void merge(Object entity) {
        em.merge(entity);
    }

    public void remove(Object entity) {
        em.remove(entity);
    }

    public void beginTransaction() {
        em.getTransaction().begin();
    }

    public void commit() {
        em.getTransaction().commit();
    }

    public void rollback() {
        em.getTransaction().rollback();
    }

    public <T> T find(Class<T> type, Object primaryKey) {
        T t = em.find(type, primaryKey);
        if (t != null)
            t.getClass();
        return t;
    }

    public <T> List<T> findAll(Class<T> type) {
        List<T> tList = createQuery("select t from " + type.getSimpleName(), type).getResultList();
        if (tList != null)
            tList.size();
        return tList;
    }

    public <T> TypedQuery<T> createQuery(String qlString, Class<T> type) {
        return em.createQuery(qlString, type);
    }

    public void close() {
        em.close();
    }

    private PersistenceManager() {
        emf = Persistence.createEntityManagerFactory("persistenceUnit");
    }

    public static PersistenceManager getInstance() {
        if (instance == null)
            instance = new PersistenceManager();
        return instance;
    }
}
