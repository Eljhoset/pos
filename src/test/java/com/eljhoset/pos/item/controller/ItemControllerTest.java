package com.eljhoset.pos.item.controller;

import static com.eljhoset.pos.item.model.ItemGenerator.generateTestItem;
import com.eljhoset.pos.item.model.http.ItemResponse;
import com.eljhoset.pos.item.service.ItemService;
import static org.assertj.core.util.Lists.newArrayList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Daniel
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ItemService service;
    private final String PATH = "/v1/items";

    @Test
    public void getAll_shouldReturnAllItems() throws Exception {
        ItemResponse response = generateTestItem().toItemResponse();
        given(service.findAll(anyString())).willReturn(newArrayList(response));
        mvc.perform(get(PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Shirt")))
                .andExpect(jsonPath("$[0].category.name", is("Cloth")))
                .andExpect(jsonPath("$[0].variants", hasSize(1)))
                .andExpect(jsonPath("$[0].variants[0].sku", is("3345100")))
                .andExpect(jsonPath("$[0].variants[0].price", is(100.0)))
                .andExpect(jsonPath("$[0].variants[0].options", hasSize(2)))
                .andExpect(jsonPath("$[0].variants[0].options[0].option.name", is("Color")))
                .andExpect(jsonPath("$[0].variants[0].options[0].value.name", is("Blue")))
                .andExpect(jsonPath("$[0].variants[0].options[1].option.name", is("Size")))
                .andExpect(jsonPath("$[0].variants[0].options[1].value.name", is("Large")))
                .andExpect(jsonPath("$[0].options", hasSize(2)))
                .andExpect(jsonPath("$[0].options[0].option.name", is("Color")))
                .andExpect(jsonPath("$[0].options[0].allowedValues", hasSize(2)))
                .andExpect(jsonPath("$[0].options[0].allowedValues[0].name", is("Red")))
                .andExpect(jsonPath("$[0].options[0].allowedValues[1].name", is("Blue")));
    }

}
