//package com.example.addressbook;
//
//import jakarta.persistence.*;
//import org.junit.*;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class AddressBookPersistenceTest {
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
//    public void testPersistAndRetrieveAddressBook() {
//        // Create BuddyInfo objects
//        BuddyInfo peter = new BuddyInfo("Peter", "101-219-562");
//        BuddyInfo greg = new BuddyInfo("Greg", "123-456-7890");
//        AddressBook book = new AddressBook();
//        book.addBuddy(peter);
//        book.addBuddy(greg);
//
//        // Persist AddressBook; BuddyInfo objects are automatically persisted due to cascade
//        em.getTransaction().begin();
//        em.persist(book);  // BuddyInfos persist automatically because of cascade
//        em.getTransaction().commit();
//
//        // Retrieve AddressBook
//        List<AddressBook> results = em.createQuery("SELECT a FROM AddressBook a", AddressBook.class)
//                .getResultList();
//        assertFalse(results.isEmpty());
//        AddressBook retrievedBook = results.get(0);
//
//        // Check that buddies were persisted
//        assertEquals(2, retrievedBook.getBuddies().size());
//        assertTrue(retrievedBook.getBuddies().stream().anyMatch(b -> "Peter".equals(b.getName())));
//        assertTrue(retrievedBook.getBuddies().stream().anyMatch(b -> "Greg".equals(b.getName())));
//
//        // ---- test removal ----
//        // Remove a buddy and update AddressBook
//        em.getTransaction().begin();
//        retrievedBook.getBuddies().removeIf(b -> "Greg".equals(b.getName()));
//        em.merge(retrievedBook);  // merge triggers cascade update, used for reattaching or synchronizing a detached entity with the current persistence context
//        em.getTransaction().commit();
//
//        // Verify buddy was removed from database
//        BuddyInfo deletedBuddy = em.find(BuddyInfo.class, greg.getId());
//        assertNull(deletedBuddy);
//
//        // Verify remaining buddy still exists
//        BuddyInfo remainingBuddy = em.find(BuddyInfo.class, peter.getId());
//        assertNotNull(remainingBuddy);
//        assertEquals("Peter", remainingBuddy.getName());
//
//        // ---- test cascade update ----
//        // Change Peter's phone number
//        em.getTransaction().begin();
//        remainingBuddy.setPhone("999-999-9999");
//        em.merge(retrievedBook);  // cascading merge updates BuddyInfo
//        em.getTransaction().commit();
//
//        // Verify update persisted
//        BuddyInfo updatedBuddy = em.find(BuddyInfo.class, peter.getId());
//        assertEquals("999-999-9999", updatedBuddy.getPhone());
//    }
//}
