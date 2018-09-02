package com.eljhoset.pos.item.service;

import com.eljhoset.pos.category.model.jpa.Category;
import com.eljhoset.pos.item.model.http.ItemResponse;
import com.eljhoset.pos.item.model.jpa.Item;
import com.eljhoset.pos.item.model.jpa.ItemOption;
import com.eljhoset.pos.item.model.jpa.ItemOptionValue;
import com.eljhoset.pos.item.model.jpa.ItemVariant;
import com.eljhoset.pos.item.model.jpa.ItemVariantOption;
import com.eljhoset.pos.item.model.jpa.ItemVariantOptionAllowedValue;
import com.eljhoset.pos.item.model.jpa.ItemVariantOptionValue;
import com.eljhoset.pos.item.repository.ItemOptionRepository;
import com.eljhoset.pos.item.repository.ItemOptionValueRepository;
import com.eljhoset.pos.item.repository.ItemRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    private final ItemOptionRepository itemOptionRepository;
    private final ItemOptionValueRepository itemOptionValueRepository;

    public ItemService(ItemRepository itemRepository, ItemOptionRepository itemOptionRepository,
            ItemOptionValueRepository itemOptionValueRepository) {
        this.itemRepository = itemRepository;
        this.itemOptionRepository = itemOptionRepository;
        this.itemOptionValueRepository = itemOptionValueRepository;
    }

    public Page<ItemResponse> findAll(@NotNull @NotBlank String username, Pageable pageable) {
        return itemRepository.findAll(pageable)
                .map(m -> m.toItemResponse());
    }

    private ItemVariantOption createVariantOption(Item item, ItemOption options, List<ItemOptionValue> values) {
        ItemVariantOption itemVariantOption = ItemVariantOption.builder().item(item).options(options).build();
        values.stream().map((value) -> ItemVariantOptionAllowedValue.builder().item(item).options(options)
                .optionValue(value).build()
        ).forEachOrdered((optionAllowedValue) -> {
            itemVariantOption.addAllowedValue(optionAllowedValue);
        });
        return itemVariantOption;
    }

    private ItemVariantOptionValue createVariantOptionValue(Item item, ItemOption option, ItemOptionValue value) {
        return ItemVariantOptionValue.builder().item(item).options(option)
                .optionValue(value).build();
    }

    public ItemResponse createItem(String name, Category category, String optionsWihtAllowedValues, String variantsValues) {
        final Map<String, ItemOption> options = new HashMap<>();
        final Map<String, ItemOptionValue> values = new HashMap<>();
        Item item = new Item();
        item.setName(name);
        item.setCategory(category);

        final List<ItemVariantOption> itemOptions = Stream.of(optionsWihtAllowedValues.split(";"))
                .map(m -> m.split(":"))
                .map(m -> {
                    return createVariantOption(item,
                            Optional.ofNullable(options.get(m[0])).orElseGet(() -> {
                                return itemOptionRepository.findByName(m[0]).orElseGet(() -> {
                                    ItemOption itemOption = ItemOption.builder().name(m[0]).build();
                                    itemOptionRepository.save(itemOption);
                                    options.put(m[0], itemOption);
                                    return itemOption;
                                });
                            }),
                            Stream.of(m[1].split(","))
                                    .map(t -> {
                                        return Optional.ofNullable(values.get(t)).orElseGet(() -> {
                                            return itemOptionValueRepository.findByName(t).orElseGet(() -> {
                                                ItemOptionValue itemOptionValue = ItemOptionValue.builder().name(t).build();
                                                itemOptionValueRepository.save(itemOptionValue);
                                                values.put(t, itemOptionValue);
                                                return itemOptionValue;
                                            });
                                        });
                                    }).collect(Collectors.toList()));
                }).collect(Collectors.toList());

        item.setOptions(itemOptions);
        final List<ItemVariant> itemVariants = Stream.of(variantsValues.split(";"))
                .map(t -> t.split("\\|"))
                .map(t -> ItemVariant.builder().item(item)
                .sku(t[0].split(":")[0])
                .price(BigDecimal.valueOf(Double.valueOf(t[0].split(":")[1])))
                .values(Stream.of(t[1].split(","))
                        .map(m -> m.split(":"))
                        .map(m -> createVariantOptionValue(item, options.get(m[0]), values.get(m[1]))).collect(Collectors.toList()))
                .build())
                .collect(Collectors.toList());

        item.setVariants(itemVariants);
        Item saved = itemRepository.save(item);
        return saved.toItemResponse();
    }

}
