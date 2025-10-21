package com.example.addressbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AddressBookApplication {

    private static final Logger log = LoggerFactory.getLogger(AddressBookApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AddressBookApplication.class, args);
    }

    // Runs automatically at startup
    @Bean
    public CommandLineRunner demo(AddressBookRepository addressBookRepository, BuddyInfoRepository buddyInfoRepository) {
        return (args) -> {
            log.info("--- Saving a new AddressBook with Buddies ---");

            // Create buddies
            BuddyInfo buddy1 = new BuddyInfo("Peter", "101-219-562", "456 crescent");

            BuddyInfo buddy2 = new BuddyInfo("Greg", "123-456-7890", "123 street");

            // Add to address book
            AddressBook book = new AddressBook();
            book.addBuddy(buddy1);
            book.addBuddy(buddy2);

            // Save address book (buddies saved automatically via cascade)
            addressBookRepository.save(book);
            log.info("Saved address book: {}", book);

            // Fetch all address books
            log.info("AddressBooks found with findAll():");
            for (AddressBook ab : addressBookRepository.findAll()) {
                log.info(ab.toString());
            }

            // Fetch all buddies
            log.info("Buddies found with findAll():");
            for (BuddyInfo bi : buddyInfoRepository.findAll()) {
                log.info(bi.toString());
            }

            // Fetch buddy by ID
            BuddyInfo foundBuddy = buddyInfoRepository.findById(buddy1.getId()).orElse(null);
            log.info("Buddy found with findById({}): {}", buddy1.getId(), foundBuddy);

            // Fetch buddies by name
            log.info("Buddy found with findByName('Greg'):");
            buddyInfoRepository.findByName("Greg").forEach(buddyInfo -> log.info(buddyInfo.toString()));
        };
    }
}
