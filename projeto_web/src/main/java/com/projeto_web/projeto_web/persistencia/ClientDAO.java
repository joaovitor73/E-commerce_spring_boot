package com.projeto_web.projeto_web.persistencia;

import com.projeto_web.projeto_web.model.Client;

public class ClientDAO {

    private String selectClient = "select * from clients where email=? and senha=?";
    private String selectClientEmail = "select * from clients where email=?";
    private String insertClient = "insert into clients (nome, email, senha) values(?,?,?)";
    private String deleteClient = "delete from clients where email = ?";
   
    public Object getClientEmail(String email){
        return UserDAO.getUserEmail(email, selectClientEmail);
    }

    public Object getClient(String email, String senha){
        return UserDAO.getUser(email, senha, selectClient);
    }

    public void insertClient(Client client){
        UserDAO.insertUser(client, insertClient);
    }

    public void deleteClient(String email){
        UserDAO.deleteUser(email, deleteClient);
    }
}