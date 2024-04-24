package com.company.project.userfeature;

import com.company.project.base.AbstractControllerTest;
import com.company.project.feature.Feature;
import com.company.project.feature.FeatureRepository;
import com.company.project.user.User;
import com.company.project.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class GetUserFeatureTest extends AbstractControllerTest {

    @Autowired private FeatureRepository featureRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UserFeatureRepository userFeatureRepository;

    // @MockBean private UserFeatureService userFeatureService;

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

    @Test
    void testGetNoInput() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        this.performGetRequest(featureApiUrl, params, status.isBadRequest());
    }

    @Test
    void testGetWhenUserInputNotExist() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", notFoundEmail);
        params.add("featureName", featureName);

        this.performGetRequest(featureApiUrl, params, status.isBadRequest())
                .andExpect(content.json("{'message':'User not found'}"));
    }

    @Test
    void testGetWhenFeatureNotExist() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", email);
        params.add("featureName", notFoundFeature);

        this.performGetRequest(featureApiUrl, params, status.isBadRequest())
                .andExpect(content.json("{'message':'Feature not found'}"));
    }

    @Test
    void testGetUserFeatureExist() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", email);
        params.add("featureName", featureName);

        ResultActions result = this.performGetRequest(featureApiUrl, params, status.isOk());

        result.andExpect(content.json("{'canAccess':true}"));
    }

    @Test
    void testGetUserFeatureNotExist() throws Exception {
        userFeatureRepository.deleteAll();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", email);
        params.add("featureName", featureName);

        ResultActions result = this.performGetRequest(featureApiUrl, params, status.isOk());

        result.andExpect(content.json("{'canAccess':false}"));
    }

    @Test
    void testPostUserFeatureNotExist() throws Exception {
        userFeatureRepository.deleteAll();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", email);
        params.add("featureName", featureName);

        ResultActions result = this.performGetRequest(featureApiUrl, params, status.isOk());

        result.andExpect(content.json("{'canAccess':false}"));
    }
}
