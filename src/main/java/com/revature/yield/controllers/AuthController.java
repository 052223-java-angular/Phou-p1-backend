package com.revature.yield.controllers;

import com.revature.yield.dtos.request.NewLoginRequest;
import com.revature.yield.dtos.request.NewUserRequest;
import com.revature.yield.dtos.response.Principal;
import com.revature.yield.services.JwtTokenService;
import com.revature.yield.services.UserService;
import com.revature.yield.utils.custom_exceptions.ResourceConflictException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenService tokenService;

    /**
     * Registers a new user.
     *
     * @param request the NewUserRequest object containing user registration details
     * @return ResponseEntity with the HTTP status indicating the success or failure
     *         of the registration
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody NewUserRequest request) {
        System.out.println("Registering user");
        // if username is not valid, throw exception
        if (!userService.isValidUsername(request.getUsername())) {
            throw new ResourceConflictException(
                    "Username needs to be 8-20 characters long and can only contain letters, numbers, periods, and underscores");
        }

        // if username is not unique, throw exception
        if (!userService.isUniqueUsername(request.getUsername())) {
            throw new ResourceConflictException("Username is not unique");
        }

        // if password is not valid, throw exception
        if (!userService.isValidPassword(request.getPassword())) {
            throw new ResourceConflictException(
                    "Password needs to be at least 8 characters long and contain at least one letter and one number");
        }

        // if password and confirm password do not match, throw exception
        if (!userService.isSamePassword(request.getPassword(), request.getConfirmPassword())) {
            throw new ResourceConflictException("Passwords do not match");
        }

        // register user
        userService.registerUser(request);

        // return 201 - CREATED
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Principal> login(@RequestBody NewLoginRequest request) {
        // userservice to call login method
        Principal principal = userService.login(request);

        // create a jwt token
        String token = tokenService.generateToken(principal);

        principal.setToken(token);

        // return status ok and return principal object
        return ResponseEntity.status(HttpStatus.OK).body(principal);
    }

}


