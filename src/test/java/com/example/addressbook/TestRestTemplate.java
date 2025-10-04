// Integration tests: Use TestRestTemplate for full HTTP tests.
package com.example.addressbook;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Map;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AddressBookHttpTest {

    @LocalServerPort
    private int port; // Port on which the test server is running (randomized)

    @Autowired
    private TestRestTemplate restTemplate; // Used to perform HTTP requests in tests

    @Autowired
    private AddressBookRepository addressBookRepository; // Direct access to the repository to manage test data

    @BeforeEach
    void cleanDatabase() {
        addressBookRepository.deleteAll();
    }

    // Test 1: Check that the GET /api/addressbooks endpoint returns a valid response
    @Test
    void getAllAddressBooksShouldReturn200() {
        String url = "http://localhost:" + port + "/api/addressbooks";
        String body = this.restTemplate.getForObject(url, String.class);
        assertThat(body).contains("["); // Simplest check – JSON array should start with [
    }

    // Test 2: Check that creating a new address book via POST returns a valid AddressBook object
    @Test
    void createAddressBookShouldReturnNewAddressBook() {
        AddressBook response = this.restTemplate.postForObject(
                "http://localhost:" + port + "/api/addressbooks", null, AddressBook.class);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

    // Test 3: Check that adding a buddy to an address book works and returns updated data
    @Test
    void addBuddyShouldReturnUpdatedAddressBook() {
        // Create empty AddressBook first
        AddressBook book = this.restTemplate.postForObject(
                "http://localhost:" + port + "/api/addressbooks", null, AddressBook.class);

        // Buddy data
        Map<String, String> buddyData = Map.of(
                "name", "Alice",
                "phone", "555-1234",
                "address", "456 Park Ave"
        );

        // Send POST request to add the buddy
        AddressBook updatedBook = this.restTemplate.postForObject(
                "http://localhost:" + port + "/api/addressbooks/" + book.getId() + "/buddies",
                buddyData, AddressBook.class);

        assertThat(updatedBook.getBuddies()).hasSize(1);
        assertThat(updatedBook.getBuddies().get(0).getName()).isEqualTo("Alice");
        assertThat(updatedBook.getBuddies().get(0).getAddress()).isEqualTo("456 Park Ave");
    }

    // Test 4: Verify that GET /api/addressbooks/{id}/buddies returns all buddies
    @Test
    void getBuddiesShouldReturnAllBuddies() {
        // Create AddressBook and add a buddy first
        AddressBook book = this.restTemplate.postForObject(
                "http://localhost:" + port + "/api/addressbooks", null, AddressBook.class);

        Map<String, String> buddyData = Map.of("name", "Bob", "phone", "123-4567");
        this.restTemplate.postForObject(
                "http://localhost:" + port + "/api/addressbooks/" + book.getId() + "/buddies",
                buddyData, AddressBook.class);

        String response = this.restTemplate.getForObject(
                "http://localhost:" + port + "/api/addressbooks/" + book.getId() + "/buddies",
                String.class);

        assertThat(response).contains("Bob");
    }

    // Test 5: Verify that deleting a buddy removes it from the address book
    @Test
    void deleteBuddyShouldRemoveBuddy() {
        AddressBook book = this.restTemplate.postForObject(
                "http://localhost:" + port + "/api/addressbooks", null, AddressBook.class);

        Map<String, String> buddyData = Map.of("name", "Charlie", "phone", "999-0000");
        AddressBook updatedBook = this.restTemplate.postForObject(
                "http://localhost:" + port + "/api/addressbooks/" + book.getId() + "/buddies",
                buddyData, AddressBook.class);

        Long buddyId = updatedBook.getBuddies().get(0).getId();

        this.restTemplate.delete(
                "http://localhost:" + port + "/api/addressbooks/" + book.getId() + "/buddies/" + buddyId);

        String response = this.restTemplate.getForObject(
                "http://localhost:" + port + "/api/addressbooks/" + book.getId() + "/buddies",
                String.class);

        assertThat(response).doesNotContain("Charlie");
    }

    // Test 6: Verify that deleting an entire address book works
    @Test
    void deleteAddressBookShouldRemoveIt() {
        AddressBook book = this.restTemplate.postForObject(
                "http://localhost:" + port + "/api/addressbooks", null, AddressBook.class);

        this.restTemplate.delete(
                "http://localhost:" + port + "/api/addressbooks/" + book.getId());

        String response = this.restTemplate.getForObject(
                "http://localhost:" + port + "/api/addressbooks", String.class);

        assertThat(response).doesNotContain(book.getId().toString());
    }
}
