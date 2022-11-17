package com.github.redawl.GradeCalc.Grade;

public class GradeFactory {
    public enum GradeType {
        MAX,
        CURRENT,
        REQUIRED
    }

    private static final String [] types = new String[] {
        "MAX",
        "CURRENT",
        "REQUIRED"
    };

    public static Grade buildGrade(String className, double grade, GradeType gradeType){
        return new Grade(className, grade, types[gradeType.ordinal()]);
    }
}
