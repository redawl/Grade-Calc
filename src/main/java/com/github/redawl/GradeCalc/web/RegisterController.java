package com.github.redawl.GradeCalc.web;

import com.github.redawl.GradeCalc.User.UserDto;
import com.github.redawl.GradeCalc.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDto user){
        if(!userService.registerUser(user)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already taken");
        }
    }
}
