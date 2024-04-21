package com.company.project.user;

import java.util.List;
import java.util.Optional;
import javax.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addNewUser(UserRequestDTO userRequest) {
        Optional<User> userOptional = userRepository.findByEmail(userRequest.getEmail());
        if (userOptional.isPresent()) {
            throw new ValidationException("Email used");
        }
        User user = User.builder().email(userRequest.getEmail()).build();
        return userRepository.save(user);
    }
}
