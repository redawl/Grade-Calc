package com.github.redawl.GradeCalc.web;

import com.github.redawl.GradeCalc.Assignment.Assignment;
import com.github.redawl.GradeCalc.Class.ClassService;
import com.github.redawl.GradeCalc.Grade.Grade;
import com.github.redawl.GradeCalc.Grade.GradeFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/grade")
@CrossOrigin(origins = "http://localhost:3000")
public class GradeController {
    private final ClassService classService;

    public GradeController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public Grade computeGrade(@RequestParam(value = "className") String className){
        double grade = 0;
        try {
            grade = classService.computeGradeForClass(className);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return GradeFactory.buildGrade(className, grade, GradeFactory.GradeType.CURRENT);
    }

    @GetMapping(path="/max")
    public Grade computeMaxGrade(@RequestParam(value = "className") String className){
        double grade = 0;
        try {
            grade =  classService.computeMaxGradeForClass(className);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return GradeFactory.buildGrade(className, grade, GradeFactory.GradeType.MAX);
    }

    @GetMapping(path="/required")
    public Grade computeMaxGrade(@RequestParam(value = "className") String className,
                                  @RequestParam(value = "targetGrade") double  targetGrade){
        double grade = 0;
        try {
            grade = classService.computeGradeNeededToGetGrade(className, targetGrade);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return GradeFactory.buildGrade(className, grade, GradeFactory.GradeType.REQUIRED);
    }
}
