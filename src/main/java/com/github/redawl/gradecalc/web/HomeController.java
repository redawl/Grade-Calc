package com.github.redawl.gradecalc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/class")
    public String getClassPath(){
        return "index";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

}
