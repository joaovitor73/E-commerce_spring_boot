package com.projeto_web.projeto_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projeto_web.projeto_web.model.Carrinho;
import com.projeto_web.projeto_web.persistencia.ProductDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class CarrinhoController {
    @RequestMapping(value = "/carrinho/update", method=RequestMethod.POST)
    public void doUpdateEstoque(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String command = request.getParameter("comando");
        if (command.equals("add")){
        //adicionar ao carrinho
            ProductDAO.updateEstoque(1, id);
        }else if (command.equals("remove")){
        //remover do carrinho
            ProductDAO.updateEstoque(-1, id);
        }
    }
    
}
