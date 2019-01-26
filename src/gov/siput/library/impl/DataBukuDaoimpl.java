/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.impl;

import com.mysql.jdbc.PreparedStatement;
import gov.siput.library.entity.DataBuku;
import gov.siput.library.error.DataBukuException;
import gov.siput.library.service.DataBukuDao;
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
public class DataBukuDaoimpl implements DataBukuDao {
    
    private Connection connection;
    
    private final String insertDataBuku = "INSERT INTO BUKU (KODEBUKU,JUDULBUKU,PENGARANG,TAHUNTERBIT,PENERBIT,JUMLAHBUKU,KODERAK) VALUES (?,?,?,?,?,?,?)";
    
    private final String updateDataBuku = "UPDATE BUKU SET KODEBUKU=?,JUDULBUKU=?,PENGARANG=?,TAHUNTERBIT=?,PENERBIT=?,JUMLAHBUKU=?,KODERAK=? WHERE ID=?";

    private final String deleteDataBuku = "DELETE FROM BUKU WHERE ID=?";
    
    private final String getById = "SELECT * FROM BUKU WHERE ID=?";
    
    private final String getByKodeBuku = "SELECT * FROM BUKU WHERE KODEBUKU=?";
    
    private final String selectAll = "SELECT * FROM BUKU";

    public DataBukuDaoimpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void InsertDataBuku(DataBuku databuku) throws DataBukuException {
       PreparedStatement statement = null;
       try{ 
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(insertDataBuku, Statement.RETURN_GENERATED_KEYS);
           statement.setString(1,databuku.getKodebuku());
           statement.setString(2,databuku.getJudulbuku());
           statement.setString(3,databuku.getPengarang());
           statement.setString(4,databuku.getTahunterbit());
           statement.setString(5,databuku.getPenerbit());
           statement.setString(6,databuku.getJumlahbuku());
           statement.setString(7,databuku.getKoderak());
           statement.executeUpdate();
           
           ResultSet result = statement.getGeneratedKeys();
           if(result.next()){
               databuku.setId(result.getInt(1));
           }
           
           connection.commit();
           }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataBukuException(e.getMessage());
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
    public void updateDataBuku(DataBuku databuku) throws DataBukuException {
       PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(updateDataBuku);
           statement.setString(1,databuku.getKodebuku());
           statement.setString(2,databuku.getJudulbuku());
           statement.setString(3,databuku.getPengarang());
           statement.setString(4,databuku.getTahunterbit());
           statement.setString(5,databuku.getPenerbit());
           statement.setString(6,databuku.getJumlahbuku());
           statement.setString(7,databuku.getKoderak());
           statement.setInt(8,databuku.getId());
           statement.executeUpdate();
           connection.commit();
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataBukuException(e.getMessage());
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
    public void deleteDataBuku(Integer id) throws DataBukuException {
       PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(deleteDataBuku);
           statement.setInt(1, id);
           statement.executeUpdate();
           connection.commit();
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataBukuException(e.getMessage());
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
    public DataBuku getDataBuku(Integer id) throws DataBukuException {
       PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(getById);
           statement.setInt(1,id);
           ResultSet result = statement.executeQuery();
           DataBuku databuku = null;
           if(result.next()){
               databuku = new DataBuku();
               databuku.setId(result.getInt("ID"));
               databuku.setKodebuku(result.getString("KODEBUKU"));
               databuku.setJudulbuku(result.getString("JUDULBUKU"));
               databuku.setPengarang(result.getString("PENGARANG"));
               databuku.setTahunterbit(result.getString("TAHUNTERBIT"));
               databuku.setPenerbit(result.getString("PENERBIT"));
               databuku.setJumlahbuku(result.getString("JUMLAHBUKU"));
               databuku.setKoderak(result.getString("KODERAK"));

           } else{
               throw new DataBukuException("Buku dengan id"+id+"tidak ditemukan");
           }
           connection.commit();
           return databuku;
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataBukuException(e.getMessage());
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
    public DataBuku getDataBuku(String kodebuku) throws DataBukuException {
       PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(getByKodeBuku);
           statement.setString(1,kodebuku);
           ResultSet result = statement.executeQuery();
           DataBuku databuku = null;
           if(result.next()){
               databuku = new DataBuku();
               databuku.setId(result.getInt("ID"));
               databuku.setKodebuku(result.getString("KODEBUKU"));
               databuku.setJudulbuku(result.getString("JUDULBUKU"));
               databuku.setPengarang(result.getString("PENGARANG"));
               databuku.setTahunterbit(result.getString("TAHUNTERBIT"));
               databuku.setPenerbit(result.getString("PENERBIT"));
               databuku.setJumlahbuku(result.getString("JUMLAHBUKU"));
               databuku.setKoderak(result.getString("KODERAK"));
           } else{
               throw new DataBukuException("Buku dengan kodebuku"+kodebuku+"tidak ditemukan");
           }
           connection.commit();
           return databuku;
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataBukuException(e.getMessage());
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
    public List<DataBuku> selectAllDataBuku() throws DataBukuException {
        Statement statement = null;
        List<DataBuku> list = new ArrayList<DataBuku>();
       try{
           connection.setAutoCommit(false);
           statement =connection.createStatement();

           ResultSet result = statement.executeQuery(selectAll);
           DataBuku databuku = null;
           while (result.next()){
               databuku = new DataBuku();
               databuku.setId(result.getInt("ID"));
               databuku.setKodebuku(result.getString("KODEBUKU"));
               databuku.setJudulbuku(result.getString("JUDULBUKU"));
               databuku.setPengarang(result.getString("PENGARANG"));
               databuku.setTahunterbit(result.getString("TAHUNTERBIT"));
               databuku.setPenerbit(result.getString("PENERBIT"));
               databuku.setJumlahbuku(result.getString("JUMLAHBUKU"));
               databuku.setKoderak(result.getString("KODERAK"));
               list.add(databuku);
           }
           connection.commit();
           return list;
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataBukuException(e.getMessage());
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
