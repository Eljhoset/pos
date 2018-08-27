package com.eljhoset.pos.security.client.service;

import com.eljhoset.pos.security.client.repository.AppClientRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service
public class ClientService implements ClientDetailsService {

    private final AppClientRepository appClientRepository;

    public ClientService(AppClientRepository appClientRepository) {
        this.appClientRepository = appClientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return appClientRepository.findByClientId(clientId).map(m -> m.toBaseClientDetails()).orElseThrow(() -> {
            return new ClientRegistrationException(String.format("Client[%s] Not Found", clientId));
        });
    }

}
