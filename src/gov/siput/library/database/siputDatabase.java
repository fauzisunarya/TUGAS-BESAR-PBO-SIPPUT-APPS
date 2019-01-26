/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import gov.siput.library.impl.AkunDaoImpl;
import gov.siput.library.impl.DataBukuDaoimpl;
import gov.siput.library.impl.DataPetugasDaoImpl;
import gov.siput.library.impl.DataSiswaDaoImpl;
import gov.siput.library.service.AkunDao;
import gov.siput.library.service.DataBukuDao;
import gov.siput.library.service.DataPetugasDao;
import gov.siput.library.service.DataSiswaDao;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class siputDatabase {
    
    private static Connection koneksi;
    private static DataBukuDao databukuDao;
    private static AkunDao akunDao;
    private static DataSiswaDao datasiswaDao;
    private static DataPetugasDao datapetugasDao;
    
    
    public static Connection getConnection() throws SQLException{
       if(koneksi==null){
           
           MysqlDataSource datasource = new MysqlDataSource();
           datasource.setURL("jdbc:mysql://localhost:3306/perpustakaan");
           datasource.setUser("root");
           datasource.setPassword("");  
           koneksi = datasource.getConnection();
           
       }  
       
       return koneksi;
    }
    
    public static DataBukuDao getDataBukuDao()throws SQLException{
        if(databukuDao==null){
            databukuDao = new DataBukuDaoimpl(getConnection());
        }
        
        return databukuDao;
    }
    
    public static AkunDao getAkunDao()throws SQLException{
        if(akunDao==null){
            akunDao = new AkunDaoImpl(getConnection());
        }
        
        return akunDao;
    }
    
        public static DataSiswaDao getDataSiswaDao()throws SQLException{
        if(datasiswaDao==null){
            datasiswaDao = new DataSiswaDaoImpl(getConnection());
        }
        
        return datasiswaDao;
    }
        
        public static DataPetugasDao getDataPetugasDao()throws SQLException{
        if(datapetugasDao==null){
            datapetugasDao = new DataPetugasDaoImpl(getConnection());
        }
        
        return datapetugasDao;
    }
    
}
