/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.controller;

import gov.siput.library.model.DataBukuModel;
import gov.siput.library.view.DashboardMainView;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class DataBukuController {
    
    private DataBukuModel model;

    public void setModel(DataBukuModel model) {
        this.model = model;
    }
    
    public void resetDataBuku(DashboardMainView view){
        model.resetDataBuku();
        
    }
    
        public void insertDataBuku(DashboardMainView view){
        String kodebuku = view.getjTextField_KodeBuku().getText();
        String judulbuku = view.getjTextField_JudulBuku().getText();
        String pengarang = view.getjTextField_Pengarang().getText();
        String tahunterbit = view.getjTextField_TahunTerbit().getText();
        String penerbit = view.getjTextField_Penerbit().getText();
        String jumlahbuku = view.getjTextField_JumlahBuku().getText();
        String koderak = view.getjTextField_KodeRak().getText();
        
        if(kodebuku.trim().equals("")){
            JOptionPane.showMessageDialog(view, "Kode Buku Tidak Boleh Kosong");
        }else if(kodebuku.length()>50){
            JOptionPane.showMessageDialog(view, "Kode Buku Tidak Boleh lebih dari 50 karakter");
        }else{
            model.setKodebuku(kodebuku);
            model.setJudulbuku(judulbuku);
            model.setPengarang(pengarang);
            model.setTahunterbit(tahunterbit);
            model.setPenerbit(penerbit);
            model.setJumlahbuku(jumlahbuku);
            model.setKoderak(koderak);
            
            try{
                
                model.InsertDataBuku();
                model.resetDataBuku();
                JOptionPane.showMessageDialog(view, " Data Buku Berhasil ditamabahkan");
                
            }catch (Throwable throwable){
                
                JOptionPane.showMessageDialog(view, new Object[]{"Terjadi Eror di database dengan pesan", throwable.getMessage()});
                
            }
        }
        
    }
        
        public void updateDataBuku(DashboardMainView view){
        
        if(view.getjTable_DataBuku().getSelectedRowCount()==0){
            JOptionPane.showMessageDialog(view, "Silahkan seleksi baris data yang akan di ubah");
            return;
        }
        Integer id = Integer.parseInt(view.getjTextField_ID().getText());
        String kodebuku = view.getjTextField_KodeBuku().getText();
        String judulbuku = view.getjTextField_JudulBuku().getText();
        String pengarang = view.getjTextField_Pengarang().getText();
        String tahunterbit = view.getjTextField_TahunTerbit().getText();
        String penerbit = view.getjTextField_Penerbit().getText();
        String jumlahbuku = view.getjTextField_JumlahBuku().getText();
        String koderak = view.getjTextField_KodeRak().getText();
        
        if(kodebuku.trim().equals("")){
            JOptionPane.showMessageDialog(view, "Kode Buku Tidak Boleh Kosong");
        }else if(kodebuku.length()>50){
            JOptionPane.showMessageDialog(view, "Kode Buku Tidak Boleh lebih dari 50 karakter");
        }else{
            model.setId(id);
            model.setKodebuku(kodebuku);
            model.setJudulbuku(judulbuku);
            model.setPengarang(pengarang);
            model.setTahunterbit(tahunterbit);
            model.setPenerbit(penerbit);
            model.setJumlahbuku(jumlahbuku);
            model.setKoderak(koderak);
            
            try{
                
                model.UpdateDataBuku();
                JOptionPane.showMessageDialog(view, "Data Buku Berhasil Di Ubah");
                model.resetDataBuku();
            }catch (Throwable throwable){
                
                JOptionPane.showMessageDialog(view, new Object[]{"Terjadi Eror di database dengan pesan", throwable.getMessage()});
                
            }
        }
        
        
    }
            
    public void deleteDataBuku(DashboardMainView view){
        
        if(view.getjTable_DataBuku().getSelectedRowCount()==0){
            JOptionPane.showMessageDialog(view, "Silahkan seleksi baris data yang akan di hapus");
            return;
        }
        
        if(JOptionPane.showConfirmDialog(view, "Anda yakin Akan Menghapus?")==JOptionPane.OK_OPTION){
            Integer id = Integer.parseInt(view.getjTextField_ID().getText());
            model.setId(id);
             try{
                
                model.DeleteDataBuku();
                JOptionPane.showMessageDialog(view, "Data Buku Berhasil Di Hapus");
                model.resetDataBuku();
            }catch (Throwable throwable){
                
                JOptionPane.showMessageDialog(view, new Object[]{"Terjadi Eror di database dengan pesan", throwable.getMessage()});
                
            }
        }
       
        
    }
    
}
