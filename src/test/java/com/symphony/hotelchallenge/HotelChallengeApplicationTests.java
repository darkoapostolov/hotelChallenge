package com.symphony.hotelchallenge;

import com.symphony.hotelchallenge.model.*;
import com.symphony.hotelchallenge.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    HotelService hotelService;


    private static Hotel h1;
    private static boolean dataInitialized = false;

    // initializes before each test
    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        initData();
    }

    private void initData() {
        if (!dataInitialized) {
            h1 = hotelService.create("h", "a", "i", "d", "g");
            dataInitialized = true;
        }
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

    // Tests the get details method in the hotel controller
    @Test
    public void testDetailsHotel() throws Exception {
        MockHttpServletRequestBuilder hotelDetailsRequest = MockMvcRequestBuilders
                .get("/" + h1.getId() + "/details/");

        this.mockMvc.perform(hotelDetailsRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("hotel"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("rating"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("reviews"))
                .andExpect(MockMvcResultMatchers.model().attribute("bodyContent", "details"))
                .andExpect(MockMvcResultMatchers.view().name("masterTemplate"));
    }

    // tests the get method for the review form in the review controller
    @Test
    public void testGetReviewForm() throws Exception {
        MockHttpServletRequestBuilder getReviewFormRequest = MockMvcRequestBuilders
                .get("/review/addReviewForm/" + h1.getId());

        this.mockMvc.perform(getReviewFormRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("ratings"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("hotelId"))
                .andExpect(MockMvcResultMatchers.model().attribute("bodyContent", "reviewForm"))
                .andExpect(MockMvcResultMatchers.view().name("masterTemplate"));
    }

}
