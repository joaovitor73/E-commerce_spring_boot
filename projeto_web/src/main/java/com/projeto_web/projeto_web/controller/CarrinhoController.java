package com.projeto_web.projeto_web.controller;

import java.io.IOException;
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
        boolean flag = false;
        write.println("<html> <head> <title> Loja </title> </head> <body>");
        write.println("<h1> Lista Carrinho</h1>");
        write.println("<table border='1'><thead><tr><th>Nome</th> <th>Descrição</th> <th>Quantidade</th><th>Preço</th> <th>Remover</th></tr></thead>");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (email.replace('@','|').equals(cookie.getName())) {
                Carrinho carrinho = new Carrinho();
                flag = true;
                for (Entry<Integer, Integer> entry : carrinho.cookieToArray(cookie.getValue()).entrySet()) {
                    Integer id = entry.getKey();
                    int quantidade = entry.getValue();
                    Product product = ProductDAO.getProductId(id);
                    write.println("<tr><td> " + product.getNome() + "</td><td> " + product.getDescricao()+  "</td><td> " + quantidade +  "</td><td> " + product.getPreco()+ "</td><td> "+ "<a href='/carrinho/update?id=" +id + "&comando=remove'" + ">Remover</a>" + "</td>");
                }
                
              //Cookie c = new Cookie(email.replace('@','|'), carrinho.getCookies());
              // c.setMaxAge(3600-cookie.getMaxAge());
                //response.addCookie(c);
                //response.sendRedirect("/carrinho");
            }
        }
        write.println("</table>");
        if(!flag){
            write.println("<h2>Seu carrinho está vazio</h2>");
        }
        if(flag){
            write.println("<br><a href='/carrinho/checkout'>Finalizar Compra</a><br>");
        }
        write.println("<br><a href='/'>Ver Produtos</a>");
        write.println("<br><br><a href='/logout'>Sair</a>");
        write.println("</body></html>");
    }

    @RequestMapping(value = "/carrinho/checkout", method=RequestMethod.GET)
    public void doCarrinhoCheckOut(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (email.replace('@','|').equals(cookie.getName())) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                response.sendRedirect("/");
            }
        }
    }
    



    @RequestMapping(value = "/carrinho/update", method=RequestMethod.GET)
    public void doUpdateEstoque(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, InterruptedException {
        int id = Integer.parseInt(request.getParameter("id"));
        String command = request.getParameter("comando");
        var write = response.getWriter();
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Cookie[] cookies = request.getCookies();
        if (command.equals("add")){
        //adicionar ao carrinho`
        //cookie
            ProductDAO.updateEstoque(-1, id);
            //criar cookie
            boolean flag = false;
            for (Cookie cookie : cookies) {//Verifica se cookie existe
                if (email.replace('@','|').equals(cookie.getName())) {
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
                response.sendRedirect("/carrinho");
            }
            
        }else if (command.equals("remove")){
            String cookString;
            for (Cookie cookie : cookies) {//Verifica se cookie existe
                if (email.replace('@','|').equals(cookie.getName())) {
                    Carrinho carrinho = new Carrinho();
                    carrinho.cookieRemove(cookie.getValue(), id);
                    cookString = cookie.getValue();
                    write.println(carrinho.cookieRemove(cookie.getValue(), id));
                    cookie.setMaxAge(0);
                   // response.sendRedirect("/carrinho);
                    Cookie c = new Cookie(email.replace('@','|'),cookString);
                    c.setMaxAge(3600);
                    response.addCookie(c);
                    //response.sendRedirect("/carrinho");
                }
            }
            ProductDAO.updateEstoque(1, id);
        }
        
    }
}
