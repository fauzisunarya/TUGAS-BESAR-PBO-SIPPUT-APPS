/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.model;

import gov.siput.library.database.siputDatabase;
import gov.siput.library.entity.DataBuku;
import gov.siput.library.error.DataBukuException;
import gov.siput.library.event.DataBukuListener;
import gov.siput.library.service.DataBukuDao;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class DataBukuModel {
    public Integer id;
    public String kodebuku;
    public String judulbuku;
    public String pengarang;
    public String tahunterbit;
    public String penerbit;
    public String jumlahbuku;
    public String koderak;
    
    private DataBukuListener listener;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKodebuku() {
        return kodebuku;
    }

    public void setKodebuku(String kodebuku) {
        this.kodebuku = kodebuku;
    }

    public String getJudulbuku() {
        return judulbuku;
    }

    public void setJudulbuku(String judulbuku) {
        this.judulbuku = judulbuku;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getTahunterbit() {
        return tahunterbit;
    }

    public void setTahunterbit(String tahunterbit) {
        this.tahunterbit = tahunterbit;
    }

    public String getJumlahbuku() {
        return jumlahbuku;
    }

    public void setJumlahbuku(String jumlahbuku) {
        this.jumlahbuku = jumlahbuku;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getKoderak() {
        return koderak;
    }

    public void setKoderak(String koderak) {
        this.koderak = koderak;
    }

    public DataBukuListener getListener() {
        return listener;
    }

    public void setListener(DataBukuListener listener) {
        this.listener = listener;
    }
    
    protected void fireOnInsert(DataBuku databuku){
        if(listener!=null){
            listener.OnInsertBuku(databuku);
        }
        
    }
    
     protected void fireOnUpdate(DataBuku databuku){
        if(listener!=null){
            listener.onUpdateBuku(databuku);
        }
        
    }
    
    protected void fireOnDelete(){
        if(listener!=null){
            listener.onDeleteBuku();
        }
       
    }
    
        public void InsertDataBuku() throws SQLException, DataBukuException{
        DataBukuDao dao = siputDatabase.getDataBukuDao();
        DataBuku databuku  = new DataBuku();
               databuku.setKodebuku(kodebuku);
               databuku.setJudulbuku(judulbuku);
               databuku.setPengarang(pengarang);
               databuku.setTahunterbit(tahunterbit);
               databuku.setPenerbit(penerbit);
               databuku.setJumlahbuku(jumlahbuku);
               databuku.setKoderak(koderak);
        dao.InsertDataBuku(databuku);
        fireOnInsert(databuku);
        
    }
        
        public void UpdateDataBuku() throws SQLException, DataBukuException{
        DataBukuDao dao = siputDatabase.getDataBukuDao();
        DataBuku databuku  = new DataBuku();
               databuku.setKodebuku(kodebuku);
               databuku.setJudulbuku(judulbuku);
               databuku.setPengarang(pengarang);
               databuku.setTahunterbit(tahunterbit);
               databuku.setPenerbit(penerbit);
               databuku.setJumlahbuku(jumlahbuku);
               databuku.setKoderak(koderak);
               databuku.setId(id);
        
        dao.updateDataBuku(databuku);
        fireOnUpdate(databuku);
        
    }
    
    public void DeleteDataBuku() throws SQLException, DataBukuException{
        DataBukuDao dao = siputDatabase.getDataBukuDao();
        dao.deleteDataBuku(id);
        fireOnDelete();
        
    }
    
    public void resetDataBuku(){
        setId(0);
        setKodebuku("");
        setJudulbuku("");
        setPengarang("");
        setTahunterbit("");
        setPenerbit("");
        setJumlahbuku("");
        setKoderak("");

    }
    
}
