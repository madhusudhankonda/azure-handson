package com.chocolateminds.azurehandson.breakingnews;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BreakingNewsController {

    @GetMapping("/breaking")
    public String getBreakingNews(){
        return "<h1>Breaking News </h1>";
    }
}
