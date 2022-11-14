package com.example.GradeCalc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // <1>
public class DatabaseLoader implements CommandLineRunner { // <2>

    private final ClassRepository repository;

    @Autowired // <3>
    public DatabaseLoader(ClassRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception { // <4>
        Grade grade = new Grade(.50, 100, "Testing");
        Class test = new Class("Advanced Java");
        test.addGrade(grade);
        test.calculateGrade();
        this.repository.save(test);
    }
}
