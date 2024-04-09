package com.projeto_web.projeto_web.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projeto_web.projeto_web.persistencia.LogisticDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LogisticController {
    
    @RequestMapping(value = "/logistics/validate", method = RequestMethod.POST)
    public void doValidateClient(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        LogisticDAO uDAO = new LogisticDAO();
        var write = response.getWriter();
        if(uDAO.getLogistic(email, senha) != null){//user exist
            write.println("Acesso permitido");
        }else{
            write.println("Acesso negado");
            write.println(senha);
            write.println(email);
        }
    }
}
