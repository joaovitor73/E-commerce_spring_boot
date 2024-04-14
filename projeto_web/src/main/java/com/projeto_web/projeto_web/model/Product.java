package com.projeto_web.projeto_web.model;

public class Product {

    int id;
    int preco;
    String nome;
    String Descricao;
    int estoque;

    public Product(int id, int preco, String nome, String descricao, int estoque) {
        super();
        this.id = id;
        this.preco = preco;
        this.nome = nome;
        this.Descricao = descricao;
        this.estoque = estoque;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPreco() {
        return preco;
    }
    public void setPreco(int preco) {
        this.preco = preco;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return Descricao;
    }
    public void setDescricao(String descricao) {
        Descricao = descricao;
    }
    public int getEstoque() {
        return estoque;
    }
    public void setEstoque(int qtd) {
        this.estoque = qtd;
    }
    public void incrementaEstoque() {
        this.estoque++;
    }
    public void diminuiEstoque() {
        this.estoque--;
    }        
}
