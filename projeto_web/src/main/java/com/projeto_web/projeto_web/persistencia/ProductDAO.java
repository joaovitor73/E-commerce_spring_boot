package com.projeto_web.projeto_web.persistencia;


import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.projeto_web.projeto_web.model.Product;


public class ProductDAO {

    private static String selectProducs = "select * from Products";
    private static String selectProducId = "select * from Products where id=?";
    private static String insertProduct = "insert into Products (id,preco,nome,descricao,estoque) values(?,?,?,?,?)";
    private static String deleteProduct = "delete from Products where email = ?";

    public static Product getProductId(int id){
        Product product = null;
        try{
            Connection connection = Conexao.getConnection();
            PreparedStatement stm = connection.prepareStatement(selectProducId);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
                product = new Product(rs.getInt("id"),rs.getInt("preco"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("quantidade"));
            connection.close();
        }catch(SQLException | URISyntaxException e){
            System.out.println(e);
        }
        return product;
    }

    public static List<Product> getProducts(){
        Product product = null;
        List<Product> products = new ArrayList<Product>();
        try{
            Connection connection = Conexao.getConnection();
            PreparedStatement stm = connection.prepareStatement(selectProducs);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                product = new Product(rs.getInt("id"),rs.getInt("preco"), rs.getString("nome"), rs.getString("descricao"), rs.getInt("quantidade"));
                products.add(product);
            }        
        }catch(SQLException | URISyntaxException e){
            System.out.println(e);
        }
        return products;
    }
    
    public static void insertProduct(Product product){
        try{
            Connection connection = Conexao.getConnection();
            PreparedStatement stm = connection.prepareStatement(insertProduct);
            stm.setInt(1, product.getId());
            stm.setInt(2, product.getPreco());
            stm.setString(3, product.getNome());
            stm.setString(1, product.getDescricao());
            stm.setInt(2, product.getEstoque());
            stm.executeUpdate();
            connection.close();
        } catch(SQLException | URISyntaxException e){
            System.out.println(e);
        }
    }

    public static void deleteProduct(int id){
        try{
            Connection connection = Conexao.getConnection();
            PreparedStatement stm = connection.prepareStatement(deleteProduct);
            stm.setInt(1,id);
            stm.executeUpdate();
            connection.close();
        }catch(SQLException | URISyntaxException e){
            System.out.println(e);
        }
    }

}
