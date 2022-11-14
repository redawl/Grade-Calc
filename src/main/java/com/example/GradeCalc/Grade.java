package com.example.GradeCalc;

import java.io.Serializable;

/**
 * Grade that the user got on an assignment
 */
public class Grade implements Serializable {
    private double weight;
    private double value;
    private String assignmentName;

    Grade(){
        weight = 0;
        value = 0;
        assignmentName = null;
    }

    Grade(double weight, double value, String assignmentName){
        this.weight = weight;
        this.value = value;
        this.assignmentName = assignmentName;
    }

    public double getWeight(){
        return weight;
    }

    public double getValue(){
        return value;
    }

    public String getAssignmentName(){
        return assignmentName;
    }

    public void setWeight(double weight){
        // Verify weight value
        if(weight > 1 || weight < 0){
            throw new IllegalArgumentException("Weight must be between 0 and 1");
        }
        this.weight = weight;
    }

    public void setValue(double value){
        // Verify value
        if(value > 100 || value < 0){
            throw new IllegalArgumentException("Value must be between 0 and 100");
        }
        this.value = value;
    }

    public void setAssignmentName(String assignmentName){
        // Verify Assignment Name
        if(assignmentName == null || "".equals(assignmentName)){
            throw new IllegalArgumentException("assignmentName cannot be null or empty");
        }

        this.assignmentName = assignmentName;
    }

    /**
     * Calculate the weighted grade
     * @return Grade
     */
    public double calculateValue() {
        double grade = weight * value;

        // Verify this is a legal grade
        if(grade < 0 || grade > 100){
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }

        return grade;
    }

    @Override
    public String toString(){
        return "Grade: " + calculateValue();
    }
}
