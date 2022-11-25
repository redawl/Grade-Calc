package com.github.redawl.GradeCalc.Assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssignmentTests {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void calculateGradeShouldReturnCorrectValue(){
        double testAssignmentScore = 80;
        double testAssignmentWeight = .5;
        AssignmentDto assignmentDto = new AssignmentDto();
        assignmentDto.setAssignmentScore(testAssignmentScore);
        assignmentDto.setAssignmentWeight(testAssignmentWeight);
        Assertions.assertEquals(assignmentDto.calculateValue(), testAssignmentScore * testAssignmentWeight);
    }

    @Test
    void validateFieldsArePopulatedShouldReturnTrueWithAllFields(){
        AssignmentDto assignmentDto = new AssignmentDto();
        assignmentDto.setAssignmentScore(100);
        assignmentDto.setAssignmentWeight(.5);
        assignmentDto.setAssignmentName("Testing");
        assignmentDto.setClassName("Testing Class");

        Assertions.assertTrue(assignmentDto.validateAllFieldsArePopulated());
    }

    @Test
    void validateFieldsArePopulatedShouldReturnFalseWithMissingFields(){
        AssignmentDto assignmentDto = new AssignmentDto();
        assignmentDto.setAssignmentScore(100);
        assignmentDto.setAssignmentName("Testing");
        assignmentDto.setClassName("Testing Class");

        Assertions.assertFalse(assignmentDto.validateAllFieldsArePopulated());
    }
}
