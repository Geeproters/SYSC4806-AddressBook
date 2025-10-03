//package com.example.addressbook;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class BuddyInfoPersistenceTest {
//
//    private EntityManagerFactory emf;
//    private EntityManager em;
//
//    @Before
//    public void setUp() {
//        emf = Persistence.createEntityManagerFactory("addressbookPU");
//        em = emf.createEntityManager();
//    }
//
//    @After
//    public void tearDown() {
//        if (em != null) em.close();
//        if (emf != null) emf.close();
//    }
//
//    @Test
//    public void testPersistAndRetrieveBuddyInfo() {
//        BuddyInfo buddy = new BuddyInfo("Peter", "101-219-562");
//
//        // Persist buddy. Starts a transaction, saves the BuddyInfo instance to SQLite, then commits.
//        em.getTransaction().begin();
//        em.persist(buddy);
//        em.getTransaction().commit();
//
//        // Retrieve buddy using a query
//        List<BuddyInfo> results = em.createQuery("SELECT b FROM BuddyInfo b", BuddyInfo.class)
//                .getResultList();
//
//        assertFalse(results.isEmpty());
//        assertTrue(results.stream().anyMatch(b -> "Peter".equals(b.getName())));
//    }
//}
