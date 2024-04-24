package com.company.project.feature;

import static org.assertj.core.api.Assertions.assertThat;

import com.company.project.base.AbstractRepositoryTest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

class FeatureRepositoryTest extends AbstractRepositoryTest {
    @Autowired private TestEntityManager entityManager;

    @Autowired private FeatureRepository featureRepository;

    private String featureName = "feature A";
    private String featureNameB = "feature B";
    private Feature feature;

    @BeforeEach
    void setUp() {
        this.feature = new Feature(featureName);
        entityManager.persist(this.feature);
        entityManager.flush();
    }

    @Test
    public void findByName_thenReturnFeature() {

        Optional<Feature> found = featureRepository.findByName(feature.getName());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo(this.feature.getName());
    }

    @Test
    public void findOneByName_thenReturnUser() {

        Feature found = featureRepository.findOneByName(this.feature.getName());

        assertThat(found.getName()).isEqualTo(this.feature.getName());
    }

    @Test
    public void existsByName_thenReturnTrue() {
        assertThat(featureRepository.existsByName(this.feature.getName())).isTrue();
    }

    @Test
    public void findByEmail_butNotFound() {

        Optional<Feature> found = featureRepository.findByName(featureNameB);

        assertThat(found.isPresent()).isFalse();
    }

    @Test
    public void existsByEmail_thenReturnFalse() {
        assertThat(featureRepository.existsByName(featureNameB)).isFalse();
    }
}
