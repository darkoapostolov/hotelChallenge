package com.symphony.hotelchallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// tells spring that we will be using the test properties
@ActiveProfiles("test")
// the port is different as to not interfere with the working of the app
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class HotelChallengeApplicationTests {

    MockMvc mockMvc;

    // initializes before each test
    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void contextLoads() {
    }

    // tests the get hotels controller method which lists hotels
    @Test
    public void testListHotels() throws Exception {
        MockHttpServletRequestBuilder hotelRequest = MockMvcRequestBuilders.get("/");
        this.mockMvc.perform(hotelRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("hotels"))
                .andExpect(MockMvcResultMatchers.model().attribute("bodyContent", "listHotels"))
                .andExpect(MockMvcResultMatchers.view().name("masterTemplate"));
    }

}
