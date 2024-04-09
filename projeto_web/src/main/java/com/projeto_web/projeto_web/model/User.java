package com.projeto_web.projeto_web.model;

public class User {
    
    private String nome;
    private String email;
    private String senha;

    public User(){

    }

    public User(String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    
    public String getNome(){
        return nome;
    }
    public String getEmail(){
        return email;
    }

    public String getSenha(){
        return senha;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

}
