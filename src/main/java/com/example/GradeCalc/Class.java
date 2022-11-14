package com.example.GradeCalc;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that a user wants to calculate a grade for
 */
@Entity
public class Class {
    private final HashMap<String, Grade> grades;
    private double totalGrade = 0.0;
    private final String className;

    private @Id @GeneratedValue Long id;

    Class(){
        grades = new HashMap<>();
        className = null;
    }

    Class(String name){
        if(name == null || "".equals(name)){
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        grades = new HashMap<>();
        className = name;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.className.equals(((Class) o).className);
    }

    /**
     * Retrieve grade by the assignment title
     * @param gradeName Title of the assignment
     * @return Grade that has the passed in assignment title
     */
    @Bean
    public Grade getGradeByName(String gradeName) {
        if(gradeName == null || "".equals(gradeName)){
            throw new IllegalArgumentException("gradeName cannot be null or empty");
        }
        return grades.get(gradeName);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("Class:{ Grades:[");
        for(Grade grade: grades.values()){
            builder.append(grade.toString());
        }
        builder.append("]}");

        return builder.toString();
    }

    /**
     * Add a grade for the current class
     * @param newGrade Grade to add
     */
    public void addGrade(Grade newGrade) {
        if(newGrade == null){
            throw new IllegalArgumentException("newGrade cannot be null");
        }
        String gradeName = newGrade.getAssignmentName();

        grades.put(gradeName, newGrade);
    }

    /**
     * Remove a grade from the class, by its assignment title
     * @param gradeName Title of grade to remove
     */
    public void removeGradeByName(String gradeName) {
        if(gradeName == null | "".equals(gradeName)){
            throw new IllegalArgumentException("gradeName cannot be null or empty");
        }
        grades.remove(gradeName);
    }

    /**
     * Calculate the total grade for the class
     * @return Total grade as with double precision
     */
    public double calculateGrade(){
        double totalGrade = 0;
        for(Grade grade: grades.values()){
            totalGrade += grade.calculateValue();
        }
        this.totalGrade = totalGrade;
        return totalGrade;
    }

    public double getTotalGrade(){
        return totalGrade;
    }

    public List<Grade> getGrades() {
        return new ArrayList<>(grades.values());
    }

    public String getClassName(){
        return className;
    }
}
