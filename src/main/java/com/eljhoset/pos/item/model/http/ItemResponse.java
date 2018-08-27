package com.eljhoset.pos.item.model.http;

import com.eljhoset.pos.category.model.http.CategoryResponse;
import com.eljhoset.pos.item.model.jpa.Item;
import com.eljhoset.pos.item.model.jpa.ItemVariant;
import com.eljhoset.pos.item.model.jpa.ItemVariantOption;
import com.eljhoset.pos.item.model.jpa.ItemVariantOptionValue;
import com.eljhoset.pos.item.model.jpa.Option;
import com.eljhoset.pos.item.model.jpa.OptionValue;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 *
 * @author Daniel
 */
@Getter
public class ItemResponse {

    private final Long id;
    private final String name;
    private final List<VariantResponse> variants;
    private final List<ItemOptionResponse> options;
    private final CategoryResponse category;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.variants = item.getVariants().stream().map(m -> new VariantResponse(m)).collect(Collectors.toList());
        this.options = item.getOptions().stream().map(m -> new ItemOptionResponse(m)).collect(Collectors.toList());
        this.category = new CategoryResponse(item.getCategory());
    }

    @Getter
    public class OptionResponse {

        private final Long id;
        private final String name;

        private OptionResponse(Option options) {
            this.id = options.getId();
            this.name = options.getName();
        }
    }

    @Getter
    public class OptionValueResponse {

        private final Long id;
        private final String name;

        private OptionValueResponse(OptionValue optionValue) {
            this.id = optionValue.getId();
            this.name = optionValue.getName();
        }
    }

    @Getter
    public class ItemOptionResponse {

        private final List<OptionValueResponse> allowedValues;
        private final OptionResponse option;

        private ItemOptionResponse(ItemVariantOption itemVariantOption) {
            this.option = new OptionResponse(itemVariantOption.getOptions());
            this.allowedValues = itemVariantOption.getAllowedValues().stream().map(m -> new OptionValueResponse(m.getOptionValue())).collect(Collectors.toList());
        }

    }

    @Getter
    public class VariantResponse {

        private final List<VariantOptionValueResponse> values;
        private final String sku;
        private final BigDecimal price;

        private VariantResponse(ItemVariant variant) {
            this.values = variant.getValues().stream().map(m -> new VariantOptionValueResponse(m)).collect(Collectors.toList());
            this.sku = variant.getSku();
            this.price = variant.getPrice();
        }

        @Getter
        public class VariantOptionValueResponse {

            private final OptionResponse option;
            private final OptionValueResponse value;

            private VariantOptionValueResponse(ItemVariantOptionValue m) {
                this.option = new OptionResponse(m.getOptions());
                this.value = new OptionValueResponse(m.getOptionValue());

            }
        }
    }
}
