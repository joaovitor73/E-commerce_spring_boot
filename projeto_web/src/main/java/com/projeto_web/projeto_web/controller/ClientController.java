package com.projeto_web.projeto_web.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.projeto_web.projeto_web.model.Client;
import com.projeto_web.projeto_web.persistencia.ClientDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ClientController {
    
    @RequestMapping(value = "/cliets/validate", method = RequestMethod.POST)
    public void doValidateClient(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        ClientDAO uDAO = new ClientDAO();
        var write = response.getWriter();
        if(uDAO.getClient(email, senha) != null){//user exist
            write.println("Acesso permitido");
        }else{
            write.println("Acesso negado");
            write.println(senha);
            write.println(email);
        }
    }

    @RequestMapping(value = "/cliets/create", method = RequestMethod.POST)
    public void doCreateClient(HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException{
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        ClientDAO uDAO = new ClientDAO();
        var write = response.getWriter();
        if(uDAO.getClientEmail(email) == null){
            Client user = new Client(nome, email, senha);
            uDAO.insertClient(user);
            write.println("Usuario criado");
        }else{
            write.println("Usuario existente");
        }
      //  response.sendRedirect("index.html");
    }

    @RequestMapping(value = "/cliets/delete", method = RequestMethod.DELETE)
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
