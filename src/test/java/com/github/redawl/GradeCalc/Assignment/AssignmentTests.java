package com.github.redawl.GradeCalc.Assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssignmentTests {
    @Test
    void calculateGradeShouldReturnCorrectValue(){
        Assignment assignment = new Assignment();
        assignment.setAssignmentScore(80);
        assignment.setAssignmentWeight(.5);
        Assertions.assertEquals(assignment.calculateValue(), 40);
    }
}
