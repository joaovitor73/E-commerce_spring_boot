package com.projeto_web.projeto_web.controller;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projeto_web.projeto_web.model.Product;
import com.projeto_web.projeto_web.persistencia.ProductDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class CarrinhoController {

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
                    Cookie c = new Cookie(email.replace('@','|'), cookie.getValue()+"|"+nome+"|"+preco);
                    c.setMaxAge(3600-cookie.getMaxAge());
                    response.addCookie(c);
                }
            }
            if(!flag){
                Cookie c = new Cookie(email.replace('@','|'), nome+"|"+preco );
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
// Cookie[] cookies = request.getCookies();
// boolean flag = true;
// if (cookies == null) {//Criar cookie
//     Cookie cookie = new Cookie(email.replace('@','|'), "");
//     cookie.setMaxAge(3600);
//     response.addCookie(cookie);
// }else{
//      for (Cookie cookie : cookies) {//Verifica se cookie existe
//         if (email.replace('@','|').equals(cookie.getName())) {
//             flag = false;
//         }
//     }
//     if(flag){//criar cookie
//         Cookie cookie = new Cookie(email.replace('@','|'), "");
//         cookie.setMaxAge(3600);
//         response.addCookie(cookie);
//     }
// }