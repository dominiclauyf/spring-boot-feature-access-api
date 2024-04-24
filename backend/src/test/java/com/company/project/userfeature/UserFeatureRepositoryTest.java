package com.company.project.userfeature;

import static org.assertj.core.api.Assertions.assertThat;

import com.company.project.base.AbstractRepositoryTest;
import com.company.project.feature.Feature;
import com.company.project.user.User;
import com.company.project.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

class UserFeatureRepositoryTest extends AbstractRepositoryTest {
    @Autowired private TestEntityManager entityManager;

    @Autowired private UserFeatureRepository userFeatureRepository;
    @Autowired private UserRepository userRepository;

    private String featureName = "feature A";
    private String email = "test@test.com";

    private String featureNameB = "feature B";
    private String email2 = "test2@test.com";

    private UserFeature userFeature;
    private User user;
    private Feature feature;

    @BeforeEach
    void setUp() {
        this.user = new User(email);
        this.feature = new Feature(featureName);
        this.userFeature = UserFeature.builder().user(user).feature(feature).build();

        entityManager.persist(this.user);
        entityManager.persist(this.feature);
        entityManager.persist(this.userFeature);
        entityManager.flush();
    }

    @Test
    public void findByUserEmailAndFeatureName_thenReturnUserFeature() {

        Optional<UserFeature> found =
                userFeatureRepository.findByUserEmailAndFeatureName(this.email, this.featureName);

        assertThat(found.isPresent()).isTrue();
    }

    @Test
    public void existsByUserEmailAndFeatureName_thenReturnTrue() {
        boolean result =
                userFeatureRepository.existsByUserEmailAndFeatureName(this.email, this.featureName);
        assertThat(result).isTrue();
    }

    @Test
    public void findByUserEmailAndFeatureName_butNotFound() {

        Optional<UserFeature> found =
                userFeatureRepository.findByUserEmailAndFeatureName(this.email2, this.featureNameB);

        assertThat(found.isPresent()).isFalse();
    }

    @Test
    public void existsByEmail_thenReturnFalse() {
        boolean result =
                userFeatureRepository.existsByUserEmailAndFeatureName(
                        this.email2, this.featureNameB);
        assertThat(result).isFalse();
    }
}
