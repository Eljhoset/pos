package com.eljhoset.pos.item.controller;

import static com.eljhoset.pos.item.model.ItemGenerator.generateTestItem;
import com.eljhoset.pos.item.model.http.ItemResponse;
import com.eljhoset.pos.item.service.ItemService;
import java.util.List;
import static org.assertj.core.util.Lists.newArrayList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
@EnableSpringDataWebSupport
public class ItemControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ItemService service;
    private final String PATH = "/v1/items";

    @Test
    public void getAll_shouldReturnAllItems() throws Exception {
        ItemResponse response = generateTestItem().toItemResponse();
        final List<ItemResponse> newArrayList = newArrayList(response);
        final PageImpl<ItemResponse> page = new PageImpl<>(newArrayList);
        given(service.findAll(anyString(), any(Pageable.class))).willReturn(page);
        mvc.perform(get(PATH)
                .with(user("user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Shirt")))
                .andExpect(jsonPath("$.content[0].category.name", is("Cloth")))
                .andExpect(jsonPath("$.content[0].variants", hasSize(1)))
                .andExpect(jsonPath("$.content[0].variants[0].sku", is("sku_fks3345100")))
                .andExpect(jsonPath("$.content[0].variants[0].price", is(100.0)))
                .andExpect(jsonPath("$.content[0].variants[0].values", hasSize(2)))
                .andExpect(jsonPath("$.content[0].variants[0].values[0].option.name", is("Color")))
                .andExpect(jsonPath("$.content[0].variants[0].values[0].value.name", is("Red")))
                .andExpect(jsonPath("$.content[0].variants[0].values[1].option.name", is("Size")))
                .andExpect(jsonPath("$.content[0].variants[0].values[1].value.name", is("Small")))
                .andExpect(jsonPath("$.content[0].options", hasSize(2)))
                .andExpect(jsonPath("$.content[0].options[0].option.name", is("Color")))
                .andExpect(jsonPath("$.content[0].options[0].allowedValues", hasSize(2)))
                .andExpect(jsonPath("$.content[0].options[0].allowedValues[0].name", is("Blue")))
                .andExpect(jsonPath("$.content[0].options[0].allowedValues[1].name", is("Red")));
    }

}
