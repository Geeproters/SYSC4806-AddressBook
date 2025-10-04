// Sanity check: @SpringBootTest with contextLoads.
package com.example.addressbook;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddressBookApplicationTests {

    @Test
    void contextLoads() {
        /// Runs fast and ensures configuration, beans, and JPA setup are okay.
    }
}
