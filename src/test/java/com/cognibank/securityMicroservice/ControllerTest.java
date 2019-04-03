package com.cognibank.securityMicroservice;

import com.cognibank.securityMicroservice.Controller.MainController;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest(MainController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUserDetails() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/sendOtp"))
                .andExpect(jsonPath("userId").value("userID"))
                .andExpect(jsonPath("type").value("email"))
                .andDo(print ()).andExpect(status().isOk()).andExpect(content ().string(Matchers.containsString ("anilvarma0093@gmail.com")));
    }

}

