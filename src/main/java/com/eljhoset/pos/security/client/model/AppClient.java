package com.eljhoset.pos.security.client.model;

import com.eljhoset.pos.jpa.model.BaseEntity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 *
 * @author Daniel
 */
@Entity
@Getter
public class AppClient extends BaseEntity implements Serializable {

    @Id
    private String clientId;
    private String secret;
    private int tokenValidity;
    private int refreshTokenValidity;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> resourcesId;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> grantTypes;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> authorities;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> scopes;

    public void addResourcesId(String resourceId) {
        if (this.resourcesId == null) {
            this.resourcesId = new HashSet<>();
        }
        grantTypes.add(resourceId);
    }

    public void addGrantTypes(String grantType) {
        if (this.grantTypes == null) {
            this.grantTypes = new HashSet<>();
        }
        grantTypes.add(grantType);
    }

    public void addAuthorities(String authority) {
        if (authorities == null) {
            authorities = new HashSet<>();
        }
        authorities.add(authority);
    }

    public void addScopes(String scope) {
        if (scopes == null) {
            scopes = new HashSet<>();
        }
        scopes.add(scope);
    }

    public BaseClientDetails toBaseClientDetails() {
        BaseClientDetails baseClientDetails = new BaseClientDetails();
        baseClientDetails.setClientId(clientId);
        baseClientDetails.setClientSecret(secret);
        baseClientDetails.setAccessTokenValiditySeconds(tokenValidity);
        baseClientDetails.setRefreshTokenValiditySeconds(refreshTokenValidity);
        baseClientDetails.setAuthorizedGrantTypes(grantTypes);
        baseClientDetails.setScope(scopes);
        baseClientDetails.setResourceIds(resourcesId);
        baseClientDetails.setAuthorities(authorities.stream().map(m -> new SimpleGrantedAuthority(m)).collect(Collectors.toList()));
        return baseClientDetails;
    }

}
