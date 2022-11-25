package com.github.redawl.GradeCalc.web;

import com.github.redawl.GradeCalc.Class.ClassService;
import com.github.redawl.GradeCalc.Grade.GradeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/grade")
@CrossOrigin(origins = "http://localhost:3000")
public class GradeController {
    private final ClassService classService;

    public GradeController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public GradeResponse computeGrade(@RequestParam(value = "className") String className, HttpServletRequest httpServletRequest){
        double grade = 0;
        try {
            grade = classService.computeGradeForClass(className, httpServletRequest.getRemoteUser());
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return GradeResponse.builder().className(className).grade(grade)
                .gradeType(GradeResponse.gradeType(GradeResponse.GradeType.CURRENT))
                .build();
    }

    @GetMapping(path="/max")
    public GradeResponse computeMaxGrade(@RequestParam(value = "className") String className, HttpServletRequest httpServletRequest){
        double grade = 0;
        try {
            grade =  classService.computeMaxGradeForClass(className, httpServletRequest.getRemoteUser());
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return GradeResponse.builder()
                .className(className)
                .grade(grade)
                .gradeType(GradeResponse.gradeType(GradeResponse.GradeType.MAX))
                .build();
    }

    @GetMapping(path="/required")
    public GradeResponse computeRequiredGrade(@RequestParam(value = "className") String className,
                                         @RequestParam(value = "targetGrade") double  targetGrade,
                                         HttpServletRequest httpServletRequest){
        double grade = 0;
        try {
            grade = classService.computeGradeNeededToGetGrade(className, targetGrade, httpServletRequest.getRemoteUser());
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return GradeResponse.builder()
                .className(className)
                .grade(grade)
                .gradeType(GradeResponse.gradeType(GradeResponse.GradeType.REQUIRED))
                .build();
    }
}
