package com.projeto_web.projeto_web.persistencia;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.projeto_web.projeto_web.model.User;

public class UserDAO {

    public static User getUserEmail(String email, String query){
        User user = null;
        try{
            Connection connection = Conexao.getConnection();
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
                user = new User(rs.getString("nome"),rs.getString("email"), rs.getString("senha"));
            connection.close();
        }catch(SQLException | URISyntaxException e){
            System.out.println(e);
        }
        return user;
    }

    public static User getUser(String email, String senha, String query){
        User user = null;
        try{
            Connection connection = Conexao.getConnection();
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1,email);
            stm.setString(2, senha);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
                user = new User(rs.getString("nome"), rs.getString("email"), rs.getString("senha"));
            connection.close();
        }catch(SQLException | URISyntaxException e){
            System.out.println(e);
        }
        return user;
    }

    public static void insertUser(User user, String query){
        try{
            Connection connection = Conexao.getConnection();
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, user.getNome());
            stm.setString(2, user.getEmail());
            stm.setString(3, user.getSenha());
            stm.executeUpdate();
            connection.close();
        } catch(SQLException | URISyntaxException e){
            System.out.println(e);
        }
    }

    public static void deleteUser(String email,String query){
        try{
            Connection connection = Conexao.getConnection();
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1,email);
            stm.executeUpdate();
            connection.close();
        }catch(SQLException | URISyntaxException e){
            System.out.println(e);
        }
    }

}
