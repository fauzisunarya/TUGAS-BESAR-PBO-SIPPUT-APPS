/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.model;

import gov.siput.library.database.siputDatabase;
import gov.siput.library.entity.Akun;
import gov.siput.library.error.AkunException;
import gov.siput.library.event.AkunListener;
import gov.siput.library.service.AkunDao;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class AkunModel {
    
    private int id;
    private String nama;
    private String username;
    private String email;
    private String password;
    
    private AkunListener listener;

    public AkunListener getListener() {
        return listener;
    }

    public void setListener(AkunListener listener) {
        this.listener = listener;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    protected void fireOnchange(){
        if(listener!=null){
            listener.onChange(this);
         
            
        }
        
    }
    
    protected void fireOnInsert(Akun akun){
        if(listener!=null){
            listener.OnInsert(akun);
        }
        
    }
    
     protected void fireOnUpdate(Akun akun){
        if(listener!=null){
            listener.onUpdate(akun);
        }
        
    }
    
    protected void fireOnDelete(){
        if(listener!=null){
            listener.onDelete();
        }
       
    }
    
        public void InsertAkun() throws SQLException, AkunException{
        AkunDao dao = siputDatabase.getAkunDao();
        Akun akun  = new Akun();
        akun.setNama(nama);
        akun.setUsername(username);
        akun.setEmail(email);
        akun.setPassword(password);
        
        dao.InsertAkun(akun);
        fireOnInsert(akun);
        
    }
        
        public void UpdateAkun() throws SQLException, AkunException{
        AkunDao dao = siputDatabase.getAkunDao();
        Akun akun  = new Akun();
        akun.setNama(nama);
        akun.setUsername(username);
        akun.setEmail(email);
        akun.setPassword(password);
        akun.setId(id);
        
        dao.updateAkun(akun);
        fireOnUpdate(akun);
        
    }
    
    public void DeleteAkun() throws SQLException, AkunException{
        AkunDao dao = siputDatabase.getAkunDao();;
        dao.deleteAKun(id);
        fireOnDelete();
        
    }
    
    public void resetAkun(){
        setId(0);
        setNama("");;
        setUsername("");
        setEmail("");
        setPassword(password);
    }
    
    
}
