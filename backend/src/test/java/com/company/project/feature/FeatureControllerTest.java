package com.company.project.feature;

import com.company.project.base.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FeatureControllerTest extends AbstractControllerTest {

    @Autowired private FeatureRepository featureRepository;

    private String featureApiUrl = "/basefeature";

    @Test
    void whenInputIsInvalid_thenReturnsStatus400() throws Exception {
        FeatureRequestDTO input = new FeatureRequestDTO("");

        this.performPostRequest(this.featureApiUrl, input, status.isBadRequest());
    }

    @Test
    void whenFeatureNameBeenUsed_thenReturnsStatus400() throws Exception {
        // Create a existing feature.
        String featureName = "feature1";
        Feature feature = Feature.builder().name(featureName).build();
        featureRepository.save(feature);

        FeatureRequestDTO input = new FeatureRequestDTO(featureName);

        this.performPostRequest(this.featureApiUrl, input, status.isBadRequest());
    }

    @Test
    void featureCreationSuccessfully() throws Exception {
        String featureName = "feature2";
        FeatureRequestDTO input = new FeatureRequestDTO(featureName);

        this.performPostRequest(this.featureApiUrl, input, status.isCreated());
    }
}
