package com.magister.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private DataSource datasource;

    @RequestMapping("/ds")
    @ResponseBody
    public String datasource() {
        return datasource.toString();
    }
}
