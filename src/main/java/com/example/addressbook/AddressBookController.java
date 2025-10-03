package com.example.addressbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AddressBookController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    // Create a new empty address book
    @PostMapping("/addressbooks")
    public AddressBook createAddressBook() {
        return addressBookRepository.save(new AddressBook());
    }

    // Get all address books
    @GetMapping("/addressbooks")
    public Iterable<AddressBook> getAllAddressBooks() {
        return addressBookRepository.findAll();
    }

    // Get a specific address book by ID
    @GetMapping("/addressbooks/{id}")
    public ResponseEntity<AddressBook> getAddressBook(@PathVariable Long id) {
        Optional<AddressBook> addressBook = addressBookRepository.findById(id);
        return addressBook.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Add a buddy to an address book
    @PostMapping("/addressbooks/{addressBookId}/buddies")
    public ResponseEntity<AddressBook> addBuddy(
            @PathVariable Long addressBookId,
            @RequestBody Map<String, String> buddyData) {

        Optional<AddressBook> addressBookOpt = addressBookRepository.findById(addressBookId);
        if (addressBookOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String name = buddyData.get("name");
        String phone = buddyData.get("phone");

        if (name == null || phone == null) {
            return ResponseEntity.badRequest().build();
        }

        AddressBook addressBook = addressBookOpt.get();

        // Create buddy using default constructor and setters
        BuddyInfo newBuddy = new BuddyInfo();
        newBuddy.setName(name);
        newBuddy.setPhone(phone);

        // Save the buddy first to ensure it's managed by JPA
        BuddyInfo savedBuddy = buddyInfoRepository.save(newBuddy);
        addressBook.addBuddy(savedBuddy);

        AddressBook savedBook = addressBookRepository.save(addressBook);
        return ResponseEntity.ok(savedBook);
    }

    // Get all buddies from an address book
    @GetMapping("/addressbooks/{addressBookId}/buddies")
    public ResponseEntity<Iterable<BuddyInfo>> getBuddies(@PathVariable Long addressBookId) {
        Optional<AddressBook> addressBookOpt = addressBookRepository.findById(addressBookId);
        if (addressBookOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addressBookOpt.get().getBuddies());
    }

    // Remove a buddy from an address book
    @DeleteMapping("/addressbooks/{addressBookId}/buddies/{buddyId}")
    public ResponseEntity<AddressBook> removeBuddy(
            @PathVariable Long addressBookId,
            @PathVariable Long buddyId) {

        Optional<AddressBook> addressBookOpt = addressBookRepository.findById(addressBookId);
        Optional<BuddyInfo> buddyOpt = buddyInfoRepository.findById(buddyId);

        if (addressBookOpt.isEmpty() || buddyOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AddressBook addressBook = addressBookOpt.get();
        BuddyInfo buddy = buddyOpt.get();

        addressBook.getBuddies().remove(buddy);
        AddressBook savedBook = addressBookRepository.save(addressBook);

        return ResponseEntity.ok(savedBook);
    }

    // Delete an address book
    @DeleteMapping("/addressbooks/{id}")
    public ResponseEntity<Void> deleteAddressBook(@PathVariable Long id) {
        if (addressBookRepository.existsById(id)) {
            addressBookRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}