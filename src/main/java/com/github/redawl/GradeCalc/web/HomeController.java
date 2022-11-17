package com.github.redawl.GradeCalc.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

}
