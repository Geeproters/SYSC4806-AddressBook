package com.example.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressBookTest {

    private AddressBook book;
    private BuddyInfo peter;
    private BuddyInfo greg;

    @BeforeEach
    void setUp() {
        book = new AddressBook();
        peter = new BuddyInfo("Peter", "101-219-562", "456 cres");
        greg = new BuddyInfo("Greg", "123-456-7890", "123 str");
    }

    @Test
    void testAddBuddy() {
        book.addBuddy(peter);
        List<BuddyInfo> buddies = book.getBuddies();
        assertEquals(1, buddies.size());
        assertEquals(peter, buddies.get(0));
    }

    @Test
    void testToString() {
        book.addBuddy(peter);
        book.addBuddy(greg);

        String expected = "[Peter (101-219-562) [456 cres], Greg (123-456-7890) [123 str]]";
        assertEquals(expected, book.toString());
    }
}
