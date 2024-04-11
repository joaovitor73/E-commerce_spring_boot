package com.projeto_web.projeto_web.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projeto_web.projeto_web.model.Carrinho;
import com.projeto_web.projeto_web.model.Product;
import com.projeto_web.projeto_web.persistencia.ProductDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class CarrinhoController {
    @RequestMapping(value = "/carrinho", method=RequestMethod.GET)
    public void doVerCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var write = response.getWriter();
        write.println("<html> <head> <title> Loja </title> </head> <body>");
        write.println("<h1> Lista Carrinho</h1>");
        write.println("<table border='1'><thead><tr><th>Nome</th> <th>Descrição</th> <th>Quantidade</th><th>Preço</th> <th>Remover</th></tr></thead>");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (email.replace('@','|').equals(cookie.getName())) {
                Carrinho carrinho = new Carrinho();
                for (Entry<Integer, Integer> entry : carrinho.cookieToArray(cookie.getValue()).entrySet()) {
                    Integer chave = entry.getKey();
                    int valor = entry.getValue();
                    Product product = ProductDAO.getProductId(chave);
                    write.println("<tr><td> " + product.getNome() + "</td><td> " + product.getDescricao()+  "</td><td> " + valor +  "</td>");
                }
            }
        }
        write.println("</table>");
        write.println("<br><a href='/'>Ver Produtos</a>");
    }

    @RequestMapping(value = "/carrinho/update", method=RequestMethod.GET)
    public void doUpdateEstoque(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String command = request.getParameter("comando");
        var write = response.getWriter();
        if (command.equals("add")){
        //adicionar ao carrinho`
        //cookie
            ProductDAO.updateEstoque(-1, id);
            Product product = ProductDAO.getProductId(id);
            String nome = product.getNome().replace(" ", "-");
           
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");
            int preco = product.getPreco();
            
            Cookie[] cookies = request.getCookies();
            //criar cookie
            boolean flag = false;
            for (Cookie cookie : cookies) {//Verifica se cookie existe
                if (email.replace('@','|').equals(cookie.getName())) {
                    write.println(cookie.getValue());
                    flag = true;
                    String data;
                    if(cookie.getValue().equals("")){
                        data = String.valueOf(id);
                    }else{
                        data =  cookie.getValue()+"|"+id;
                    }
                   
                    Cookie c = new Cookie(email.replace('@','|'), data);
                    c.setMaxAge(3600-cookie.getMaxAge());
                    response.addCookie(c);
                    response.sendRedirect("/carrinho");
                }
            }
            if(!flag){
                Cookie c = new Cookie(email.replace('@','|'),"");
                c.setMaxAge(3600);
                response.addCookie(c);
                response.sendRedirect("/carrinho/update?id=" + id + "&comando=add");
            }
            
        }else if (command.equals("remove")){
        //remover do carrinho
            ProductDAO.updateEstoque(1, id);
        }
        
    }
}
