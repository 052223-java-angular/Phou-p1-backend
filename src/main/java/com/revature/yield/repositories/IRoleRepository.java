package com.revature.yield.repositories;

import com.revature.yield.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, String> {

    /**
     * Finds a role by its name.
     *
     * @param name the name of the role to search for
     * @return an Optional containing the Role object if found, or an empty Optional
     *         otherwise
     */
    Optional<Role> findByName(String name);
}
