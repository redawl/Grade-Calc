package com.github.redawl.GradeCalc.Assignment;

import com.sun.istack.NotNull;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Assignment that has been graded for a class
 */
@Builder
public class AssignmentDto {
    private double assignmentWeight = 0.0;
    private double assignmentScore = 0.0;

    private String assignmentName;
    private String className;

    public double getAssignmentWeight(){
        return assignmentWeight;
    }

    public double getAssignmentScore(){
        return assignmentScore;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentName(){
        return assignmentName;
    }

    public String getClassName() { return className; }
    public void setAssignmentWeight(double assignmentWeight){
        if(assignmentWeight > 1 || assignmentWeight < 0){
            throw new IllegalArgumentException("Weight must be between 0 and 1");
        }
        this.assignmentWeight = assignmentWeight;
    }

    public void setAssignmentScore(double assignmentValue){
        if(assignmentValue > 100 || assignmentValue < 0){
            throw new IllegalArgumentException("Value must be between 0 and 100");
        }
        this.assignmentScore = assignmentValue;
    }

    public void setClassName(String className){
        if(className == null || className.isEmpty()){
            throw new IllegalArgumentException("Class name cannot be null or empty");
        }
        this.className = className;
    }

    /**
     * Calculate the weighted grade
     * @return Grade
     */
    public double calculateValue() {
        return assignmentWeight * assignmentScore;
    }

    /**
     * Validate that every field in the assignment is populated
     * @return True if all are populated, false if not
     */
    public boolean validateAllFieldsArePopulated(){
        return assignmentName != null && assignmentWeight != 0 && assignmentScore != 0 && className != null;
    }
}
