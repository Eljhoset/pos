package com.eljhoset.pos.item.service;

import com.eljhoset.pos.item.model.http.ItemResponse;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service
public class ItemService {

    public List<ItemResponse> findAll(@NotNull @NotBlank String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
