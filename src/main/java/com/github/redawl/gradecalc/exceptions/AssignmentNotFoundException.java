package com.github.redawl.gradecalc.exceptions;

public class AssignmentNotFoundException extends Exception {
    public AssignmentNotFoundException(String assignmentName, String className){
        super(String.format("Could not find and assignment %s for class %s.", assignmentName, className));
    }
}
