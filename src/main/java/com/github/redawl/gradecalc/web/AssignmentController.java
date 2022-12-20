package com.github.redawl.gradecalc.web;

import com.github.redawl.gradecalc.assignment.AssignmentDTO;
import com.github.redawl.gradecalc.exceptions.AssignmentNotFoundException;
import com.github.redawl.gradecalc.modelclass.ClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/assignment")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Assignment", description = "Manage Assignments for your classes")
public class AssignmentController {

    private final ClassService classService;

    public AssignmentController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create an assignment",
            description = "Create an assignment for a class. ",
            responses = {
                    @ApiResponse(description = "Created - Assignment was successfully created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request - Assignment was not successfully created.", responseCode = "400")
            }
    )
    public void addAssignmentToClass(@RequestBody AssignmentDTO newAssignmentDTO, HttpServletRequest httpServletRequest){
        if(newAssignmentDTO == null || !newAssignmentDTO.validateAllFieldsArePopulated()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All required fields must be populated");
        }

        try {
            classService.addAssignment(newAssignmentDTO, httpServletRequest.getRemoteUser());
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Retrieve list of assignments for a class",
            responses = {
                    @ApiResponse(description = "Ok - Assignments were found for the given class", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    // Use the `array` property instead of `schema`
                                    array = @ArraySchema(schema = @Schema(implementation = AssignmentDTO.class))
                            )
                    }),
                    @ApiResponse(description = "Not Found - No assignments were found for the given class", responseCode = "404"),
            }
    )
    public Iterable<AssignmentDTO> getAssignmentsByClass(@RequestParam(value = "className") String className,
                                                         HttpServletRequest httpServletRequest){
        try {
            return classService.getAssignmentsByClass(className, httpServletRequest.getRemoteUser())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "No assignments found for class " + className));
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Remove an assignment",
            description = "Remove an assignment for a class.",
            responses = {
                    @ApiResponse(description = "No Content - Assignment was successfully deleted", responseCode = "204"),
                    @ApiResponse(description = "Bad Request - Assignment was not successfully deleted.", responseCode = "400"),
                    @ApiResponse(description = "Not Found - Assignment does not exist.", responseCode = "404")
            }
    )
    public void removeAssignment(@RequestParam String assignmentName,
                                 @RequestParam String className,
                                 HttpServletRequest httpServletRequest){
        try{
            classService.removeAssignment(assignmentName, className, httpServletRequest.getRemoteUser());
        } catch (AssignmentNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
