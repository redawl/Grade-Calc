package com.github.redawl.gradecalc.grade;

import lombok.Builder;

import java.util.List;

/**
 * Calculated grade
 */
@Builder
public class GradeResponse {
    public enum GradeType {
        MAX,
        CURRENT,
        REQUIRED
    }

    private static final List<String> types = List.of(
            "MAX",
            "CURRENT",
            "REQUIRED"
    );

    private String className;
    private double grade;
    private String gradeType;

    public GradeResponse(String className, double grade, String gradeType) {
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

    public static String gradeType(GradeType gradeType){
        return types.get(gradeType.ordinal());
    }
}
