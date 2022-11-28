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
        AssignmentDto assignmentDto = AssignmentDto.builder()
                .assignmentScore(testAssignmentScore)
                .assignmentWeight(testAssignmentWeight)
                        .build();
        Assertions.assertEquals(assignmentDto.calculateValue(), testAssignmentScore * testAssignmentWeight);
    }

    @Test
    void validateFieldsArePopulatedShouldReturnTrueWithAllFields(){
        AssignmentDto assignmentDto = AssignmentDto.builder()
                .assignmentScore(100)
                .assignmentWeight(.5)
                .assignmentName("Testing")
                .className("Testing Class")
                .build();

        Assertions.assertTrue(assignmentDto.validateAllFieldsArePopulated());
    }

    @Test
    void validateFieldsArePopulatedShouldReturnFalseWithMissingFields(){
        AssignmentDto assignmentDto = AssignmentDto.builder()
                .assignmentScore(100)
                .assignmentName("Testing")
                .className("Testing Class")
                .build();

        Assertions.assertFalse(assignmentDto.validateAllFieldsArePopulated());
    }
}
