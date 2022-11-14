package com.example.GradeCalc;

import org.hibernate.cache.spi.access.CachedDomainDataAccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClassTests {
    // Smoke Test
    @Test
    void shouldCalculateCorrectGradeOneHundred(){
        Grade grade1 = new Grade(.25, 100, "Assignment1");
        Grade grade2 = new Grade(.75, 100, "Assignment2");

        Class advancedJava = new Class("Advanced Java");
        advancedJava.addGrade(grade1);
        advancedJava.addGrade(grade2);

        Assertions.assertEquals(100.0, advancedJava.calculateGrade());
    }

    @Test
    void shouldCalculateCorrectGradeNotOneHundred(){
        Grade grade1 = new Grade(.10, 80, "Assignment1");
        Grade grade2 = new Grade(.70, 100, "Assignment2");

        Class advancedJava = new Class("Advanced Java");
        advancedJava.addGrade(grade1);
        advancedJava.addGrade(grade2);

        Assertions.assertEquals(78.0, advancedJava.calculateGrade());
    }
}
