package com.projeto_web.projeto_web.controller;

import java.io.IOException;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projeto_web.projeto_web.model.Carrinho;
import com.projeto_web.projeto_web.model.Product;
import com.projeto_web.projeto_web.persistencia.ProductDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ProductController {

    @RequestMapping(value="/logistc/products/create", method = RequestMethod.POST)
    public void doCreateProdutc(HttpServletRequest request, HttpServletResponse response)  throws IOException, ServletException{
        Integer id = Integer.parseInt(request.getParameter("id"));
        Integer preco = Integer.parseInt(request.getParameter("preco"));
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");;
        Integer estoque = Integer.parseInt(request.getParameter("estoque"));
        Product product = new Product(id,preco,nome,descricao,estoque);
        ProductDAO.insertProduct(product);
        response.sendRedirect("/");
    }

    @RequestMapping(value = "/logistc/products/delete", method = RequestMethod.GET)
    public void doDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String id = request.getParameter("id");
        Cookie[] cookies = request.getCookies(); 
        for (Cookie cookie : cookies) {
            String[] ids = cookie.getValue().split("-");
            String cookString;
            for (String i : ids) {
                if (i.equals(id)) {
                    //aqui ainda está com bugs, por causa que não está removendo o produto do carrinho do cliente
                    Carrinho carrinho = new Carrinho();
                    cookString = carrinho.cookieRemove(cookie.getValue(), Integer.parseInt(i));
                    cookie.setValue(cookString);
                    Cookie newCookie = new Cookie(cookie.getName(), cookie.getValue());
                    response.addCookie(cookie);
                    response.addCookie(newCookie);
                    response.sendRedirect("/logistc/products/delete");
                }
            }
        }        
        if(ProductDAO.getProductId(Integer.parseInt(id)) != null){
            ProductDAO.deleteProduct(Integer.parseInt(id));
            response.sendRedirect("/");
        }
    }
}