package com.github.redawl.gradecalc.web;

import com.github.redawl.gradecalc.user.UserDto;
import com.github.redawl.gradecalc.user.UserService;
import com.github.redawl.gradecalc.exceptions.InvalidUsernameException;
import com.github.redawl.gradecalc.exceptions.PasswordsDoNotMatchException;
import com.github.redawl.gradecalc.exceptions.UsernameAlreadyTakenException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Register", description = "Manage a users registration")
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Register a user",
            description = "Register a new user. Username cannot be already taken, and cannot be null.",
            responses = {
                    @ApiResponse(description = "Created - User registration was successful", responseCode = "201"),
                    @ApiResponse(description = "Bad Request - User registration was not successful", responseCode = "400")
            }
    )
    public void registerUser(@RequestBody UserDto user){
        try {
            userService.registerUser(user);
        } catch (UsernameAlreadyTakenException | PasswordsDoNotMatchException | InvalidUsernameException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
