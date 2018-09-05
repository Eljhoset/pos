package com.eljhoset.pos.boot;

import com.eljhoset.pos.account.model.jpa.Account;
import com.eljhoset.pos.account.repository.AccountRepository;
import com.eljhoset.pos.category.model.jpa.Category;
import com.eljhoset.pos.category.repository.CategoryRepository;
import com.eljhoset.pos.item.service.ItemService;
import com.eljhoset.pos.jpa.config.TenantContext;
import com.eljhoset.pos.user.model.jpa.Users;
import com.eljhoset.pos.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Daniel
 */
@Configuration
public class BootstrapApp implements ApplicationRunner {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        accountRepository.save(account);
        TenantContext.setCurrentTenant(account);
        Category category = Category.builder().name("Cloth").build();
        categoryRepository.save(category);
        itemService.createItem("Shirt", category,
                "Color:Blue,Red;Size:Small,Medium,Large",
                "sku_fks3345100:100|Color:Red,Size:Small;");
        Users u = new Users();
        u.setUsername("eljhoset");
        u.setPassword(("Hello123++"));
        u.addRole("ADMIN");
        userRepository.save(u);
    }

}
