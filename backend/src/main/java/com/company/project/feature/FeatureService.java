package com.company.project.feature;

import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FeatureService {
    private final FeatureRepository featureRepository;

    public List<Feature> getFeature() {
        return featureRepository.findAll();
    }

    public Feature addFeature(FeatureRequestDTO featureRequest) {
        Optional<Feature> featureOptional = featureRepository.findByName(featureRequest.getName());
        if (featureOptional.isPresent()) {
            throw new ValidationException("Feature already created");
        }
        Feature feature = Feature.builder().name(featureRequest.getName()).build();
        return featureRepository.save(feature);

    }
}
