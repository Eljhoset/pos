package com.eljhoset.pos.item.controller;

import com.eljhoset.pos.item.model.http.ItemResponse;
import com.eljhoset.pos.item.service.ItemService;
import java.security.Principal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Daniel
 */
@RestController
@RequestMapping("v1/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<Page<ItemResponse>> getAll(Principal principal,
            @PageableDefault(sort = "name") Pageable pageable) {
        return ResponseEntity.ok(itemService.findAll(principal.getName(), pageable));
    }
}
