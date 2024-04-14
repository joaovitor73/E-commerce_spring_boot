package com.projeto_web.projeto_web.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class RoutesController {
    @RequestMapping(value = "/signIn", method= RequestMethod.GET)
    public String signIn(HttpServletRequest request, HttpServletResponse response) throws IOException{
        return "signIn.html";
    }
    @RequestMapping(value = "/signUp", method= RequestMethod.GET)
    public String signUp(HttpServletRequest request, HttpServletResponse response){
        return "signUp.html";
    }
    @RequestMapping(value = "/logout", method= RequestMethod.GET)
    public void doLogout(HttpServletRequest request, HttpServletResponse response)  throws IOException, ServletException{
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
            response.sendRedirect("/signIn");
        }
    }
}
