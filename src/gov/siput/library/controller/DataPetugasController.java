/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.controller;

import gov.siput.library.model.DataPetugasModel;
import gov.siput.library.view.DashboardMainView;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class DataPetugasController {
    
    private DataPetugasModel model;

    public void setModel(DataPetugasModel model) {
        this.model = model;
    }
    
    public void resetDataPetugas (DashboardMainView view){
        model.resetDataPetugas();
    }
    
    public void insertDataPetugas(DashboardMainView view){
        String kodepetugas = view.getjTextField_KodePetugas().getText();
        String namapetugas = view.getjTextField_NamaPetugas().getText();
        String jeniskelaminpetugas = view.getjTextField_JenisKelaminPetugas().getText();
        String tempatlahirpetugas = view.getjTextField_TempatLahirPetugas().getText();
        String tanggallahirpetugas = view.getjTextField_TanggalLahirPetugas().getText();
        String alamatpetugas = view.getjTextField_AlamatPetugas().getText();
        String nohopetugas = view.getjTextField_NoHpPetugas().getText();
        
        if(kodepetugas.trim().equals("")){
            JOptionPane.showMessageDialog(view, "Kode Petugas Tidak Boleh Kosong");
        }else if(kodepetugas.length()>10){
            JOptionPane.showMessageDialog(view, "Kode Petugas Tidak Boleh lebih dari 10 karakter");
        }else{
            model.setKodepetugas(kodepetugas);
            model.setNamapetugas(namapetugas);
            model.setJeniskelaminpetugas(jeniskelaminpetugas);
            model.setTempatlahirpetugas(tempatlahirpetugas);
            model.setTanggallahirpetugas(tanggallahirpetugas);
            model.setAlamatpetugas(alamatpetugas);
            model.setNohppetugas(nohopetugas);
            
            try{  
                model.InsertDataPetugas();
                model.resetDataPetugas();
                JOptionPane.showMessageDialog(view, "Data Petugas Berhasil ditambahkan");
                
            }catch (Throwable throwable){
                
                JOptionPane.showMessageDialog(view, new Object[]{"Terjadi Eror di database dengan pesan", throwable.getMessage()});
                
            }
        }
        
    }
        
        public void updateDataPetugas(DashboardMainView view){
        
        if(view.getjTable_DataPetugas1().getSelectedRowCount()==0){
            JOptionPane.showMessageDialog(view, "Silahkan seleksi baris data yang akan di ubah");
            return;
        }
        Integer idpetugas = Integer.parseInt(view.getjTextField_IdPetugas().getText());
        String kodepetugas = view.getjTextField_KodePetugas().getText();
        String namapetugas = view.getjTextField_NamaPetugas().getText();
        String jeniskelaminpetugas = view.getjTextField_JenisKelaminPetugas().getText();
        String tempatlahirpetugas = view.getjTextField_TempatLahirPetugas().getText();
        String tanggallahirpetugas = view.getjTextField_TanggalLahirPetugas().getText();
        String alamatpetugas = view.getjTextField_AlamatPetugas().getText();
        String nohopetugas = view.getjTextField_NoHpPetugas().getText();
        
        if(kodepetugas.trim().equals("")){
            JOptionPane.showMessageDialog(view, "Kode Petugas Tidak Boleh Kosong");
        }else if(kodepetugas.length()>10){
            JOptionPane.showMessageDialog(view, "Kode Petugas Tidak Boleh lebih dari 10 karakter");
        }else{
            model.setIdpetugas(idpetugas);
            model.setKodepetugas(kodepetugas);
            model.setNamapetugas(namapetugas);
            model.setJeniskelaminpetugas(jeniskelaminpetugas);
            model.setTempatlahirpetugas(tempatlahirpetugas);
            model.setTanggallahirpetugas(tanggallahirpetugas);
            model.setAlamatpetugas(alamatpetugas);
            model.setNohppetugas(nohopetugas);
            
            try{
                
                model.UpdateDataPetugas();
                JOptionPane.showMessageDialog(view, "Data Petugas Berhasil Di Ubah");
                model.resetDataPetugas();
            }catch (Throwable throwable){
                
                JOptionPane.showMessageDialog(view, new Object[]{"Terjadi Eror di database dengan pesan", throwable.getMessage()});
                
            }
        }
        
        
    }
            
    public void deleteDataPetugas(DashboardMainView view){
        
        if(view.getjTable_DataPetugas1().getSelectedRowCount()==0){
            JOptionPane.showMessageDialog(view, "Silahkan seleksi baris data yang akan di hapus");
            return;
        }
        
        if(JOptionPane.showConfirmDialog(view, "Anda yakin Akan Menghapus?")==JOptionPane.OK_OPTION){
            Integer idpetugas = Integer.parseInt(view.getjTextField_IdPetugas().getText());
            model.setIdpetugas(idpetugas);
             try{
                
                model.DeleteDataPetugas();
                JOptionPane.showMessageDialog(view, "Data Petugas Berhasil Di Hapus");
                model.resetDataPetugas();
            }catch (Throwable throwable){
                
                JOptionPane.showMessageDialog(view, new Object[]{"Terjadi Eror di database dengan pesan", throwable.getMessage()});
                
            }
        }
       
        
    }
    
}
