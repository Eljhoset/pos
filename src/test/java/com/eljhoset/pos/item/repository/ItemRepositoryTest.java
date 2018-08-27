/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eljhoset.pos.item.repository;

import com.eljhoset.pos.account.model.jpa.Account;
import com.eljhoset.pos.category.model.jpa.Category;
import static com.eljhoset.pos.item.model.ItemGenerator.generateTestItem;
import com.eljhoset.pos.item.model.jpa.Item;
import com.eljhoset.pos.item.model.jpa.ItemVariantOptionValue;
import com.eljhoset.pos.jpa.config.TenantContext;
import java.util.List;
import java.util.stream.Collectors;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Daniel
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ItemRepository itemRepository;
    private Account account;

    @Before
    public void setUp() {
        account = new Account();
        entityManager.persist(account);
        TenantContext.setCurrentTenant(account);
        Category category = Category.builder().name("Cloth").build();
        Item item = generateTestItem(category);
        entityManager.persist(category);
        entityManager.persistAndFlush(item);
        Account account2 = new Account();
        entityManager.persist(account2);
        TenantContext.setCurrentTenant(account2);
        entityManager.persistAndFlush(generateTestItem(category));
    }

    @Test
    public void findAll() {
        Page<Item> page = itemRepository.findAll(account, null);
        List<Item> items = page.getContent();
        assertThat(items, hasSize(1));
        Item item = items.get(0);
        assertThat(item.getOptions().stream().map(m -> m.getOptions()).map(m -> m.getName()).collect(Collectors.toList()),
                hasItems("Size", "Color"));
        assertThat(item.getOptions().stream().filter(f -> f.getOptions().getName().equals("Color")).findFirst()
                .map(m -> m.getAllowedValues()).get().stream().map(m -> m.getOptionValue()).map(m -> m.getName()).collect(Collectors.toList()),
                hasItems("Red", "Blue"));
        assertThat(item.getOptions().stream().filter(f -> f.getOptions().getName().equals("Size")).findFirst()
                .map(m -> m.getAllowedValues()).get().stream().map(m -> m.getOptionValue()).map(m -> m.getName()).collect(Collectors.toList()),
                hasItems("Small", "Large", "Medium"));
        assertThat(item.getVariants(), hasSize(1));
        List<ItemVariantOptionValue> itemVariantOptionValues = item.getVariants().stream().findFirst().map(m -> m.getValues()).get();
        assertThat(itemVariantOptionValues.stream().map(m -> m.getOptions()).map(m -> m.getName()).collect(Collectors.toList()),
                hasItems("Size", "Color"));
        assertTrue(itemVariantOptionValues.stream().anyMatch(f -> f.getOptions().getName().equals("Color") && f.getOptionValue().getName().equals("Red"))
                && itemVariantOptionValues.stream().anyMatch(f -> f.getOptions().getName().equals("Size") && f.getOptionValue().getName().equals("Small")));

    }

}
