package com.projeto_web.projeto_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/carrinho/finalizar_compra", method=RequestMethod.GET)
    public void doComprar(HttpServletRequest request, HttpServletResponse response){
       /// @RequestMapping(value = "/carrinho", method=RequestMethod.GET)
       // public void doCarrinho (HttpServletRequest request, HttpServletResponse response) throws IOException {
           // Cookie cookie = new Cookie("fellipe.patrick|email.com", "Camisa=5|Short=10|Terno=1");
           // cookie.setMaxAge(3600);
           // response.addCookie(cookie);
        }
     
       /*  @RequestMapping(value = "/MeusProdutos", method=RequestMethod.GET)
        public void doMeusProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {
           Cookie[] cookies = request.getCookies();
            var write = response.getWriter(); 
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("fellipe.patrick|email.com".equals(cookie.getName())) {
                        String str = cookie.getValue();        
                        String[] pares = str.split("\\|");
                        String[][] matriz = new String[pares.length][2];
                        for (int i = 0; i < pares.length; i++) {
                            String[] par = pares[i].split("=");
                            matriz[i][0] = par[0];
                            matriz[i][1] = par[1];
                        }
                        for (String[] par : matriz) {
                            //adiciona na lista de produtos
                            //write.println(("Nome: " + par[0] + " Quantidade: " + par[1]));
                        }
                    }
                }
            }
    
            write.println("<html> <head> <title> Carrinho </title> </head> <body> <table border='1'>" +
            "<thead><tr><th>Nome</th> <th>Descrição</th> <th>Quantidade</th> <th>Remover</th></tr></thead>"
            );
            
            for (int i = 1; i < 7; i++) {
                write.println("<tr><td>Produto " + i + "</td><td>Descrição do Produto " + i + "</td><td>Quantidade do Produto " + i + "</td><td>");
                write.println("<a href='/carrinho?id=" + i + "&comando=remover'" + ">Remover</a>");
                write.println("</td></tr>");
            }
            
        }
        
        
    }*/
}
