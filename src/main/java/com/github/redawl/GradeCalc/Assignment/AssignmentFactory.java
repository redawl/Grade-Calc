package com.github.redawl.GradeCalc.Assignment;

/**
 * Convert AssignmentDto to Assignment and vice versa
 */
public class AssignmentFactory {
    public static Assignment dtoToAssignment(AssignmentDto assignmentDto, String username){
        Assignment assignment = new Assignment();
        assignment.setUsername(username);
        assignment.setClassName(assignmentDto.getClassName());
        assignment.setAssignmentName(assignmentDto.getAssignmentName());
        assignment.setAssignmentScore(assignmentDto.getAssignmentScore());
        assignment.setAssignmentWeight(assignmentDto.getAssignmentWeight());
        assignment.setAssignmentScore(assignmentDto.getAssignmentScore());

        return assignment;
    }

    public static AssignmentDto assignmentToDto(Assignment assignment){
        AssignmentDto assignmentDto = AssignmentDto.builder()
                .assignmentName(assignment.getAssignmentName())
                .className(assignment.getClassName())
                .assignmentWeight(assignment.getAssignmentWeight())
                .assignmentScore(assignment.getAssignmentScore())
                .build();

        return assignmentDto;
    }
}
