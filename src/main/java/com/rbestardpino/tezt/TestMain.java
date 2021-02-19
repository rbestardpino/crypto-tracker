package com.rbestardpino.tezt;

import java.time.Instant;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestMain {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(new TestClass("id1", 24, Instant.now()));
        em.persist(new TestClass("id2", 816235123, Instant.now()));
        // List<TestClass> clases = em.createQuery("from TestClass",
        // TestClass.class).getResultList();
        // System.out.println(clases.get(0).getTime());
        // System.out.println(clases.get(1).getTime());
        // Duration delta = Duration.between(clases.get(0).getTime(),
        // clases.get(1).getTime());
        // System.out.println(delta.toMinutes());
        em.getTransaction().commit();
        em.close();
    }
}
