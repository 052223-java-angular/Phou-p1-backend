package com.revature.yield.controllers;

import com.revature.yield.dtos.request.NewRoleRequest;
import com.revature.yield.services.RoleService;
import com.revature.yield.utils.custom_exceptions.ResourceConflictException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * Creates a new role.
     *
     * @param request the NewRoleRequest object containing role creation details
     * @return ResponseEntity with the HTTP status indicating the success or failure
     *         of the role creation
     */
    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody NewRoleRequest request) {
        if (!roleService.isUniqueRole(request.getName())) {
            throw new ResourceConflictException("Role " + request.getName() + " already exists");
        }

        roleService.saveRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
