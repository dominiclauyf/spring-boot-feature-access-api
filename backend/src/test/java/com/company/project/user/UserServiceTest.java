package com.company.project.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import javax.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserServiceTest {

    @Autowired private UserService userService;

    @MockBean private UserRepository userRepository;

    private String email = "john@example.com";
    private User sampleUser = User.builder().email(this.email).build();

    @Test
    public void testGetUsers() {
        // Mock the repository behavior
        when(userRepository.findAll()).thenReturn(List.of(sampleUser));

        // Perform the test
        List<User> users = userService.getUsers();

        // Assertions
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getEmail()).isEqualTo(this.email);
    }

    @Test
    public void testAddNewUser() {
        // Mock the repository behavior
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        User created = userService.addNewUser(new UserRequestDTO(this.email));

        // Assertions
        assertThat(created.getEmail()).isEqualTo(this.email);
        verify(userRepository).save(sampleUser);
    }

    @Test
    public void testRaiseExceptionAddNewUserWhenEmailExist() {
        // Mock the repository behavior
        when(userRepository.findByEmail(this.email)).thenReturn(Optional.of(sampleUser));

        assertThrows(
                ValidationException.class,
                () -> userService.addNewUser(new UserRequestDTO(this.email)));

        verify(userRepository, times(0)).save(sampleUser);
    }
}
