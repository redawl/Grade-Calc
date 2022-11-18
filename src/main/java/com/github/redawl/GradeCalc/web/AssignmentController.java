package com.github.redawl.GradeCalc.web;

import com.github.redawl.GradeCalc.Assignment.Assignment;
import com.github.redawl.GradeCalc.Class.ClassService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/assignment")
@CrossOrigin(origins = "http://localhost:3000")
public class AssignmentController {

    private final ClassService classService;

    public AssignmentController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAssignmentToClass(@RequestBody Assignment newAssignment){
        try {
            classService.addAssignment(newAssignment);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping
    public Iterable<Assignment> getAssignmentsByClass(@RequestParam(value = "className") String className){
        try {
            return classService.getAssignmentsByClass(className);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAssignment(@RequestParam(value = "assignmentName") String assignmentName){
        try{
            classService.removeAssignment(assignmentName);
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
