package com.company.project.userfeature;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "feature")
public class UserFeatureController {

    private final UserFeatureService userFeatureService;

    @GetMapping(params = {"email", "featureName"})
    public ResponseEntity<UserFeatureResponseDTO> getUserFeatures(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "featureName") String featureName) {
        return ResponseEntity.ok(
                new UserFeatureResponseDTO(
                        userFeatureService.isUserCanAccessFeature(email, featureName)));
    }

    @PostMapping
    public void updateUserFeature(
            @RequestBody @Valid UserFeatureUpdateRequestDTO userFeatureRequest) {
        // Assuming FeatureRequest is a POJO representing the JSON request
        // Validate featureRequest if necessary

        // Perform database update operation based on the featureRequest
        boolean databaseUpdated = userFeatureService.updateUserFeature(userFeatureRequest);

        if (databaseUpdated) {
            // Database update successful, return HTTP status OK (200)
            return;
        } else {
            // Database update failed, return HTTP status Not Modified (304)
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Database update failed");
        }
    }
}
