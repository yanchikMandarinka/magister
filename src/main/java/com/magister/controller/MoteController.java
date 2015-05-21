package com.magister.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mote")
public class MoteController {

    @RequestMapping("/show")
    public String chart(long id) {
        return "mote/chart";
    }
}
