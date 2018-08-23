package com.eljhoset.pos.item.model.http;

import com.eljhoset.pos.category.model.http.CategoryResponse;
import com.eljhoset.pos.item.model.jpa.Item;
import com.eljhoset.pos.item.model.jpa.ItemOption;
import com.eljhoset.pos.item.model.jpa.ItemOptionValue;
import com.eljhoset.pos.item.model.jpa.ItemOptions;
import com.eljhoset.pos.item.model.jpa.ItemVariantOption;
import com.eljhoset.pos.item.model.jpa.ItemVariant;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 *
 * @author Daniel
 */
@Getter
public class ItemResponse {

    private final String name;
    private final Long id;
    private final List<ItemVariantResponse> variants;
    private final List<ItemOptionResponse> options;
    private final CategoryResponse category;

    public ItemResponse(Item item) {
        this.name = item.getName();
        this.id = item.getId();
        this.variants = item.getVariants().stream().map(m -> new ItemVariantResponse(m)).collect(Collectors.toList());
        this.options = item.getOptions().stream().map(m -> new ItemOptionResponse(m)).collect(Collectors.toList());
        this.category = item.getCategory().toCategoryResponse();
    }

    @Getter
    public static class ItemVariantResponse {

        private final String sku;
        private final double price;
        private final List<ItemVariantOptionResponse> options;

        private ItemVariantResponse(ItemVariant itemVariant) {
            this.sku = itemVariant.getSku();
            this.price = itemVariant.getPrice().doubleValue();
            this.options = itemVariant.getOptions().stream().map(m -> new ItemVariantOptionResponse(m)).collect(Collectors.toList());
        }

    }

    @Getter
    public static class ItemOptionResponse {

        private final OptionResponse option;
        private final List<OptionValueResponse> allowedValues;

        private ItemOptionResponse(ItemOptions itemOptions) {
            this.option = new OptionResponse(itemOptions.getItemOption());
            this.allowedValues = itemOptions.getAllowedValues().stream().map(m -> new OptionValueResponse(m)).collect(Collectors.toList());
        }
    }

    @Getter
    public static class OptionValueResponse {

        private final Long id;
        private final String name;

        private OptionValueResponse(ItemOptionValue itemOptionValue) {
            this.id = itemOptionValue.getId();
            this.name = itemOptionValue.getName();
        }
    }

    @Getter
    public static class OptionResponse {

        private final Long id;
        private final String name;

        private OptionResponse(ItemOption itemOption) {
            this.id = itemOption.getId();
            this.name = itemOption.getName();
        }
    }

    @Getter
    public static class ItemVariantOptionResponse {

        private final OptionResponse option;
        private final OptionValueResponse value;

        private ItemVariantOptionResponse(ItemVariantOption itemVarianOption) {
            this.option = new OptionResponse(itemVarianOption.getOptions());
            this.value = new OptionValueResponse(itemVarianOption.getOptionValue());
        }

    }

}
