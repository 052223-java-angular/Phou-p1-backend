package com.revature.yield.services;

import com.revature.yield.dtos.request.NewRoleRequest;
import com.revature.yield.entities.Role;
import com.revature.yield.repositories.IRoleRepo;
import com.revature.yield.utils.custom_exceptions.RoleNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class RoleService {
    private final IRoleRepo roleRepo;

    /**
     * Saves a new role based on the provided information.
     *
     * @param request the NewRoleRequest object containing role details
     * @return the newly created Role object
     */
    public Role saveRole(NewRoleRequest request) {
        Role newRole = new Role(request.getName());
        return roleRepo.save(newRole);
    }

    /**
     * Finds a role by its name.
     *
     * @param name the name of the role to find
     * @return the Role object with the specified name
     * @throws RoleNotFoundException if the role with the specified name is not
     *                               found
     */
    public Role findByName(String name) {
        return roleRepo.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException(format("Role %s not found", name)));
    }

    /**
     * Checks if a role with the specified name already exists.
     *
     * @param name the name to check for uniqueness
     * @return true if the role name is unique, false otherwise
     */
    public boolean isUniqueRole(String name) {
        return roleRepo.findByName(name).isEmpty();
    }
}
