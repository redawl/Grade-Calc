package com.github.redawl.gradecalc.exceptions;

public class ClassNotFoundException extends Exception {
    public ClassNotFoundException(String className){
        super(String.format("Could not find class %s", className));
    }
}
