// Mock dependencies: @WebMvcTest + @MockBean to isolate controller logic.
package com.example.addressbook;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ApiController.class)
class ApiControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressBookRepository addressBookRepository;

    @MockBean
    private BuddyInfoRepository buddyInfoRepository;


    @Test
    void getAddressBooksShouldReturnEmptyList() throws Exception {
        when(addressBookRepository.findAll()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/api/addressbooks"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }
}
