package com.projeto_web.projeto_web.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.projeto_web.projeto_web.model.Client;
import com.projeto_web.projeto_web.persistencia.ClientDAO;
import com.projeto_web.projeto_web.persistencia.LogisticDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    
    @RequestMapping(value = "/user/validate", method = RequestMethod.POST)
    public void doValidate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        ClientDAO uDAO = new ClientDAO();
        LogisticDAO lDAO = new LogisticDAO();
        var write = response.getWriter();
        if(uDAO.getClient(email, password) != null){//user exist
            HttpSession session = request.getSession();
            session.setAttribute("email", email);
            session.setAttribute("rule", "cliente");
            response.sendRedirect("/");
        }else if(lDAO.getLogistic(email, password) != null){//logistic exist
            HttpSession session = request.getSession();
            session.setAttribute("email", email);
            session.setAttribute("rule", "lojista");
            response.sendRedirect("/");
        }else{
            write.println("Login invalido");
        }
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public void doCreateClient(HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException{
        String nome = request.getParameter("name");
        String email = request.getParameter("email");
        String senha = request.getParameter("password");
        ClientDAO uDAO = new ClientDAO();
        var write = response.getWriter();
        if(uDAO.getClientEmail(email) == null){
            Client user = new Client(nome, email, senha);
            uDAO.insertClient(user);
            write.println("Usuario criado");
        }else{
            write.println("<html><head><title>Erro</title></head><body><p>Esse email já existe</p></body></html>");
        }
    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)
    public void doDeleteClient(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
            String email = request.getParameter("email");
            ClientDAO uDAO =  new ClientDAO();
            var write = response.getWriter();
            if(uDAO.getClientEmail(email) != null){
                uDAO.deleteClient(email);
                write.println("Usuario deletado");
            }else{
                write.println("Usuario não encontrado");
            }
    }
}
