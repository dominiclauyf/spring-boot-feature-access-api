package com.company.project.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.util.MultiValueMap;

@EnableConfigurationProperties
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {
    public static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(
                    MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));

    @Autowired private MockMvc mvc;

    @Autowired private ObjectMapper objectMapper;

    protected StatusResultMatchers status = MockMvcResultMatchers.status();
    protected ContentResultMatchers content = MockMvcResultMatchers.content();

    protected ResultActions performGetRequest(
            String path, MultiValueMap<String, String> params, ResultMatcher expectedStatus)
            throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(path).params(params);

        return mvc.perform(requestBuilder).andExpect(expectedStatus);
    }

    protected ResultActions performPostRequest(
            String path, Object object, ResultMatcher expectedStatus) throws Exception {

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(path)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(object));

        return mvc.perform(requestBuilder).andExpect(expectedStatus);
    }
}
