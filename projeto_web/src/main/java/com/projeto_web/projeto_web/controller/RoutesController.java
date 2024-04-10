package com.projeto_web.projeto_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RoutesController {
    @RequestMapping(value = "/signIn", method= RequestMethod.GET)
    public String signIn(){
        return "signIn";
    }
    @RequestMapping(value = "/signUp", method= RequestMethod.GET)
    public String signUp(){
        return "signUp";
    }
    
}
