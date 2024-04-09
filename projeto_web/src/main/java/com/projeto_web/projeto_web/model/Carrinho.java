package com.projeto_web.projeto_web.model;

import java.util.ArrayList;

public class Carrinho {

    ArrayList<Product> produtos;

    public Carrinho(ArrayList<Product> produtos) {
        super();
        this.produtos = produtos;
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

    public void removeProduto (int id){
        Product p = getProduto(id);
        produtos.remove(p);
    }
    public void addProduto (Product p){
        produtos.add(p);
    }        
}
