package com.company.project.userfeature;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import com.company.project.base.AbstractControllerTest;
import com.company.project.feature.Feature;
import com.company.project.feature.FeatureRepository;
import com.company.project.user.User;
import com.company.project.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UpdateUserFeatureTest extends AbstractControllerTest {

    @Autowired private FeatureRepository featureRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UserFeatureRepository userFeatureRepository;

    private String featureApiUrl = "/feature";
    private String email = "john@example.com";
    private String featureName = "feature A";
    private String notFoundEmail = "test@test.com";
    private String notFoundFeature = "feature B";
    private User sampleUser;

    private Feature sampleFeature;
    private UserFeature sampleUserFeature;

    @BeforeEach
    public void setup() {
        sampleUser = User.builder().email(email).build();
        sampleFeature = Feature.builder().name(featureName).build();

        userRepository.save(sampleUser);
        featureRepository.save(sampleFeature);

        this.sampleUserFeature =
                UserFeature.builder().user(sampleUser).feature(sampleFeature).build();

        userFeatureRepository.save(this.sampleUserFeature);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        featureRepository.deleteAll();
        userFeatureRepository.deleteAll();
    }

    void performPostSuccess(UserFeatureUpdateRequestDTO userFeatureRequest) throws Exception {
        this.performPostRequest(featureApiUrl, userFeatureRequest, status.isOk());
    }

    void performPostFail(UserFeatureUpdateRequestDTO userFeatureRequest) throws Exception {
        this.performPostRequest(featureApiUrl, userFeatureRequest, status.isNotModified())
                .andExpect(status.reason(containsString("Database update failed")));
    }

    @Test
    void testEmailNotExist() throws Exception {
        UserFeatureUpdateRequestDTO userFeatureRequest =
                new UserFeatureUpdateRequestDTO(notFoundEmail, featureName, false);

        performPostFail(userFeatureRequest);
    }

    @Test
    void testFeatureNotExist() throws Exception {
        UserFeatureUpdateRequestDTO userFeatureRequest =
                new UserFeatureUpdateRequestDTO(email, notFoundFeature, false);

        performPostFail(userFeatureRequest);
    }

    @Test
    void testDeleteUserFeatureSuccess() throws Exception {
        UserFeatureUpdateRequestDTO userFeatureRequest =
                new UserFeatureUpdateRequestDTO(email, featureName, false);

        performPostSuccess(userFeatureRequest);

        assertThat(userFeatureRepository.existsByUserEmailAndFeatureName(email, featureName))
                .isFalse();
    }

    @Test
    void testAddUserFeatureSuccess() throws Exception {
        userFeatureRepository.deleteAll();
        UserFeatureUpdateRequestDTO userFeatureRequest =
                new UserFeatureUpdateRequestDTO(email, featureName, true);

        performPostSuccess(userFeatureRequest);

        assertThat(userFeatureRepository.existsByUserEmailAndFeatureName(email, featureName))
                .isTrue();
    }

    @Test
    void testDeleteUserFeatureWhenNotExist() throws Exception {
        userFeatureRepository.deleteAll();
        UserFeatureUpdateRequestDTO userFeatureRequest =
                new UserFeatureUpdateRequestDTO(email, featureName, false);

        performPostFail(userFeatureRequest);
    }

    @Test
    void testAddUserFeatureWhenExist() throws Exception {
        UserFeatureUpdateRequestDTO userFeatureRequest =
                new UserFeatureUpdateRequestDTO(email, featureName, true);

        performPostFail(userFeatureRequest);
    }
}
