package com.github.redawl.gradecalc.exceptions;

public class UsernameAlreadyTakenException extends Exception {
    public UsernameAlreadyTakenException(String username){
        super(String.format("%s is already registered. Please try a different username", username));
    }
}
