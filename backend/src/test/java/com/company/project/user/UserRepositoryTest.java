package com.company.project.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.company.project.base.AbstractRepositoryTest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

class UserRepositoryTest extends AbstractRepositoryTest {
    @Autowired private TestEntityManager entityManager;

    @Autowired private UserRepository userRepository;

    private String tempEmail = "test@test.com";
    private String tempEmail2 = "test2@test.com";

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

    @Test
    public void findByEmail_butNotFound() {

        Optional<User> found = userRepository.findByEmail(tempEmail2);

        assertThat(found.isPresent()).isFalse();
    }

    @Test
    public void existsByEmail_thenReturnFalse() {
        assertThat(userRepository.existsByEmail(tempEmail2)).isFalse();
    }
}
