package de.wakeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping({"", "/"})t
    public String test() {
        return "index";
    }
}
