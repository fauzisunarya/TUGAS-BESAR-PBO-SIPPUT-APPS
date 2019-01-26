/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.impl;

import gov.siput.library.entity.DataPetugas;
import gov.siput.library.error.DataPetugasException;
import gov.siput.library.service.DataPetugasDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Administrator
 */
public class DataPetugasDaoImpl implements DataPetugasDao{
    
    private Connection connection;
    
    private final String insertDataPetugas = "INSERT INTO PETUGAS (KODEPETUGAS,NAMA,JENISKELAMIN,TEMPATLAHIR,TANGGALLAHIR,ALAMAT,NOHP) VALUES (?,?,?,?,?,?,?)";
    
    private final String updateDataPetugas = "UPDATE PETUGAS SET KODEPETUGAS=?,NAMA=?,JENISKELAMIN=?,TEMPATLAHIR=?,TANGGALLAHIR=?,ALAMAT=?,NOHP=? WHERE ID=?";

    private final String deleteDataPetugas = "DELETE FROM PETUGAS WHERE ID=?";
    
    private final String getByIdPetugas = "SELECT * FROM PETUGAS WHERE ID=?";
    
    private final String getByKodePetugas= "SELECT * FROM PETUGAS WHERE KODEPETUGAS=?";
    
    private final String selectAll = "SELECT * FROM PETUGAS";
    
    public DataPetugasDaoImpl (Connection connection) {
        this.connection = connection;
    }

    @Override
    public void InsertDataPetugas(DataPetugas datapetugas) throws DataPetugasException {
        PreparedStatement statement = null;
       try{ 
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(insertDataPetugas, Statement.RETURN_GENERATED_KEYS);
           statement.setString(1,datapetugas.getKodepetugas());
           statement.setString(2,datapetugas.getNamapetugas());
           statement.setString(3,datapetugas.getJeniskelaminpetugas());
           statement.setString(4,datapetugas.getTempatlahirpetugas());
           statement.setString(5,datapetugas.getTanggallahirpetugas());
           statement.setString(6,datapetugas.getAlamatpetugas());
           statement.setString(7,datapetugas.getNohppetugas());
           statement.executeUpdate();
           
           ResultSet result = statement.getGeneratedKeys();
           if(result.next()){
               datapetugas.setIdpetugas(result.getInt(1));
           }
           
           connection.commit();
           }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataPetugasException(e.getMessage());
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
    public void UpdateDataPetugas(DataPetugas datapetugas) throws DataPetugasException {
       PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(updateDataPetugas);
           statement.setString(1,datapetugas.getKodepetugas());
           statement.setString(2,datapetugas.getNamapetugas());
           statement.setString(3,datapetugas.getJeniskelaminpetugas());
           statement.setString(4,datapetugas.getTempatlahirpetugas());
           statement.setString(5,datapetugas.getTanggallahirpetugas());
           statement.setString(6,datapetugas.getAlamatpetugas());
           statement.setString(7,datapetugas.getNohppetugas());
           statement.setInt(8,datapetugas.getIdpetugas());
           statement.executeUpdate();
           connection.commit();
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataPetugasException(e.getMessage());
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
    public void DeleteDataPetugas(Integer idpetugas) throws DataPetugasException {
       PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(deleteDataPetugas);
           statement.setInt(1, idpetugas);
           statement.executeUpdate();
           connection.commit();
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataPetugasException(e.getMessage());
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
    public DataPetugas getDataPetugas(Integer idpetugas) throws DataPetugasException {
        PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(getByIdPetugas);
           statement.setInt(1,idpetugas);
           ResultSet result = statement.executeQuery();
           DataPetugas datapetugas = null;
           if(result.next()){
               datapetugas = new DataPetugas();
               datapetugas.setIdpetugas(result.getInt("ID"));
               datapetugas.setKodepetugas(result.getString("KODEPETUGAS"));
               datapetugas.setNamapetugas(result.getString("NAMA"));
               datapetugas.setJeniskelaminpetugas(result.getString("JENISKELAMIN"));
               datapetugas.setTempatlahirpetugas(result.getString("TEMPATLAHIR"));
               datapetugas.setTanggallahirpetugas(result.getString("TANGGALLAHIR"));
               datapetugas.setAlamatpetugas(result.getString("ALAMAT"));
               datapetugas.setNohppetugas(result.getString("NOHP"));
           } else{
               throw new DataPetugasException("Petugas dengan id"+idpetugas+"tidak ditemukan");
           }
           connection.commit();
           return datapetugas;
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataPetugasException(e.getMessage());
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
    public DataPetugas getDataPetugas(String kodepetugas) throws DataPetugasException {
        PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(getByKodePetugas);
           statement.setString(1,kodepetugas);
           ResultSet result = statement.executeQuery();
           DataPetugas datapetugas = null;
           if(result.next()){
               datapetugas = new DataPetugas();
               datapetugas.setIdpetugas(result.getInt("ID"));
               datapetugas.setKodepetugas(result.getString("KODEPETUGAS"));
               datapetugas.setNamapetugas(result.getString("NAMA"));
               datapetugas.setJeniskelaminpetugas(result.getString("JENISKELAMIN"));
               datapetugas.setTempatlahirpetugas(result.getString("TEMPATLAHIR"));
               datapetugas.setTanggallahirpetugas(result.getString("TANGGALLAHIR"));
               datapetugas.setAlamatpetugas(result.getString("ALAMAT"));
               datapetugas.setNohppetugas(result.getString("NOHP"));
           } else{
               throw new DataPetugasException("Petugas dengan kode petugas"+kodepetugas+"tidak ditemukan");
           }
           connection.commit();
           return datapetugas;
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataPetugasException(e.getMessage());
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
    public List<DataPetugas> selectAllDataPetugas() throws DataPetugasException {
        Statement statement = null;
        List<DataPetugas> list = new ArrayList<DataPetugas>();
       try{
           connection.setAutoCommit(false);
           statement =connection.createStatement();

           ResultSet result = statement.executeQuery(selectAll);
           DataPetugas datapetugas = null;
           while (result.next()){
               datapetugas = new DataPetugas();
               datapetugas.setIdpetugas(result.getInt("ID"));
               datapetugas.setKodepetugas(result.getString("KODEPETUGAS"));
               datapetugas.setNamapetugas(result.getString("NAMA"));
               datapetugas.setJeniskelaminpetugas(result.getString("JENISKELAMIN"));
               datapetugas.setTempatlahirpetugas(result.getString("TEMPATLAHIR"));
               datapetugas.setTanggallahirpetugas(result.getString("TANGGALLAHIR"));
               datapetugas.setAlamatpetugas(result.getString("ALAMAT"));
               datapetugas.setNohppetugas(result.getString("NOHP"));
               list.add(datapetugas);
           }
           connection.commit();
           return list;
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataPetugasException(e.getMessage());
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
