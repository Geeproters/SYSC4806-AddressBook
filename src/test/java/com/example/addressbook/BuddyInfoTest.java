package com.example.addressbook;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BuddyInfoTest {

    private BuddyInfo buddy;

    @Before
    public void setUp() {
        buddy = new BuddyInfo("Peter", "101219562");
    }

    @Test
    public void testToString() {
        assertEquals("Peter (101219562)", buddy.toString());
    }

    //Trivial getter tests:
    @Test
    public void testGetName() {
        assertEquals("Peter", buddy.getName());
    }

    @Test
    public void testGetPhone() {
        assertEquals("101219562", buddy.getPhone());
    }
}
