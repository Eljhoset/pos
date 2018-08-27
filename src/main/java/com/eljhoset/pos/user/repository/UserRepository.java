package com.eljhoset.pos.user.repository;

import com.eljhoset.pos.user.model.jpa.Users;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel
 */
@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

    Optional<Users> findByUsername(String username);
}
