/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.impl;

import com.mysql.jdbc.PreparedStatement;
import gov.siput.library.entity.Akun;
import gov.siput.library.error.AkunException;
import gov.siput.library.service.AkunDao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class AkunDaoImpl implements AkunDao {
    
    private Connection connection;
    
    private final String insertAkun = "INSERT INTO AKUN (NAMA,USERNAME,EMAIL,PASSWORD) VALUES (?,?,?,?)";
    
    private final String updateAkun = "UPDATE AKUN SET NAMA=?,EMAIL=?,PASSWORD=? WHERE ID=?";

    private final String deleteAkun = "DELETE FROM AKUNWHERE ID=?";
    
    private final String getById = "SELECT * FROM AKUN WHERE ID=?";
    
    private final String getByEmail = "SELECT * FROM AKUN WHERE EMAIL=?";
    
    private final String selectAll = "SELECT * FROM AKUN";

    public AkunDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void InsertAkun(Akun akun) throws AkunException {
       PreparedStatement statement = null;
       try{ 
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(insertAkun, Statement.RETURN_GENERATED_KEYS);
           statement.setString(1,akun.getNama());
           statement.setString(2,akun.getUsername());
           statement.setString(3,akun.getEmail());
           statement.setString(4,akun.getPassword());
           statement.executeUpdate();
           
           ResultSet result = statement.getGeneratedKeys();
           if(result.next()){
               akun.setId(result.getInt(1));
               
           }
           
           connection.commit();
           }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new AkunException(e.getMessage());
       }finally{
           try{
               connection.setAutoCommit(true);
           }catch(SQLException ex){
           }
           if(statement!=null){
               try {
               statement.close();
           }catch(SQLException e){   
           }
           } 
       }
    }

    @Override
    public void updateAkun(Akun akun) throws AkunException {
         PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(updateAkun);
           statement.setString(1,akun.getNama());
           statement.setString(2,akun.getUsername());
           statement.setString(3,akun.getEmail());
           statement.setInt(4, akun.getId());
           statement.executeUpdate();
           connection.commit();
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new AkunException(e.getMessage());
       }finally{
           try{
               connection.setAutoCommit(true);
           }catch(SQLException ex){
           }
           if(statement!=null){
               try {
               statement.close();
           }catch(SQLException e){   
           }
           } 
       }      
    }

    @Override
    public void deleteAKun(Integer id) throws AkunException {
        PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(deleteAkun);
           statement.setInt(1, id);
           statement.executeUpdate();
           connection.commit();
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new AkunException(e.getMessage());
       }finally{
           try{
               connection.setAutoCommit(true);
           }catch(SQLException ex){
           }
           if(statement!=null){
               try {
               statement.close();
           }catch(SQLException e){   
           }
           } 
       }      
    }

    @Override
    public Akun getAkun(Integer id) throws AkunException {
        PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(getById);
           statement.setInt(1,id);
           ResultSet result = statement.executeQuery();
           Akun akun = null;
           if(result.next()){
               akun = new Akun();
               akun.setId(result.getInt("ID"));
               akun.setNama(result.getString("NAMA"));
               akun.setUsername(result.getString("USERNAME"));
               akun.setEmail(result.getString("EMAIL"));
               akun.setPassword(result.getString("PASSWORD"));
           } else{
               throw new AkunException("Akun dengan id"+id+"tidak ditemukan");
           }
           connection.commit();
           return akun;
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new AkunException(e.getMessage());
       }finally{
           try{
               connection.setAutoCommit(true);
           }catch(SQLException ex){
           }
           if(statement!=null){
               try {
               statement.close();
           }catch(SQLException e){   
           }
           } 
       }      
        
    }

    @Override
    public Akun getAkun(String email) throws AkunException {
        PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(getByEmail);
           statement.setString(1,email);
           ResultSet result = statement.executeQuery();
           Akun akun = null;
           if(result.next()){
               akun = new Akun();
               akun.setId(result.getInt("ID"));
               akun.setNama(result.getString("NAMA"));
               akun.setUsername(result.getString("USERNAME"));
               akun.setEmail(result.getString("EMAIL"));
               akun.setPassword(result.getString("PASSWORD"));
           } else{
               throw new AkunException("Akun dengan email"+email+"tidak ditemukan");
           }
           connection.commit();
           return akun;
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new AkunException(e.getMessage());
       }finally{
           try{
               connection.setAutoCommit(true);
           }catch(SQLException ex){
           }
           if(statement!=null){
               try {
               statement.close();
           }catch(SQLException e){   
           }
           } 
       }      
    }

    @Override
    public List<Akun> selectAllAKun() throws AkunException {
        Statement statement = null;
        List<Akun> list = new ArrayList<Akun>();
       try{
           connection.setAutoCommit(false);
           statement =connection.createStatement();

           ResultSet result = statement.executeQuery(selectAll);
           Akun akun = null;
           while (result.next()){
               akun = new Akun();
               akun.setId(result.getInt("ID"));
               akun.setNama(result.getString("NAMA"));
               akun.setUsername(result.getString("USERNAME"));
               akun.setEmail(result.getString("EMAIL"));
               akun.setPassword(result.getString("PASSWORD"));
               list.add(akun);
           }
           connection.commit();
           return list;
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new AkunException(e.getMessage());
       }finally{
           try{
               connection.setAutoCommit(true);
           }catch(SQLException ex){
           }
           if(statement!=null){
               try {
               statement.close();
           }catch(SQLException e){   
           }
           }      
    }
    }   
}
