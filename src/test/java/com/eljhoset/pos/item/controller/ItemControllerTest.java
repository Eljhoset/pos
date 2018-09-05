package com.eljhoset.pos.item.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 *
 * @author Daniel
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemControllerTest {

    @Autowired
    private MockMvc mvc;
    private final String PATH = "/v1/items";

    private String obtainAccessToken(String username, String password) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);
        ResultActions result
                = mvc.perform(post("/oauth/token")
                        .params(params)
                        .with(httpBasic("my-client", "secret"))
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    @Transactional
    public void getAll_shouldReturnAllItems() throws Exception {
        String accessToken = obtainAccessToken("eljhoset", "Hello123++");
        mvc.perform(get(PATH)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").exists())
                .andExpect(jsonPath("$.content[0].name", is("Shirt")))
                .andExpect(jsonPath("$.content[0].category.id").exists())
                .andExpect(jsonPath("$.content[0].category.name", is("Cloth")))
                .andExpect(jsonPath("$.content[0].variants", hasSize(1)))
                .andExpect(jsonPath("$.content[0].variants[0].sku", is("sku_fks3345100")))
                .andExpect(jsonPath("$.content[0].variants[0].price", is(100.0)))
                .andExpect(jsonPath("$.content[0].variants[0].values", hasSize(2)))
                .andExpect(jsonPath("$.content[0].variants[0].values[0].option.id").exists())
                .andExpect(jsonPath("$.content[0].variants[0].values[0].option.name", is("Color")))
                .andExpect(jsonPath("$.content[0].variants[0].values[0].value.id").exists())
                .andExpect(jsonPath("$.content[0].variants[0].values[0].value.name", is("Red")))
                .andExpect(jsonPath("$.content[0].variants[0].values[1].option.id").exists())
                .andExpect(jsonPath("$.content[0].variants[0].values[1].option.name", is("Size")))
                .andExpect(jsonPath("$.content[0].variants[0].values[1].value.id").exists())
                .andExpect(jsonPath("$.content[0].variants[0].values[1].value.name", is("Small")))
                .andExpect(jsonPath("$.content[0].options", hasSize(2)))
                .andExpect(jsonPath("$.content[0].options[0].option.name", is("Color")))
                .andExpect(jsonPath("$.content[0].options[0].allowedValues", hasSize(2)))
                .andExpect(jsonPath("$.content[0].options[0].allowedValues[0].id").exists())
                .andExpect(jsonPath("$.content[0].options[0].allowedValues[0].name", is("Blue")))
                .andExpect(jsonPath("$.content[0].options[0].allowedValues[1].id").exists())
                .andExpect(jsonPath("$.content[0].options[0].allowedValues[1].name", is("Red")));
    }

}
