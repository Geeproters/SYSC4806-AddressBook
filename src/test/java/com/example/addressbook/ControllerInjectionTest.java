// Controller injection test: Ensure beans are created.
package com.example.addressbook;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ControllerInjectionTest {

    @Autowired
    private AddressBookController controller;

    @Test
    void controllerLoads() {
        assertThat(controller).isNotNull();
    }
}
