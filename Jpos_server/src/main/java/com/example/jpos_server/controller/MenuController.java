package com.example.jpos_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/menus")
@Controller
public class MenuController {

    @GetMapping()
    public String test(){
        return "a";
    }

}
