package com.eljhoset.pos.security.client.repository;

import com.eljhoset.pos.security.client.model.AppClient;
import java.io.Serializable;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel
 */
@Repository
public interface AppClientRepository extends CrudRepository<AppClient, Serializable> {

    @Query("Select e From AppClient e Where e.status='ACTIVE' and e.clientId=:clientID")
    Optional<AppClient> findByClientId(@Param("clientID") String clientID);
}
