package com.eljhoset.pos.jpa.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

/**
 *
 * @author Daniel
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("");
    }

}
