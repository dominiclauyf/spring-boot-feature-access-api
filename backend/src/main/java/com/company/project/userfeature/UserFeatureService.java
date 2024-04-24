package com.company.project.userfeature;

import com.company.project.feature.Feature;
import com.company.project.feature.FeatureRepository;
import com.company.project.user.UserRepository;
import java.util.Optional;
import javax.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserFeatureService {
    private final UserRepository userRepository;
    private final FeatureRepository featureRepository;
    private final UserFeatureRepository userFeatureRepository;

    public boolean isUserCanAccessFeature(String email, String featureName) {
        validateUserEmailAndFeatureName(email, featureName);

        return userFeatureRepository.existsByUserEmailAndFeatureName(email, featureName);
    }

    public boolean updateUserFeature(UserFeatureUpdateRequestDTO userFeatureRequest) {
        String email = userFeatureRequest.getEmail();
        String featureName = userFeatureRequest.getFeatureName();
        boolean enable = userFeatureRequest.isEnable();
        try {
            validateUserEmailAndFeatureName(email, featureName);
        } catch (ValidationException e) {
            return false;
        }

        Optional<UserFeature> existingUserFeature =
                userFeatureRepository.findByUserEmailAndFeatureName(email, featureName);
        if (enable) {
            // Already enable
            if (existingUserFeature.isPresent()) {
                return false;
            } else {
                // Create
                Feature feature = featureRepository.findOneByName(featureName);
                System.out.println(userRepository.findOneByEmail(email));
                System.out.println(feature);
                UserFeature userFeature =
                        UserFeature.builder()
                                .user(userRepository.findOneByEmail(email))
                                .feature(featureRepository.findOneByName(featureName))
                                .build();
                System.out.println(userFeature);
                userFeatureRepository.save(userFeature);
                return true;
            }
        } else {
            if (existingUserFeature.isPresent()) {
                userFeatureRepository.deleteById(existingUserFeature.get().getId());
                return true;

            } else {
                return false;
            }
        }
    }

    void validateUserEmailAndFeatureName(String email, String featureName)
            throws ValidationException {
        if (!userRepository.existsByEmail(email)) {
            throw new ValidationException("User not found");
        }

        if (!featureRepository.existsByName(featureName)) {
            throw new ValidationException("Feature not found");
        }
    }
}
