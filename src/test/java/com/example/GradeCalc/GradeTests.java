package com.example.GradeCalc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GradeTests {
    @Test
    void gradeShouldBeBetweenZeroAndOne(){
        Grade testGrade = new Grade(0,100,"Test Grade");

        Assertions.assertThat(testGrade.calculateValue()).isBetween(0.0, 1.0);
    }

}
