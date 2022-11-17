package com.github.redawl.GradeCalc.Assignment;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Assignment that has been graded for a class
 */
@Entity
public class Assignment {
    private double AssignmentWeight;
    private double assignmentScore;
    @Id
    private String assignmentName;
    private String className;

    public double getAssignmentWeight(){
        return AssignmentWeight;
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
        this.AssignmentWeight = assignmentWeight;
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
        return AssignmentWeight * assignmentScore;
    }
}
