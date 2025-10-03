package com.example.addressbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


// SpringBootApplication tells Spring Boot “this is the entry point, configure everything automatically.”
@SpringBootApplication
public class AddressBookApplication {

    private static final Logger log = LoggerFactory.getLogger(AddressBookApplication.class);

    // main() launches a Spring application context and starts the embedded server
    public static void main(String[] args) {
        SpringApplication.run(AddressBookApplication.class, args);
    }

    // The @Bean annotation tells Spring to run this code automatically when the application starts.
    @Bean
    public CommandLineRunner demo(AddressBookRepository addressBookRepository, BuddyInfoRepository buddyInfoRepository) {
        return (args) -> {
            // This is where we replicate the functionality of our old tests

            log.info("--- Saving a new AddressBook with Buddies to the H2 database ---");

            // Create some buddies
            BuddyInfo buddy1 = new BuddyInfo("Peter", "101-219-562");
            BuddyInfo buddy2 = new BuddyInfo("Greg", "123-456-7890");

            // Create an address book and add the buddies
            AddressBook book = new AddressBook();
            book.addBuddy(buddy1);
            book.addBuddy(buddy2);

            // We only need to save the address book because CascadeType.PERSIST will automatically save the buddies.
            addressBookRepository.save(book);
            log.info("Saved address book: {}", book);

            log.info("");
            log.info("--- Retrieving all data from the database ---");

            // Fetch all address books
            log.info("AddressBooks found with findAll():");
            log.info("-------------------------------");
            for (AddressBook ab : addressBookRepository.findAll()) {
                log.info(ab.toString());
            }
            log.info("");

            // Fetch all buddies
            log.info("Buddies found with findAll():");
            log.info("-------------------------------");
            for (BuddyInfo bi : buddyInfoRepository.findAll()) {
                log.info(bi.toString());
            }
            log.info("");

            // Fetch a buddy by ID
            BuddyInfo foundBuddy = buddyInfoRepository.findById(buddy1.getId()).orElse(null);
            log.info("Buddy found with findById({}):", buddy1.getId());
            log.info("--------------------------------");
            log.info(foundBuddy.toString());
            log.info("");

            // Fetch buddies by name using our custom method
            log.info("Buddy found with findByName('Greg'):");
            log.info("--------------------------------------------");
            buddyInfoRepository.findByName("Greg").forEach(buddyInfo -> {
                log.info(buddyInfo.toString());
            });
            log.info("");
        };
    }
}