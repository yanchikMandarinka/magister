package com.magister.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    @RequestMapping("/")
    public String welcome() {
        // returns index.jsp page from WEB-INF/jsp
        return "index";
    }
}
