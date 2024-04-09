package com.projeto_web.projeto_web.persistencia;

import com.projeto_web.projeto_web.model.Logistic;

public class LogisticDAO {
    private String selectLogistic = "select * from logistics where email=? and senha=?";
    private String selectLogisticEmail = "select * from logistics where email=?";
    private String insertLogistic = "insert into logistics (nome, email, senha) values(?,?,?)";
    private String deleteLogistic = "delete from logistics where email = ?";
   
    public Object getLogisticEmail(String email){
        return UserDAO.getUserEmail(email, selectLogisticEmail);
    }

    public Object getLogistic(String email, String senha){
        return UserDAO.getUser(email, senha, selectLogistic);
    }

    public void insertLogistic(Logistic logistic){
        UserDAO.insertUser(logistic, insertLogistic);
    }

    public void deleteLogistic(String email){
        UserDAO.deleteUser(email, deleteLogistic);
    }
}
