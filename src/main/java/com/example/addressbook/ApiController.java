package com.example.addressbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private AddressBookRepository addressBookRepository;

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

    // Get a specific address book
    @GetMapping("/addressbooks/{id}")
    public ResponseEntity<AddressBook> getAddressBook(@PathVariable Long id) {
        return addressBookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Add a buddy
    @PostMapping("/addressbooks/{id}/buddies")
    public ResponseEntity<AddressBook> addBuddy(
            @PathVariable Long id,
            @RequestBody Map<String, String> buddyData) {

        Optional<AddressBook> abOpt = addressBookRepository.findById(id);
        if (abOpt.isEmpty()) return ResponseEntity.notFound().build();

        String name = buddyData.get("name");
        String phone = buddyData.get("phone");
        String address = buddyData.get("address");

        if (name == null || phone == null) return ResponseEntity.badRequest().build();

        BuddyInfo buddy = new BuddyInfo(name, phone, address);
        AddressBook ab = abOpt.get();
        ab.addBuddy(buddy);
        addressBookRepository.save(ab); // cascade persists buddy

        return ResponseEntity.ok(ab);
    }

    // Get buddies of an address book
    @GetMapping("/addressbooks/{id}/buddies")
    public ResponseEntity<Iterable<BuddyInfo>> getBuddies(@PathVariable Long id) {
        return addressBookRepository.findById(id)
                .map(ab -> ResponseEntity.ok((Iterable<BuddyInfo>) ab.getBuddies()))
                .orElse(ResponseEntity.notFound().build());
    }


    // Remove a buddy
    @DeleteMapping("/addressbooks/{bookId}/buddies/{buddyId}")
    public ResponseEntity<AddressBook> removeBuddy(@PathVariable Long bookId, @PathVariable Long buddyId) {
        Optional<AddressBook> abOpt = addressBookRepository.findById(bookId);
        if (abOpt.isEmpty()) return ResponseEntity.notFound().build();

        AddressBook ab = abOpt.get();
        ab.getBuddies().removeIf(b -> b.getId().equals(buddyId));
        addressBookRepository.save(ab);

        return ResponseEntity.ok(ab);
    }

    // Delete an address book
    @DeleteMapping("/addressbooks/{id}")
    public ResponseEntity<Void> deleteAddressBook(@PathVariable Long id) {
        if (!addressBookRepository.existsById(id)) return ResponseEntity.notFound().build();
        addressBookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
