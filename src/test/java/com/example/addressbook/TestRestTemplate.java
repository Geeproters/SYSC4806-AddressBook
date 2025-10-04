// Integration tests: Use TestRestTemplate for full HTTP tests.
package com.example.addressbook;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AddressBookHttpTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllAddressBooksShouldReturn200() {
        String url = "http://localhost:" + port + "/api/addressbooks";
        String body = this.restTemplate.getForObject(url, String.class);
        assertThat(body).contains("["); // Simplest check – JSON array should start with [
    }
}
