package com.projeto_web.projeto_web.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    }

    @RequestMapping(value = "/logistc/products/delete", method = RequestMethod.DELETE)
    public void doDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
            Integer id =  Integer.parseInt(request.getParameter("email"));
            var write = response.getWriter();
            if(ProductDAO.getProductId(id) != null){
                ProductDAO.deleteProduct(id);
                write.println("Produto deletado");
            }else{
                write.println("Produto não encontrado");
            }
    }
}
