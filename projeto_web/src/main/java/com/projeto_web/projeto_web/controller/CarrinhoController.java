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
    public void doVerCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null){
            if(session.getAttribute("rule") == "cliente"){
                var write = response.getWriter();
                write.println("<html> <head> <title> Loja </title> </head> <body>");
                String email = (String) session.getAttribute("email");
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if (email.replace('@','-').equals(cookie.getName())) {
                            Carrinho carrinho = new Carrinho();
                            String data = cookie.getValue();
                            if(data != "" || data == null){
                                write.println("<h1> Lista Carrinho</h1>");
                                write.println("<table border='1'><thead><tr><th>Nome</th> <th>Descrição</th> <th>Quantidade</th><th>Preço</th> <th>Remover</th> <th>Adicionar</th></tr></thead>");
                                Product product;
                                for (Entry<Integer, Integer> entry : carrinho.cookieToArray(data).entrySet()) {
                                    Integer id = entry.getKey();
                                    int quantidade = entry.getValue();
                                    product = ProductDAO.getProductId(id);
                                    write.println("<tr><td> " + product.getNome() + "</td><td> " + product.getDescricao()+  "</td><td> " + quantidade +  "</td><td> " + product.getPreco()+ "</td>");
                                    write.println("<td> "+ "<a href='/carrinho/update?id=" +id + "&comando=remove'" + ">Remover</a>" + "</td>");
                                    if(quantidade < product.getEstoque()){
                                        write.println("<td> "+  "<a href='/carrinho/update?id=" + id +"&comando=add'" + ">Adicionar</a>" +"</td>");
                                    }else{
                                        write.println("<td>Estoque vazio</td>");
                                    }
                                }
                                
                                write.println("</table>");
                                write.println("<br><a href='/carrinho/checkout'>Finalizar Compra</a><br>");

                            }else{
                                write.println("<h2>Seu carrinho está vazio</h2>");
                            }
                        }
                    }
                write.println("<br><a href='/'>Ver Produtos</a>");
                write.println("<br><br><a href='/logout'>Sair</a>");
                write.println("</body></html>");
            }else{
                response.sendRedirect("/");
            }
        }
    }

    @RequestMapping(value = "/carrinho/checkout", method=RequestMethod.GET)
    public void doCarrinhoCheckOut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        HttpSession session = request.getSession(false);
        if (session != null) {
            if(session.getAttribute("rule")=="cliente"){
                String email = (String) session.getAttribute("email");
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if (email.replace('@','-').equals(cookie.getName())) {
                        Carrinho carrinho = new Carrinho();
                        int total = 0;
                        for (Entry<Integer, Integer> entry : carrinho.cookieToArray(cookie.getValue()).entrySet()) {
                            Integer id = entry.getKey();
                            int quantidade = entry.getValue();
                            total += (ProductDAO.getProductId(id).getPreco()*quantidade);
                            ProductDAO.updateEstoque(-quantidade, id); 
                        }
                        cookie.setValue("");
                        response.addCookie(cookie);
                        var write = response.getWriter();
                        write.println("<html> <head> <title> Loja </title> </head> <body>");
                        write.println("<h3> valor a ser pago: " + total + " </h3>");
                        write.println("<a href='/'> Ver produtos </a>");
                    }
                }
            }else{
                response.sendRedirect("/");
            }
        }
    }

    @RequestMapping(value="/carrinho/total", method = RequestMethod.GET)
    public void doTotal(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession(false);
        if (session != null) {
            if(session.getAttribute("rule") == "cliente"){
                response.getWriter().println("<h2>O valor a ser pago é: "+ request.getAttribute("total") + "</h2>");
            }else{
                response.sendRedirect("/");
            }
        }
    }


    @RequestMapping(value = "/carrinho/update", method=RequestMethod.GET)
    public void doUpdateEstoque(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, InterruptedException {
        HttpSession session = request.getSession(false);
        if (session != null){
            if(session.getAttribute("rule") == "cliente"){
                int id = Integer.parseInt(request.getParameter("id"));
                String command = request.getParameter("comando");
                String email = (String) session.getAttribute("email");
                Cookie[] cookies = request.getCookies();
                var write = response.getWriter();
                if (command.equals("add")){
                    //adicionar ao carrinho`
                    //cookie
                    //criar cookie
                    boolean flag = false;
                    String data;
                    for (Cookie cookie : cookies) {//Verifica se cookie existe
                        if (email.replace('@','-').equals(cookie.getName())) {
                            flag = true;
                            if(cookie.getValue().equals("")){
                                data = String.valueOf(id);
                            }else{
                                data =  cookie.getValue()+"-"+id;
                            }
                            cookie.setValue(data);
                            response.addCookie(cookie);
                            response.sendRedirect("/carrinho");
                        }
                    }
                    if(!flag){
                        data = String.valueOf(id);
                        Cookie c = new Cookie(email.replace('@','-'), data);
                        c.setMaxAge((3600*48)); //cria um cookie com 48 horas de duração
                        response.addCookie(c);
                        response.sendRedirect("/carrinho");
                    }
                    
                }else if (command.equals("remove")){
                    String cookString;
                    String flag = request.getParameter("flag");
                    for (Cookie cookie : cookies) {//Verifica se cookie existe
                        if(flag == null){
                            if (email.replace('@','-').equals(cookie.getName())) {
                                Carrinho carrinho = new Carrinho();
                                cookString = carrinho.cookieRemove(cookie.getValue(), id);
                                cookie.setValue(cookString);
                                response.addCookie(cookie);
                                response.sendRedirect("/carrinho");
                            }
                        }else{
                            if (cookie.getName().contains("-")) {
                                write.println(cookie.getValue() + "\n");
                                Carrinho carrinho = new Carrinho();
                                cookString = carrinho.cookieRemoveAll(cookie.getValue(), id);
                                cookie.setValue(cookString);
                                response.addCookie(cookie);
                            
                            }
                        }
                    }
                    if(flag != null){
                        response.sendRedirect("/"); 
                    }
            } 
        }else{
            response.sendRedirect("/");
        }
        }
    }
}
