package com.eljhoset.pos.item.model;

import com.eljhoset.pos.category.model.jpa.Category;
import com.eljhoset.pos.item.model.jpa.Item;
import com.eljhoset.pos.item.model.jpa.ItemOption;
import com.eljhoset.pos.item.model.jpa.ItemOptionValue;
import com.eljhoset.pos.item.model.jpa.ItemOptions;
import com.eljhoset.pos.item.model.jpa.ItemVariant;
import com.eljhoset.pos.item.model.jpa.ItemVariantOption;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Daniel
 */
public class ItemGenerator {

    final static Map<String, ItemOption> OPTIONS = new HashMap<>();
    final static Map<String, ItemOptionValue> VALUES = new HashMap<>();

    public static Item generateItem(String name, Category category, String itemOptions, String variants) {
        Item item = Item.builder().name(name).category(category).build();

        item.setOptions(Stream.of(itemOptions.split(";")).map(m -> m.split(":")).map((m) -> {
            ItemOption option = Optional.ofNullable(OPTIONS.get(m[0])).orElseGet(() -> {
                ItemOption itemOption = ItemOption.builder().name(m[0]).build();
                OPTIONS.put(m[0], itemOption);
                return itemOption;
            });
            List<ItemOptionValue> allowedValues = Stream.of(m[1].split(",")).map(t -> Optional.ofNullable(VALUES.get(t)).orElseGet(() -> {
                ItemOptionValue value = ItemOptionValue.builder().name(t).build();
                VALUES.put(t, value);
                return value;
            })).collect(Collectors.toList());
            return generateItemOption(item, option, allowedValues);
        }).collect(Collectors.toList()));

        item.setVariants(Stream.of(variants.split(";")).map((m) -> {
            String[] split = m.split("\\|");
            String[] variantData = split[0].split(":");
            return generateVariant(item, variantData[0], Double.valueOf(variantData[1]),
                    Stream.of(split[1].split(","))
                            .map(y -> y.split(":"))
                            .map(y -> generateVariantOption(item, OPTIONS.get(y[0]), VALUES.get(y[1]))).collect(Collectors.toList()));
        }).collect(Collectors.toList()));
        return item;
    }

    private static ItemOptions generateItemOption(Item item, ItemOption option, List<ItemOptionValue> allowedValues) {
        return ItemOptions.builder().allowedValues(allowedValues).item(item).itemOption(option).build();
    }

    public static Item generateItem(String name, String categoryName, String itemOptions, String variants) {
        return generateItem(name, Category.builder().name(categoryName).build(), itemOptions, variants);
    }

    private static ItemVariantOption generateVariantOption(Item item, ItemOption option, ItemOptionValue value) {
        return ItemVariantOption.builder().item(item).options(option).optionValue(value).build();
    }

    private static ItemVariant generateVariant(Item item, String sku, Double price, List<ItemVariantOption> options) {
        return ItemVariant.builder().item(item).sku(sku).price(BigDecimal.valueOf(price)).options(options).build();
    }

    public static List<ItemOption> getOptions() {
        return OPTIONS.entrySet().stream().map(m -> m.getValue()).collect(Collectors.toList());
    }

    public static List<ItemOptionValue> getvalues() {
        return VALUES.entrySet().stream().map(m -> m.getValue()).collect(Collectors.toList());
    }

    public static Item generateTestItem() {
        return generateItem("Shirt", "Cloth", "Color:Red,Blue;Size:Small,Large,Medium", "3345100:100|Color:Blue,Size:Large");
    }

}
