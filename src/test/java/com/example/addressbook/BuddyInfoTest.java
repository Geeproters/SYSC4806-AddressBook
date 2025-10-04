package com.example.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BuddyInfoTest {

    private BuddyInfo buddy;

    @BeforeEach
    void setUp() {
        buddy = new BuddyInfo("Peter", "101219562");
    }

    @Test
    void testToString() {
        assertEquals("Peter (101219562)", buddy.toString());
    }

    @Test
    void testGetName() {
        assertEquals("Peter", buddy.getName());
    }

    @Test
    void testGetPhone() {
        assertEquals("101219562", buddy.getPhone());
    }
}
