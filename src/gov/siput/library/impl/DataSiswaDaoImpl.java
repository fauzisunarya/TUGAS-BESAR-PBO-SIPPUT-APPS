/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.impl;

import com.mysql.jdbc.PreparedStatement;
import gov.siput.library.entity.DataSiswa;
import gov.siput.library.error.DataSiswaException;
import gov.siput.library.service.DataSiswaDao;
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
public class DataSiswaDaoImpl implements DataSiswaDao {
    
    private Connection connection;
    
    private final String insertDataSiswa = "INSERT INTO SISWA (NISN,NAMA,KELAS,JENISKELAMIN,TEMPATLAHIR,TANGGALLAHIR,ALAMAT,NOHP) VALUES (?,?,?,?,?,?,?,?)";
    
    private final String updateDataSiswa = "UPDATE SISWA SET NISN=?,NAMA=?,KELAS=?,JENISKELAMIN=?,TEMPATLAHIR=?,TANGGALLAHIR=?,ALAMAT=?,NOHP=? WHERE ID=?";

    private final String deleteDataSiswa = "DELETE FROM SISWA WHERE ID=?";
    
    private final String getById = "SELECT * FROM SISWA WHERE ID=?";
    
    private final String getByNISN = "SELECT * FROM SISWA WHERE NISN=?";
    
    private final String selectAll = "SELECT * FROM SISWA";

    public DataSiswaDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void InsertDataSiswa(DataSiswa datasiswa) throws DataSiswaException {
       PreparedStatement statement = null;
       try{ 
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(insertDataSiswa, Statement.RETURN_GENERATED_KEYS);
           statement.setString(1,datasiswa.getNisn());
           statement.setString(2,datasiswa.getNamasiswa());
           statement.setString(3,datasiswa.getKelas());
           statement.setString(4,datasiswa.getJeniskelaminsiswa());
           statement.setString(5,datasiswa.getTempatlahirsiswa());
           statement.setString(6,datasiswa.getTanggallahirsiswa());
           statement.setString(7,datasiswa.getAlamatsiswa());
           statement.setString(8,datasiswa.getNohpsiswa());
           statement.executeUpdate();
           
           ResultSet result = statement.getGeneratedKeys();
           if(result.next()){
               datasiswa.setIdsiswa(result.getInt(1));
           }
           
           connection.commit();
           }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataSiswaException(e.getMessage());
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
    public void updateDataSiswa(DataSiswa datasiswa) throws DataSiswaException {
       PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(updateDataSiswa);
           statement.setString(1,datasiswa.getNisn());
           statement.setString(2,datasiswa.getNamasiswa());
           statement.setString(3,datasiswa.getKelas());
           statement.setString(4,datasiswa.getJeniskelaminsiswa());
           statement.setString(5,datasiswa.getTempatlahirsiswa());
           statement.setString(6,datasiswa.getTanggallahirsiswa());
           statement.setString(7,datasiswa.getAlamatsiswa());
           statement.setString(8,datasiswa.getNohpsiswa());
           statement.setInt(9,datasiswa.getIdsiswa());
           statement.executeUpdate();
           connection.commit();
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataSiswaException(e.getMessage());
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
    public void deleteDataSiswa(Integer id) throws DataSiswaException {
       PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(deleteDataSiswa);
           statement.setInt(1, id);
           statement.executeUpdate();
           connection.commit();
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataSiswaException(e.getMessage());
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
    public DataSiswa getDataSiswa(Integer id) throws DataSiswaException {
       PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(getById);
           statement.setInt(1,id);
           ResultSet result = statement.executeQuery();
           DataSiswa datasiswa = null;
           if(result.next()){
               datasiswa = new DataSiswa();
               datasiswa.setIdsiswa(result.getInt("ID"));
               datasiswa.setNisn(result.getString("NISN"));
               datasiswa.setNamasiswa(result.getString("NAMA"));
               datasiswa.setKelas(result.getString("KELAS"));
               datasiswa.setJeniskelaminsiswa(result.getString("JENISKELAMIN"));
               datasiswa.setTempatlahirsiswa(result.getString("TEMPATLAHIR"));
               datasiswa.setTanggallahirsiswa(result.getString("TANGGALLAHIR"));
               datasiswa.setAlamatsiswa(result.getString("ALAMAT"));
               datasiswa.setNohpsiswa(result.getString("NOHP"));
           } else{
               throw new DataSiswaException("Siswa dengan id"+id+"tidak ditemukan");
           }
           connection.commit();
           return datasiswa;
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataSiswaException(e.getMessage());
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
    public DataSiswa getDataSiswa(String nisn) throws DataSiswaException {
        PreparedStatement statement = null;
       try{
           connection.setAutoCommit(false);
           statement = (PreparedStatement) connection.prepareStatement(getByNISN);
           statement.setString(1,nisn);
           ResultSet result = statement.executeQuery();
           DataSiswa datasiswa = null;
           if(result.next()){
               datasiswa = new DataSiswa();
               datasiswa.setIdsiswa(result.getInt("ID"));
               datasiswa.setNisn(result.getString("NISN"));
               datasiswa.setNamasiswa(result.getString("NAMA"));
               datasiswa.setKelas(result.getString("KELAS"));
               datasiswa.setJeniskelaminsiswa(result.getString("JENISKELAMIN"));
               datasiswa.setTempatlahirsiswa(result.getString("TEMPATLAHIR"));
               datasiswa.setTanggallahirsiswa(result.getString("TANGGALLAHIR"));
               datasiswa.setAlamatsiswa(result.getString("ALAMAT"));
               datasiswa.setNohpsiswa(result.getString("NOHP"));
           } else{
               throw new DataSiswaException("Siswa dengan NISN"+nisn+"tidak ditemukan");
           }
           connection.commit();
           return datasiswa;
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataSiswaException(e.getMessage());
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
    public List<DataSiswa> selectAllDataSiswa() throws DataSiswaException {
        Statement statement = null;
        List<DataSiswa> list = new ArrayList<DataSiswa>();
       try{
           connection.setAutoCommit(false);
           statement =connection.createStatement();

           ResultSet result = statement.executeQuery(selectAll);
           DataSiswa datasiswa = null;
           while (result.next()){
               datasiswa = new DataSiswa();
               datasiswa.setIdsiswa(result.getInt("ID"));
               datasiswa.setNisn(result.getString("NISN"));
               datasiswa.setNamasiswa(result.getString("NAMA"));
               datasiswa.setKelas(result.getString("KELAS"));
               datasiswa.setJeniskelaminsiswa(result.getString("JENISKELAMIN"));
               datasiswa.setTempatlahirsiswa(result.getString("TEMPATLAHIR"));
               datasiswa.setTanggallahirsiswa(result.getString("TANGGALLAHIR"));
               datasiswa.setAlamatsiswa(result.getString("ALAMAT"));
               datasiswa.setNohpsiswa(result.getString("NOHP"));
               list.add(datasiswa);
           }
           connection.commit();
           return list;
       }catch(SQLException e){
               try{
                  connection.rollback();
               }catch(SQLException ex){   
               }
           throw new DataSiswaException(e.getMessage());
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
