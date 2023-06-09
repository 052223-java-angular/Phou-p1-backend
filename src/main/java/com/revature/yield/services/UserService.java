package com.revature.yield.services;

import com.revature.yield.dtos.request.NewLoginRequest;
import com.revature.yield.dtos.request.NewUserRequest;
import com.revature.yield.dtos.response.Principal;
import com.revature.yield.entities.Role;
import com.revature.yield.entities.User;
import com.revature.yield.repositories.UserRepository;
import com.revature.yield.utils.custom_exceptions.InvalidCredentialException;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final RoleService roleService;

    /**
     * Registers a new user based on the provided information.
     *
     * @param req the NewUserRequest object containing user registration details
     * @return the newly registered User object
     */
    public User registerUser(NewUserRequest req) {
        // find role USER
        Role foundRole = roleService.findByName("USER");

        // hash password
        String hashed = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());

        // create new user
        User newUser = new User(req.getUsername(), hashed, foundRole);

        // save and return user
        return userRepo.save(newUser);
    }

    public Principal login(NewLoginRequest request) {
        Optional<User> userOpt = userRepo.findByUsername(request.getUsername());

        if (userOpt.isPresent()) {
            User foundUser = userOpt.get();
            if (BCrypt.checkpw(request.getPassword(), foundUser.getPassword())) {
                return new Principal(foundUser);
            }
        }

        throw new InvalidCredentialException("Invalid credential");
    }

    /**
     * Checks if the provided username is valid.
     *
     * @param username the username to validate
     * @return true if the username is valid, false otherwise
     */
    public boolean isValidUsername(String username) {
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    /**
     * Checks if the provided username is unique.
     *
     * @param username the username to check for uniqueness
     * @return true if the username is unique, false otherwise
     */
    public boolean isUniqueUsername(String username) {
        Optional<User> userOpt = userRepo.findByUsername(username);
        return userOpt.isEmpty();
    }

    /**
     * Checks if the provided password is valid.
     *
     * @param password the password to validate
     * @return true if the password is valid, false otherwise
     */
    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    /**
     * Checks if the provided password and confirm password match.
     *
     * @param password        the password to compare
     * @param confirmPassword the confirm password to compare
     * @return true if the passwords match, false otherwise
     */
    public boolean isSamePassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
