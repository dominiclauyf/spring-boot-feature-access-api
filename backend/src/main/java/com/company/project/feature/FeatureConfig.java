package com.company.project.feature;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

// @Configuration
public class FeatureConfig {

    @Bean
    CommandLineRunner commandLineRunnerFeature(FeatureRepository featureRepository) {

        return args -> {
            List<Feature> features = new ArrayList<>();

            for (int i = 1; i <= 3; i++) {
                String name = "feature " + i;
                Feature feature = new Feature(name);
                features.add(feature);
            }

            featureRepository.saveAll(features);
        };
    }
}
