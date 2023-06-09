package com.revature.yield.dtos.response;

import com.revature.yield.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Principal {
    private UUID id;
    private String username;
    private String role;
    private String token;

    public Principal(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole().getName();
    }

}
