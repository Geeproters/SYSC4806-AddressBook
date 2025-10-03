package com.example.addressbook;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AddressBookTest {

    private AddressBook book;
    private BuddyInfo peter;
    private BuddyInfo greg;

    @Before
    public void setUp() {
        book = new AddressBook();
        peter = new BuddyInfo("Peter", "101-219-562");
        greg = new BuddyInfo("Greg", "123-456-7890");
    }

    @Test
    public void testAddBuddy() {
        book.addBuddy(peter);
        List<BuddyInfo> buddies = book.getBuddies();
        assertEquals(1, buddies.size());
        assertEquals(peter, buddies.get(0));
    }


    @Test
    public void testToString() {
        book.addBuddy(peter);
        book.addBuddy(greg);

        String expected = "[Peter (101-219-562), Greg (123-456-7890)]";
        assertEquals(expected, book.toString());
    }
}
