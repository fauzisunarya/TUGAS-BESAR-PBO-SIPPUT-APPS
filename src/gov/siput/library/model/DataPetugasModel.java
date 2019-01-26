/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.model;

import gov.siput.library.database.siputDatabase;
import gov.siput.library.entity.DataPetugas;
import gov.siput.library.error.DataPetugasException;
import gov.siput.library.event.DataPetugasListener;
import gov.siput.library.service.DataPetugasDao;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class DataPetugasModel {
    
    public Integer idpetugas;
    public String kodepetugas;
    public String namapetugas;
    public String jeniskelaminpetugas;
    public String tempatlahirpetugas;
    public String tanggallahirpetugas;
    public String alamatpetugas;
    public String nohppetugas;
    
    private DataPetugasListener listener;

    public Integer getIdpetugas() {
        return idpetugas;
    }

    public void setIdpetugas(Integer idpetugas) {
        this.idpetugas = idpetugas;
    }

    public String getKodepetugas() {
        return kodepetugas;
    }

    public void setKodepetugas(String kodepetugas) {
        this.kodepetugas = kodepetugas;
    }

    public String getNamapetugas() {
        return namapetugas;
    }

    public void setNamapetugas(String namapetugas) {
        this.namapetugas = namapetugas;
    }

    public String getJeniskelaminpetugas() {
        return jeniskelaminpetugas;
    }

    public void setJeniskelaminpetugas(String jeniskelaminpetugas) {
        this.jeniskelaminpetugas = jeniskelaminpetugas;
    }

    public String getTempatlahirpetugas() {
        return tempatlahirpetugas;
    }

    public void setTempatlahirpetugas(String tempatlahirpetugas) {
        this.tempatlahirpetugas = tempatlahirpetugas;
    }

    public String getTanggallahirpetugas() {
        return tanggallahirpetugas;
    }

    public void setTanggallahirpetugas(String tanggallahirpetugas) {
        this.tanggallahirpetugas = tanggallahirpetugas;
    }

    public String getAlamatpetugas() {
        return alamatpetugas;
    }

    public void setAlamatpetugas(String alamatpetugas) {
        this.alamatpetugas = alamatpetugas;
    }

    public String getNohppetugas() {
        return nohppetugas;
    }

    public void setNohppetugas(String nohppetugas) {
        this.nohppetugas = nohppetugas;
    }

    public DataPetugasListener getListener() {
        return listener;
    }

    public void setListener(DataPetugasListener listener) {
        this.listener = listener;
    }
    
    protected void fireOnInsert(DataPetugas datapetugas){
        if(listener!=null){
            listener.onInsertPetugas(datapetugas);
        }
        
    }
    
     protected void fireOnUpdate(DataPetugas dataPetugas){
        if(listener!=null){
            listener.onUpdatePetugas(dataPetugas);
        }
        
    }
    
    protected void fireOnDelete(){
        if(listener!=null){
            listener.onDeletePetugas();
        }
       
    }
    
        public void InsertDataPetugas() throws SQLException, DataPetugasException{
        DataPetugasDao dao = siputDatabase.getDataPetugasDao();
        DataPetugas datapetugas  = new DataPetugas();
               datapetugas.setKodepetugas(kodepetugas);
               datapetugas.setNamapetugas(namapetugas);
               datapetugas.setJeniskelaminpetugas(jeniskelaminpetugas);
               datapetugas.setTempatlahirpetugas(tempatlahirpetugas);
               datapetugas.setTanggallahirpetugas(tanggallahirpetugas);
               datapetugas.setAlamatpetugas(alamatpetugas);
               datapetugas.setNohppetugas(nohppetugas);
        dao.InsertDataPetugas(datapetugas);
        fireOnInsert(datapetugas);
        
    }
        
        public void UpdateDataPetugas() throws SQLException, DataPetugasException{
        DataPetugasDao dao = siputDatabase.getDataPetugasDao();
        DataPetugas datapetugas  = new DataPetugas();
               datapetugas.setKodepetugas(kodepetugas);
               datapetugas.setNamapetugas(namapetugas);
               datapetugas.setJeniskelaminpetugas(jeniskelaminpetugas);
               datapetugas.setTempatlahirpetugas(tempatlahirpetugas);
               datapetugas.setTanggallahirpetugas(tanggallahirpetugas);
               datapetugas.setAlamatpetugas(alamatpetugas);
               datapetugas.setNohppetugas(nohppetugas);
               datapetugas.setIdpetugas(idpetugas);
        dao.UpdateDataPetugas(datapetugas);
        fireOnUpdate(datapetugas);
        
    }
    
    public void DeleteDataPetugas() throws SQLException, DataPetugasException{
        DataPetugasDao dao = siputDatabase.getDataPetugasDao();
        dao.DeleteDataPetugas(idpetugas);
        fireOnDelete();
        
    }
    
    public void resetDataPetugas(){
        setIdpetugas(0);
        setKodepetugas("");
        setNamapetugas("");
        setJeniskelaminpetugas("");
        setTempatlahirpetugas("");
        setTanggallahirpetugas("");
        setAlamatpetugas("");
        setNohppetugas("");
    }
    
    
}
