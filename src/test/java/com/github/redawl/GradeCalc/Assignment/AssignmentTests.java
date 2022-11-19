package com.github.redawl.GradeCalc.Assignment;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssignmentTests {
    @Test
    void calculateGradeShouldReturnCorrectValue(){
        double testAssignmentScore = 80;
        double testAssignmentWeight = .5;
        Assignment assignment = new Assignment();
        assignment.setAssignmentScore(testAssignmentScore);
        assignment.setAssignmentWeight(testAssignmentWeight);
        Assertions.assertEquals(assignment.calculateValue(), testAssignmentScore * testAssignmentWeight);
    }

    @Test
    void validateFieldsArePopulatedShouldReturnTrueWithAllFields(){
        Assignment assignment = new Assignment();
        assignment.setAssignmentScore(100);
        assignment.setAssignmentWeight(.5);
        assignment.setAssignmentName("Testing");
        assignment.setClassName("Testing Class");

        Assertions.assertTrue(assignment.validateAllFieldsArePopulated());
    }

    @Test
    void validateFieldsArePopulatedShouldReturnFalseWithMissingFields(){
        Assignment assignment = new Assignment();
        assignment.setAssignmentScore(100);
        assignment.setAssignmentName("Testing");
        assignment.setClassName("Testing Class");

        Assertions.assertFalse(assignment.validateAllFieldsArePopulated());
    }

    @Test
    void toJSONShouldReturnValidJSONObject(){
        Assignment assignment = new Assignment();
        assignment.setAssignmentScore(100);
        assignment.setAssignmentWeight(.5);
        assignment.setAssignmentName("Testing");
        assignment.setClassName("Testing Class");

        Assertions.assertEquals(assignment.toJSON(), "{\"assignmentName\": \"Testing\",\"className\": \"Testing Class\"," +
                "\"assignmentWeight\": 0.500000,\"assignmentScore\": 100.000000}");
    }
}
