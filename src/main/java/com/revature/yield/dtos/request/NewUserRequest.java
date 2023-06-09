package com.revature.yield.dtos.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {
    public String username;
    public String password;
    public String confirmPassword;
}
