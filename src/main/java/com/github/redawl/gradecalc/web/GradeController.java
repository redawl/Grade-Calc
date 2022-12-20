package com.github.redawl.gradecalc.web;

import com.github.redawl.gradecalc.modelclass.ClassService;
import com.github.redawl.gradecalc.grade.GradeResponse;
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
@RequestMapping("/api/grade")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Grade", description = "Compute different type of grades")
public class GradeController {
    private final ClassService classService;

    public GradeController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Compute current grade in a class. Classname cannot be null or empty",
            responses = {
                    @ApiResponse(description = "Ok - Grade was computed successfully", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    // Use the `array` property instead of `schema`
                                    array = @ArraySchema(schema = @Schema(implementation = GradeResponse.class))
                            )
                    }),
                    @ApiResponse(description = "Bad Request - Invalid classname was passed", responseCode = "400")
            }
    )
    public GradeResponse computeGrade(@RequestParam(value = "className") String className, HttpServletRequest httpServletRequest){
        double grade;
        // TODO: Fix so throws exceptions for not found class, and Bad class names
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
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Compute the maximum possible grade for a class.",
            responses = {
                    @ApiResponse(description = "Ok - Maximum grade was computed successfully", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    // Use the `array` property instead of `schema`
                                    array = @ArraySchema(schema = @Schema(implementation = GradeResponse.class))
                            )
                    }),
                    @ApiResponse(description = "Bad Request - Invalid class name was passed", responseCode = "400")
            }
    )
    public GradeResponse computeMaxGrade(@RequestParam(value = "className") String className, HttpServletRequest httpServletRequest){
        double grade;
        // TODO: Same as above, fix responses
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
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Compute the grade required for the rest of the assignments in order to achieve a target grade.",
            responses = {
                    @ApiResponse(description = "Ok - Required grade was computed successfully.", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    // Use the `array` property instead of `schema`
                                    array = @ArraySchema(schema = @Schema(implementation = GradeResponse.class))
                            )
                    }),
                    @ApiResponse(description = "Bad Request - Invalid classname was passed", responseCode = "400")
            }
    )
    public GradeResponse computeRequiredGrade(@RequestParam(value = "className") String className,
                                         @RequestParam(value = "targetGrade") double  targetGrade,
                                         HttpServletRequest httpServletRequest){
        double grade;
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
