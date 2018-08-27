package com.eljhoset.pos.item.service;

import com.eljhoset.pos.item.model.http.ItemResponse;
import com.eljhoset.pos.item.repository.ItemRepository;
import com.eljhoset.pos.user.service.UserService;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;

    public ItemService(ItemRepository itemRepository, UserService userService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    public Page<ItemResponse> findAll(@NotNull @NotBlank String username, Pageable pageable) {
        return itemRepository.findAll(userService.findUserByUsername(username).getAccount(), pageable)
                .map(m -> m.toItemResponse());
    }
}
