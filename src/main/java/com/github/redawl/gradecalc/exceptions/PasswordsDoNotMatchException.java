package com.github.redawl.gradecalc.exceptions;

public class PasswordsDoNotMatchException extends Exception {
    public PasswordsDoNotMatchException(){
        super("Passwords do not match. Please ensure password and matchingPassword are identical");
    }
}
