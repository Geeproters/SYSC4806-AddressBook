package com.example.addressbook;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Primary key

    // One-to-many relationship with BuddyInfo
    // Cascade PERSIST ensures saving the AddressBook also saves its buddies
    // orphanRemoval = true automatically deletes buddies removed from the list
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BuddyInfo> buddies = new ArrayList<>();

    public AddressBook() {}

    public void addBuddy(BuddyInfo buddy) {
        buddies.add(buddy);
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    @Override
    public String toString() {
        return buddies.toString();
    }

    public Long getId() {
        return id;
    }
}
