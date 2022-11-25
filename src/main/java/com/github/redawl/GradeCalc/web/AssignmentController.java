package com.github.redawl.GradeCalc.web;

import com.github.redawl.GradeCalc.Assignment.AssignmentDto;
import com.github.redawl.GradeCalc.Class.ClassService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

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
    public void addAssignmentToClass(@RequestBody AssignmentDto newAssignmentDto, HttpServletRequest httpServletRequest){
        if(newAssignmentDto == null || !newAssignmentDto.validateAllFieldsArePopulated()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All required fields must be populated");
        }

        try {
            classService.addAssignment(newAssignmentDto, httpServletRequest.getRemoteUser());
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping
    public Iterable<AssignmentDto> getAssignmentsByClass(@RequestParam(value = "className") String className,
                                                         HttpServletRequest httpServletRequest){
        try {
            return classService.getAssignmentsByClass(className, httpServletRequest.getRemoteUser());
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAssignment(@RequestParam(value = "assignmentName") String assignmentName,
                                 @RequestParam(value = "className") String className,
                                 HttpServletRequest httpServletRequest){
        boolean result;
        try{
            result = classService.removeAssignment(assignmentName, className, httpServletRequest.getRemoteUser());
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        if(result){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
