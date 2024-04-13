package com.projeto_web.projeto_web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.projeto_web.projeto_web.persistencia.ProductDAO;

public class Carrinho {

    ArrayList<Product> produtos;
    private String cookie = "";

    public Carrinho(ArrayList<Product> produtos) {
        super();
        this.produtos = produtos;
    }

    public Carrinho() {
        super();
        this.produtos = new ArrayList<Product>();
    }
    public ArrayList<Product> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Product> produtos) {
        this.produtos = produtos;
    }

    public Product getProduto (int id){
        Product mp = null;
        for (Product p : produtos){
            if (p.getId() == id){
                return p;
            }
        }
        return mp;
    }

    public Map<Integer, Integer> cookieToArray(String produtos){ //cookie
        String[] produtosString = produtos.split("\\-");
        Set<String> conjuntoProduto = new HashSet<>();
        Map<Integer, Integer> mapProduto = new HashMap<>();
        int id;
        for (int i = 0; i < produtosString.length; i++) {
            id = Integer.parseInt(produtosString[i]);
            if(ProductDAO.getProductId(id)  != null){
                if(i != 0)
                    this.setCookie("-" + produtosString[i]);
                else
                    this.setCookie(produtosString[i]);
                if(conjuntoProduto.contains(produtosString[i])){
                    mapProduto.put(id, mapProduto.get(id)+1);
                }else{
                    mapProduto.put(id, 1);
                    conjuntoProduto.add(produtosString[i]);
                }
            }
        }
        return mapProduto;
    }

    public void setCookie(String dados){
        cookie+=dados;
    }

    public String getCookies(){
        return cookie;
    }

    public String cookieRemove(String produtos, int id){
        String idStr = String.valueOf(id);
        String idPreceding = "-" + idStr;
        String produtosAtualizado;
        // Se o ID está precedido por "|", o remove
        if (produtos.contains(idPreceding)) {
            produtosAtualizado = produtos.replaceFirst(idPreceding, "");
        } else {
            // Se não, remove o ID normalmente
            produtosAtualizado = produtos.replaceFirst(idStr, "");
        }
        return produtosAtualizado;
    }


    public void removeProduto (int id){
        Product p = getProduto(id);
        produtos.remove(p);
    }
    public void addProduto (Product p){
        produtos.add(p);
    }        
}
