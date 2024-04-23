package com.company.project.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@EnableConfigurationProperties
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    public static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(
                    MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));
    @Autowired private MockMvc mvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired private UserRepository userRepository;

    @Test
    void whenInputIsInvalid_thenReturnsStatus400() throws Exception {
        UserRequestDTO input = new UserRequestDTO("a.coma");
        String body = objectMapper.writeValueAsString(input);

        mvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/user")
                                .contentType(APPLICATION_JSON_UTF8)
                                .content(body))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void whenEmailBeenUsed_thenReturnsStatus400() throws Exception {
        // Create a existing user.
        String email = "john@example.com";
        User sampleUser = User.builder().email(email).build();
        userRepository.save(sampleUser);

        UserRequestDTO input = new UserRequestDTO(email);
        String body = objectMapper.writeValueAsString(input);

        mvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/user")
                                .contentType(APPLICATION_JSON_UTF8)
                                .content(body))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void userRegisterSuccessfully() throws Exception {
        // Create a existing user.
        String email = "john1@example.com";

        UserRequestDTO input = new UserRequestDTO(email);
        String body = objectMapper.writeValueAsString(input);

        mvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/user")
                                .contentType(APPLICATION_JSON_UTF8)
                                .content(body))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
