package com.example.addressbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class AddressBookViewController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    // Display all address books on the home page
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("addressBooks", addressBookRepository.findAll());
        return "index";  // index.html template
    }

    // Display a specific address book and its buddies
    @GetMapping("/addressbooks/{id}/view")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        Optional<AddressBook> addressBook = addressBookRepository.findById(id);

        if (addressBook.isPresent()) {
            model.addAttribute("addressBook", addressBook.get());
            model.addAttribute("buddies", addressBook.get().getBuddies());
            return "addressbook-view";  // addressbook-view.html template
        } else {
            return "redirect:/";  // Redirect if not found
        }
    }
}
