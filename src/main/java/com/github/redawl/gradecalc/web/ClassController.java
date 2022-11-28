package com.github.redawl.gradecalc.web;

import com.github.redawl.gradecalc.modelclass.Class;
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
import java.util.List;

@RestController
@RequestMapping("/api/class")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Class", description = "Retrieve information about classes")
public class ClassController {
    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Retrieve a list of every class registered by the user.",
            responses = {
                    @ApiResponse(description = "Ok - Classes were found for the user", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    // Use the `array` property instead of `schema`
                                    array = @ArraySchema(schema = @Schema(implementation = Class.class))
                            )
                    }),
                    @ApiResponse(description = "Not Found - No classes were found for the current user", responseCode = "404")
            }
    )
    public List<com.github.redawl.gradecalc.modelclass.Class> getClasses(HttpServletRequest httpServletRequest){
        return classService.getAllClasses(httpServletRequest.getRemoteUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No classes were found for the current user"));
    }
}
