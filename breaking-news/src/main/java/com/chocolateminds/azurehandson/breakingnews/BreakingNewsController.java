package com.chocolateminds.azurehandson.breakingnews;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BreakingNewsController {

    @GetMapping("/")
    public String getDefaultNews(){
        return "<h1>Default News </h1>";
    }

    @GetMapping("/breaking")
    public String getBreakingNews(){
        return "<h1>Breaking News </h1>";
    }
}
