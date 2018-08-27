package com.eljhoset.pos.item.model;

import com.eljhoset.pos.category.model.jpa.Category;
import com.eljhoset.pos.item.model.jpa.Item;
import com.eljhoset.pos.item.model.jpa.ItemVariant;
import com.eljhoset.pos.item.model.jpa.ItemVariantOption;
import com.eljhoset.pos.item.model.jpa.ItemVariantOptionAllowedValue;
import com.eljhoset.pos.item.model.jpa.ItemVariantOptionValue;
import com.eljhoset.pos.item.model.jpa.Option;
import com.eljhoset.pos.item.model.jpa.OptionValue;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

/**
 *
 * @author Daniel
 */
@UtilityClass
public class ItemGenerator {

    private final Map<String, Option> options = new HashMap<>();
    private final Map<String, OptionValue> values = new HashMap<>();

    public static List<Option> getOptions() {
        return options.entrySet().stream().map(m -> m.getValue()).collect(Collectors.toList());
    }

    public static List<OptionValue> getValues() {
        return values.entrySet().stream().map(m -> m.getValue()).collect(Collectors.toList());
    }

    public static ItemVariantOption createVariantOption(Item item, Option options, List<OptionValue> values) {
        ItemVariantOption itemVariantOption = ItemVariantOption.builder().item(item).options(options).build();
        values.stream().map((value) -> ItemVariantOptionAllowedValue.builder().item(item).options(options)
                .optionValue(value).build()
        ).forEachOrdered((optionAllowedValue) -> {
            itemVariantOption.addAllowedValue(optionAllowedValue);
        });
        return itemVariantOption;
    }

    public static ItemVariantOptionValue createVariantOptionValue(Item item, Option option, OptionValue value) {
        return ItemVariantOptionValue.builder().item(item).options(option)
                .optionValue(value).build();
    }

    public static Item createItem(String name, Category category, String optionsWihtAllowedValues, String variantsValues) {

        Item item = new Item();
        item.setName(name);
        item.setCategory(category);
        item.setOptions(Stream.of(optionsWihtAllowedValues.split(";"))
                .map(m -> m.split(":"))
                .map(m -> createVariantOption(item,
                Optional.ofNullable(options.get(m[0])).orElseGet(() -> {
                    Option op = Option.builder().name(m[0]).build();
                    options.put(m[0], op);
                    return op;
                }),
                Stream.of(m[1].split(","))
                        .map(t -> Optional.ofNullable(values.get(t)).orElseGet(() -> {
                    OptionValue ov = OptionValue.builder().name(t).build();
                    values.put(t, ov);
                    return ov;
                })).collect(Collectors.toList()))).collect(Collectors.toList()));
        item.setVariants(Stream.of(variantsValues.split(";"))
                .map(t -> t.split("\\|"))
                .map(t -> ItemVariant.builder().item(item)
                .sku(t[0].split(":")[0])
                .price(BigDecimal.valueOf(Double.valueOf(t[0].split(":")[1])))
                .values(Stream.of(t[1].split(","))
                        .map(m -> m.split(":"))
                        .map(m -> createVariantOptionValue(item, options.get(m[0]), values.get(m[1]))).collect(Collectors.toList()))
                .build()
                )
                .collect(Collectors.toList()));
        return item;
    }

    public static Item generateTestItem(Category category) {
        return createItem("Shirt", Category.builder().name("Cloth").build(),
                "Color:Blue,Red;Size:Small,Medium,Large",
                "sku_fks3345100:100|Color:Red,Size:Small;");
    }

    public static Item generateTestItem() {
        return generateTestItem(Category.builder().name("Cloth").build());
    }
}
