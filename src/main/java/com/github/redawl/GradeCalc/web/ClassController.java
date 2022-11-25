package com.github.redawl.GradeCalc.web;

import com.github.redawl.GradeCalc.Class.ClassService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/class")
@CrossOrigin(origins = "http://localhost:3000")
public class ClassController {
    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public List<com.github.redawl.GradeCalc.Class.Class> getClasses(HttpServletRequest httpServletRequest){
        return classService.getAllClasses(httpServletRequest.getRemoteUser());
    }
}
