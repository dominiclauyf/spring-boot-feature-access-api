package com.company.project.feature;

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
public class FeatureServiceTest {

    @Autowired private FeatureService featureService;

    @MockBean private FeatureRepository featureRepository;

    private String featureName = "feature";

    private Feature sampleFeature = Feature.builder().name(this.featureName).build();

    @Test
    public void testGetFeatures() {
        // Mock the repository behavior
        when(featureRepository.findAll()).thenReturn(List.of(sampleFeature));

        // Perform the test
        List<Feature> features = featureService.getFeature();

        // Assertions
        assertThat(features).isNotNull();
        assertThat(features.size()).isEqualTo(1);
        assertThat(features.get(0).getName()).isEqualTo(this.featureName);
    }

    @Test
    public void testAddNewFeature() {
        // Mock the repository behavior
        when(featureRepository.save(sampleFeature)).thenReturn(sampleFeature);

        Feature created = featureService.addFeature(new FeatureRequestDTO(this.featureName));

        // Assertions
        assertThat(created.getName()).isEqualTo(this.featureName);
        verify(featureRepository).save(sampleFeature);
    }

    @Test
    public void testRaiseExceptionAddNewFeatureWhenNameExist() {
        // Mock the repository behavior
        when(featureRepository.findByName(this.featureName)).thenReturn(Optional.of(sampleFeature));

        assertThrows(
                ValidationException.class,
                () -> featureService.addFeature(new FeatureRequestDTO(this.featureName)));

        verify(featureRepository, times(0)).save(sampleFeature);
    }
}
