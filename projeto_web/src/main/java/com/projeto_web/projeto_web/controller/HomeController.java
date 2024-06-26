package com.projeto_web.projeto_web.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projeto_web.projeto_web.model.Product;
import com.projeto_web.projeto_web.persistencia.ProductDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @RequestMapping(value = "/", method=RequestMethod.GET)
    public void doHome (HttpServletRequest request, HttpServletResponse response) throws IOException { 
        HttpSession session = request.getSession(false);
        if (session != null) {
            var write = response.getWriter();
            write.println("<html> <head> <title> Loja </title> </head> <body>");
            if(session.getAttribute("rule").equals("cliente")){
                List<Product> products = ProductDAO.getProducts();
                if(!products.isEmpty()){
                    write.println("<h1>Lista Produtos</h1>");
                    write.println("<table border='1'><thead><tr><th>Nome</th> <th>Descrição</th> <th>Quantidade</th><th>Preço</th> <th>Adicionar</th></tr></thead>");
                    
                    for(Product p : products){
                        write.println("<tr><td> " + p.getNome() + "</td><td> " + p.getDescricao() + "</td><td> " + p.getEstoque() + "</td><td>" + + p.getPreco() + "</td><td>");
                        if(p.getEstoque() > 0){
                            write.println("<a href='/carrinho/update?id=" + p.getId() + "&comando=add'" + ">Adicionar</a>");
                        }else{
                            write.println("<p> Sem estoque </p>");
                        }
                    
                        write.println("</td></tr>");
                    }
                    write.println("</table>");
                }else{
                    write.println("<h2>Nenhum produto foi cadastrado</h2>");
                }
                write.println("<br><a href='/carrinho'>Ver carrinho</a>");
            }
            if(session.getAttribute("rule").equals("lojista")){
                List<Product> products = ProductDAO.getProducts();
                String erro = request.getParameter("erro");
                if(erro != null){
                    write.println("<h2>Id já existente, produto nao cadastrado</h2>");
                }
                if(products.size() == 0){
                    write.println("<h2>Não há produtos cadastrados</h2>");
                }else{
                    write.println("<h1>Exibir Produtos</h1>");
                    write.println("<table border='1'><thead><tr><th>Nome</th> <th>Descrição</th> <th>Quantidade</th><th>Preço</th> <th>Remover</th></tr></thead>");
                    for(Product p : products){
                        write.println("<tr><td> " + p.getNome() + "</td><td> " + p.getDescricao() + "</td><td> " + p.getEstoque() + "</td><td>" + p.getPreco() + "</td><td>");
                        write.println("<a href='/logistc/products/delete?id=" + p.getId() +"'>Excluir</a>");
                        write.println("</td></tr>");
                    }
                    write.println("</table>");
                }
                write.println("<h1>Adicionar Produto</h1>");
                write.println("<br><form action='/logistc/products/create' method='post'>");
                write.println("ID do Produto <input type='number' name='id' required> <br><br>");
                write.println("Nome do Produto <input type='text' name='nome' required> <br><br>");
                write.println("Descrição <input type='text' name='descricao' required> <br><br>");
                write.println("Valor do Produto <input type='number' name='preco' required> <br><br>");
                write.println("Quantidade no Estoque <input type='number' name='estoque' required> <br><br>");
                write.println("<button type='submit'> Cadastrar Produto  </button></form>");
            }
            write.println("<br><br><a href='/logout'>Sair</a>");
            write.println("</body></html>");
        }
    }
}
