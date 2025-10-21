package com.example.addressbook;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_book_id") // avoids a join table, fixes duplicates
    private List<BuddyInfo> buddies = new ArrayList<>();

    public AddressBook() {}

    public void addBuddy(BuddyInfo buddy) {
        buddies.add(buddy);
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return buddies.toString();
    }
}
