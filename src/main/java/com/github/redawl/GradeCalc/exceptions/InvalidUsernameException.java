package com.github.redawl.GradeCalc.exceptions;

public class InvalidUsernameException extends Exception{
    public InvalidUsernameException(){
        super("Username cannot be empty or null");
    }
}
