package com.company.project.feature;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "basefeature")
public class FeatureController {

    private final FeatureService featureService;

    @GetMapping
    public List<Feature> getFeatures() {
        return featureService.getFeature();
    }

    @PostMapping
    public ResponseEntity<Feature> createFeature(
            @RequestBody @Valid FeatureRequestDTO featureRequest) {
        return new ResponseEntity<>(featureService.addFeature(featureRequest), HttpStatus.CREATED);
    }
}
