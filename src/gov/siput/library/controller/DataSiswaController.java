/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.controller;

import gov.siput.library.model.DataSiswaModel;
import gov.siput.library.view.DashboardMainView;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class DataSiswaController {
    
    private DataSiswaModel model;

    public void setModel(DataSiswaModel model) {
        this.model = model;
    }
    
    public void resetDataSiswa(DashboardMainView view){
        model.resetDataSiswa();
        
    }
    
    public void insertDataSiswa(DashboardMainView view){
        String nisn = view.getjTextField_NISN().getText();
        String nama = view.getjTextField_NamaSiswa().getText();
        String kelas = view.getjTextField_Kelas().getText();
        String jeniskelamin = view.getjTextField_JenisKelamin().getText();
        String tempatlahir = view.getjTextField_TempatLahirSiswa().getText();
        String tanggallahir = view.getjTextField_TanggalLahirSiswa().getText();
        String alamat = view.getjTextField_AlamatSiswa().getText();
        String nohp = view.getjTextField_NoHpSiswa().getText();
        
        if(nisn.trim().equals("")){
            JOptionPane.showMessageDialog(view, "NISN Tidak Boleh Kosong");
        }else if(nisn.length()>15){
            JOptionPane.showMessageDialog(view, "NISN Tidak Boleh lebih dari 15 karakter");
        }else{
            model.setNisn(nisn);
            model.setNamasiswa(nama);
            model.setKelas(kelas);
            model.setJeniskelaminsiswa(jeniskelamin);
            model.setTempatlahirsiswa(tempatlahir);
            model.setTanggallahirsiswa(tanggallahir);
            model.setAlamatsiswa(alamat);
            model.setNohpsiswa(nohp);
            
            try{
                
                model.InsertDataSiswa();
                model.resetDataSiswa();
                JOptionPane.showMessageDialog(view, "Data Siswa Berhasil ditambahkan");
                
            }catch (Throwable throwable){
                
                JOptionPane.showMessageDialog(view, new Object[]{"Terjadi Eror di database dengan pesan", throwable.getMessage()});
                
            }
        }
        
    }
        
        public void updateDataSiswa(DashboardMainView view){
        
        if(view.getjTable_DataSiswa().getSelectedRowCount()==0){
            JOptionPane.showMessageDialog(view, "Silahkan seleksi baris data yang akan di ubah");
            return;
        }
        Integer idsiswa = Integer.parseInt(view.getjTextField_IdSiswa().getText());
        String nisn = view.getjTextField_NISN().getText();
        String nama = view.getjTextField_NamaSiswa().getText();
        String kelas = view.getjTextField_Kelas().getText();
        String jeniskelamin = view.getjTextField_JenisKelamin().getText();
        String tempatlahir = view.getjTextField_TempatLahirSiswa().getText();
        String tanggallahir = view.getjTextField_TanggalLahirSiswa().getText();
        String alamat = view.getjTextField_AlamatSiswa().getText();
        String nohp = view.getjTextField_NoHpSiswa().getText();
        
        if(nisn.trim().equals("")){
            JOptionPane.showMessageDialog(view, "NISN Tidak Boleh Kosong");
        }else if(nisn.length()>15){
            JOptionPane.showMessageDialog(view, "NISN Tidak Boleh lebih dari 15 karakter");
        }else{
            model.setIdsiswa(idsiswa);
            model.setNisn(nisn);
            model.setNamasiswa(nama);
            model.setKelas(kelas);
            model.setJeniskelaminsiswa(jeniskelamin);
            model.setTempatlahirsiswa(tempatlahir);
            model.setTanggallahirsiswa(tanggallahir);
            model.setAlamatsiswa(alamat);
            model.setNohpsiswa(nohp);
            
            try{
                
                model.UpdateDataSiswa();
                JOptionPane.showMessageDialog(view, "Data Siswa Berhasil Di Ubah");
                model.resetDataSiswa();
            }catch (Throwable throwable){
                JOptionPane.showMessageDialog(view, new Object[]{"Terjadi Eror di database dengan pesan", throwable.getMessage()});
                
            }
        }
        
        
    }
            
    public void deleteDataSIswa(DashboardMainView view){
        
        if(view.getjTable_DataSiswa().getSelectedRowCount()==0){
            JOptionPane.showMessageDialog(view, "Silahkan seleksi baris data yang akan di hapus");
            return;
        }
        
        if(JOptionPane.showConfirmDialog(view, "Anda yakin Akan Menghapus?")==JOptionPane.OK_OPTION){
            Integer idsiswa = Integer.parseInt(view.getjTextField_IdSiswa().getText());
            model.setIdsiswa(idsiswa);
             try{
                
                model.DeleteDataSiswa();
                JOptionPane.showMessageDialog(view, "Data Siswa Berhasil Di Hapus");
                model.resetDataSiswa();
            }catch (Throwable throwable){
                
                JOptionPane.showMessageDialog(view, new Object[]{"Terjadi Eror di database dengan pesan", throwable.getMessage()});
                
            }
        }
       
        
    }
    
}
