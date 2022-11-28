package com.github.redawl.gradecalc.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AssignmentTests {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void calculateGradeShouldReturnCorrectValue(){
        double testAssignmentScore = 80;
        double testAssignmentWeight = .5;
        AssignmentDTO assignmentDto = AssignmentDTO.builder()
                .assignmentScore(testAssignmentScore)
                .assignmentWeight(testAssignmentWeight)
                        .build();
        Assertions.assertEquals(assignmentDto.calculateValue(), testAssignmentScore * testAssignmentWeight);
    }

    @Test
    void validateFieldsArePopulatedShouldReturnTrueWithAllFields(){
        AssignmentDTO assignmentDto = AssignmentDTO.builder()
                .assignmentScore(100)
                .assignmentWeight(.5)
                .assignmentName("Testing")
                .className("Testing Class")
                .build();

        Assertions.assertTrue(assignmentDto.validateAllFieldsArePopulated(),
                "validateAllFieldsArePopulated should return true if all fields are populated");
    }

    @Test
    void validateFieldsArePopulatedShouldReturnFalseWithMissingFields(){
        AssignmentDTO assignmentDto = AssignmentDTO.builder()
                .assignmentScore(100)
                .assignmentName("Testing")
                .className("Testing Class")
                .build();

        Assertions.assertFalse(assignmentDto.validateAllFieldsArePopulated(),
                "validateAllFieldsArePopulated should return false if not all fields are populated");
    }
}
