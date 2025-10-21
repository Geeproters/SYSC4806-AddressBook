package com.example.addressbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController { // new controller on the server-side to handle client requests and render relevant views

    private final AddressBookRepository addressBookRepository;

    public WebController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    // Home page
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("addressBooks", addressBookRepository.findAll());
        return "index";
    }

    // Create a new AddressBook
    @PostMapping("/addressbooks")
    public String createAddressBook() {
        addressBookRepository.save(new AddressBook());
        return "redirect:/";
    }

    // View one AddressBook
    @GetMapping("/addressbooks/{id}")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        AddressBook ab = addressBookRepository.findById(id).orElse(null);
        if (ab == null) return "redirect:/";

        model.addAttribute("addressBook", ab);
        model.addAttribute("buddies", ab.getBuddies());
        model.addAttribute("newBuddy", new BuddyInfo());
        return "addressbook";
    }

    // Add a Buddy
    @PostMapping("/addressbooks/{id}/buddies")
    public String addBuddy(@PathVariable Long id, @ModelAttribute BuddyInfo newBuddy) {
        AddressBook ab = addressBookRepository.findById(id).orElse(null);
        if (ab == null) return "redirect:/";

        // Make sure the buddy is new (no ID)
        newBuddy.setId(null);

        ab.addBuddy(newBuddy);
        addressBookRepository.save(ab); // cascade persists BuddyInfo
        return "redirect:/addressbooks/" + id;
    }

    // Delete a buddy
    @PostMapping("/addressbooks/{abId}/buddies/{buddyId}/delete")
    public String deleteBuddy(@PathVariable Long abId, @PathVariable Long buddyId) {
        AddressBook ab = addressBookRepository.findById(abId).orElse(null);
        if (ab != null) {
            ab.getBuddies().removeIf(b -> b.getId().equals(buddyId));
            addressBookRepository.save(ab);
        }
        return "redirect:/addressbooks/" + abId;
    }

    // Delete an address book
    @PostMapping("/addressbooks/{id}/delete")
    public String deleteAddressBook(@PathVariable Long id) {
        if (addressBookRepository.existsById(id)) {
            addressBookRepository.deleteById(id);
        }
        return "redirect:/";
    }
}
