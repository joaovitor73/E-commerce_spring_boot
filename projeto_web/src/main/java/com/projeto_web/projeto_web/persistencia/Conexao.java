package com.projeto_web.projeto_web.persistencia;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConnection() throws SQLException, URISyntaxException{
        String db = "localhost";
        String port ="5432";
        String name = "projeto";
        String username = "postgres";
        String password ="123";
        String dbUrl = "jdbc:postgresql://" + db+ ':' + port + "/" + name + "?serverTimezone=UTC";
        return DriverManager.getConnection(dbUrl, username, password);
    }
}
