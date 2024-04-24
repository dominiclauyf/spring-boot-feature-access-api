package com.company.project.user;

import com.company.project.base.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserControllerTest extends AbstractControllerTest {

    @Autowired private UserRepository userRepository;

    private String userApiUrl = "/api/v1/user";

    @Test
    void whenInputIsInvalid_thenReturnsStatus400() throws Exception {
        UserRequestDTO input = new UserRequestDTO("a.coma");

        this.performPostRequest(this.userApiUrl, input, status.isBadRequest());
    }

    @Test
    void whenEmailBeenUsed_thenReturnsStatus400() throws Exception {
        // Create a existing user.
        String email = "john@example.com";
        User sampleUser = User.builder().email(email).build();
        userRepository.save(sampleUser);

        UserRequestDTO input = new UserRequestDTO(email);

        this.performPostRequest(this.userApiUrl, input, status.isBadRequest());
    }

    @Test
    void userRegisterSuccessfully() throws Exception {
        String email = "john1@example.com";
        UserRequestDTO input = new UserRequestDTO(email);

        this.performPostRequest(this.userApiUrl, input, status.isCreated());
    }
}
