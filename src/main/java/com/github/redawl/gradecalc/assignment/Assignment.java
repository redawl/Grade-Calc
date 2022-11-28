package com.github.redawl.gradecalc.assignment;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Assignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String assignmentName;

    @NotNull
    private String className;

    @NotNull
    private double assignmentWeight;

    @NotNull
    private double assignmentScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getAssignmentWeight() {
        return assignmentWeight;
    }

    public void setAssignmentWeight(double assignmentWeight) {
        this.assignmentWeight = assignmentWeight;
    }

    public double getAssignmentScore() {
        return assignmentScore;
    }

    public void setAssignmentScore(double assignmentScore) {
        this.assignmentScore = assignmentScore;
    }

    /**
     * Calculate the weighted grade
     * @return Grade
     */
    public double calculateValue() {
        return assignmentWeight * assignmentScore;
    }
}
