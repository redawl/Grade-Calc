package com.github.redawl.GradeCalc.web;

import com.github.redawl.GradeCalc.User.UserDto;
import com.github.redawl.GradeCalc.User.UserService;
import com.github.redawl.GradeCalc.exceptions.InvalidUsernameException;
import com.github.redawl.GradeCalc.exceptions.PasswordsDoNotMatchException;
import com.github.redawl.GradeCalc.exceptions.UsernameAlreadyTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Manage user registration
 * @author Eli Burch
 */
@RestController
@RequestMapping("/api/register")
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Add a new user
     * @param user {@link UserDto} of user to register
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDto user){
        try {
            userService.registerUser(user);
        } catch (UsernameAlreadyTakenException | PasswordsDoNotMatchException | InvalidUsernameException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
