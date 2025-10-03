package com.example.addressbook;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity //@Entity → Marks the class as a JPA entity (maps to a database table).
public class AddressBook {

    @Id //@Id → Marks the primary key field.
    @GeneratedValue(strategy = GenerationType.AUTO) //@GeneratedValue(strategy = GenerationType.AUTO) → Tells JPA how to generate primary key values automatically.
    private Long id; // Primary key for persistence

    // One-to-many relationship: one AddressBook can have many BuddyInfo objects
    //
    // cascade = CascadeType.ALL:
    //   - Defines what happens to the related BuddyInfo entities when performing operations on the parent AddressBook
    //   - CascadeType.ALL includes all operations: PERSIST (saving AddressBook also saves its buddies), MERGE, REMOVE, REFRESH, DETACH
    //   Using ALL means any operation on the parent is automatically applied to children
    //
    // orphanRemoval = true:
    //   - Automatically removes "orphan" children
    //   - If a BuddyInfo is removed from the buddies list but the parent AddressBook is still persisted,
    //     JPA deletes that BuddyInfo from the database
    //
    // Together, these make managing the AddressBook and its BuddyInfo objects simpler and safer:
    //   persisting, updating, or deleting the AddressBook automatically handles the related BuddyInfo objects

    // We can change the fetch type on the relationship from LAZY (default) to EAGER.
    // This tells JPA to load the buddies immediately when the AddressBook is loaded.
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
