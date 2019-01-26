/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.controller;

import gov.siput.library.model.AkunModel;
import gov.siput.library.view.RegisterView;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class AkunController {
    
    private AkunModel model;

    public void setModel(AkunModel model) {
        this.model = model;
    }
    
        public void resetAkun(RegisterView view){
        model.resetAkun();
        
    }
    
    public void insertAkun(RegisterView view){
        String nama = view.getjTextField_Nama().getText();
        String username = view.getjTextField_Username().getText();
        String email = view.getjTextField_Email().getText();
        String password = view.getjPasswordField_Password().getText();
        
        if(nama.trim().equals("")){
            JOptionPane.showMessageDialog(view, "Nama Tidak Boleh Kosong");
        }else if(username.trim().equals("")){
            JOptionPane.showMessageDialog(view, "Username Tidak Boleh Kosong");
        }else if(username.length()<12){
            JOptionPane.showMessageDialog(view, "Username Tidak Boleh Kurang dari 12 karakter");
        }else if(username.length()>12){
            JOptionPane.showMessageDialog(view, "Username Tidak Boleh lebih dari 12 karakter");
        }else if(!email.contains("@")||!email.contains(".")){
            JOptionPane.showMessageDialog(view, "Email tidak valid");
        }else{
            model.setNama(nama);
            model.setUsername(username);
            model.setEmail(email);
            model.setPassword(password);
            
            try{
                
                model.InsertAkun();
                model.resetAkun();
                JOptionPane.showMessageDialog(view, "Registrasi Berhasil");
                
            }catch (Throwable throwable){
                
                JOptionPane.showMessageDialog(view, new Object[]{"Terjadi Eror di database dengan pesan", throwable.getMessage()});
                
            }
        }
        
    }
    
}
