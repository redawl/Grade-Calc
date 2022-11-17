package com.github.redawl.GradeCalc.Grade;

/**
 * Calculated grade
 */
public class Grade {
    private String className;
    private double grade;
    private String gradeType;

    public Grade(String className, double grade, String gradeType) {
        this.className = className;
        this.grade = grade;
        this.gradeType = gradeType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }
}
