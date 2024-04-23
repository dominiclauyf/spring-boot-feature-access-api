package com.company.project.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest {
    @Autowired private TestEntityManager entityManager;

    @Autowired private UserRepository userRepository;

    private String tempEmail = "test@test.com";

    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User(tempEmail);
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    public void findByEmail_thenReturnUser() {

        Optional<User> found = userRepository.findByEmail(user.getEmail());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getEmail()).isEqualTo(this.user.getEmail());
    }

    @Test
    public void findOneByEmail_thenReturnUser() {

        User found = userRepository.findOneByEmail(this.user.getEmail());

        assertThat(found.getEmail()).isEqualTo(this.user.getEmail());
    }

    @Test
    public void existsByEmail_thenReturnTrue() {
        assertThat(userRepository.existsByEmail(this.user.getEmail())).isTrue();
    }
}

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserNotExistRepositoryTest {

    @Autowired private UserRepository userRepository;

    private String tempEmail = "test@test.com";

    @Test
    public void findByEmail_butNotFound() {

        Optional<User> found = userRepository.findByEmail(tempEmail);

        assertThat(found.isPresent()).isFalse();
    }

    @Test
    public void existsByEmail_thenReturnFalse() {
        assertThat(userRepository.existsByEmail(tempEmail)).isFalse();
    }
}
