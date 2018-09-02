package com.eljhoset.pos.user.model.jpa;

import com.eljhoset.pos.jpa.model.AccountBaseEntity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Daniel
 */
@Entity
@Getter
public class Users extends AccountBaseEntity implements Serializable {

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Id
    @Setter
    private String username;
    @NotNull
    @NotBlank
    private String password;
    @Size(min = 1)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    public List<GrantedAuthority> getAuthorities() {
        return roles.stream().map(m -> new SimpleGrantedAuthority(m)).collect(Collectors.toList());
    }

    public void addRole(String role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }

}
