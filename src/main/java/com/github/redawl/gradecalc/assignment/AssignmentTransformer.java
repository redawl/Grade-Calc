package com.github.redawl.gradecalc.assignment;

/**
 * Convert AssignmentDto to Assignment and vice versa
 */
public class AssignmentTransformer {
    public static Assignment DTOToAssignment(AssignmentDTO assignmentDto, String username){
        Assignment assignment = new Assignment();
        assignment.setUsername(username);
        assignment.setClassName(assignmentDto.getClassName());
        assignment.setAssignmentName(assignmentDto.getAssignmentName());
        assignment.setAssignmentScore(assignmentDto.getAssignmentScore());
        assignment.setAssignmentWeight(assignmentDto.getAssignmentWeight());
        assignment.setAssignmentScore(assignmentDto.getAssignmentScore());

        return assignment;
    }

    public static AssignmentDTO assignmentToDTO(Assignment assignment){
        return AssignmentDTO.builder()
                .assignmentName(assignment.getAssignmentName())
                .className(assignment.getClassName())
                .assignmentWeight(assignment.getAssignmentWeight())
                .assignmentScore(assignment.getAssignmentScore())
                .build();
    }
}
