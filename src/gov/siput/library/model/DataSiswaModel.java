/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.model;

import gov.siput.library.database.siputDatabase;
import gov.siput.library.entity.DataSiswa;
import gov.siput.library.error.DataSiswaException;
import gov.siput.library.event.DataSiswaListener;
import gov.siput.library.service.DataSiswaDao;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class DataSiswaModel {
    
    public int idsiswa;
    public String nisn;
    public String namasiswa;
    public String kelas;
    public String jeniskelaminsiswa;
    public String tempatlahirsiswa;
    public String tanggallahirsiswa;
    public String alamatsiswa;
    public String nohpsiswa;
    
    private DataSiswaListener listener;

    public int getIdsiswa() {
        return idsiswa;
    }

    public void setIdsiswa(int idsiswa) {
        this.idsiswa = idsiswa;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public String getNamasiswa() {
        return namasiswa;
    }

    public void setNamasiswa(String namasiswa) {
        this.namasiswa = namasiswa;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getJeniskelaminsiswa() {
        return jeniskelaminsiswa;
    }

    public void setJeniskelaminsiswa(String jeniskelaminsiswa) {
        this.jeniskelaminsiswa = jeniskelaminsiswa;
    }

    public String getTempatlahirsiswa() {
        return tempatlahirsiswa;
    }

    public void setTempatlahirsiswa(String tempatlahirsiswa) {
        this.tempatlahirsiswa = tempatlahirsiswa;
    }

    public String getTanggallahirsiswa() {
        return tanggallahirsiswa;
    }

    public void setTanggallahirsiswa(String tanggallahirsiswa) {
        this.tanggallahirsiswa = tanggallahirsiswa;
    }

    public String getAlamatsiswa() {
        return alamatsiswa;
    }

    public void setAlamatsiswa(String alamatsiswa) {
        this.alamatsiswa = alamatsiswa;
    }

    public String getNohpsiswa() {
        return nohpsiswa;
    }

    public void setNohpsiswa(String nohpsiswa) {
        this.nohpsiswa = nohpsiswa;
    }
    
    


    public DataSiswaListener getListener() {
        return listener;
    }

    public void setListener(DataSiswaListener listener) {
        this.listener = listener;
    }
    
        protected void fireOnInsert(DataSiswa datasiswa){
        if(listener!=null){
            listener.OnInsertSiswa(datasiswa);
        }
        
    }
    
     protected void fireOnUpdate(DataSiswa datasiswa){
        if(listener!=null){
            listener.onUpdateSiswa(datasiswa);
        }
        
    }
    
    protected void fireOnDelete(){
        if(listener!=null){
            listener.onDeleteSiswa();
        }
       
    }
    
        public void InsertDataSiswa() throws SQLException, DataSiswaException{
        DataSiswaDao dao = siputDatabase.getDataSiswaDao();
        DataSiswa datasiswa  = new DataSiswa();
               datasiswa.setNisn(nisn);
               datasiswa.setNamasiswa(namasiswa);
               datasiswa.setKelas(kelas);
               datasiswa.setJeniskelaminsiswa(jeniskelaminsiswa);
               datasiswa.setTempatlahirsiswa(tempatlahirsiswa);
               datasiswa.setTanggallahirsiswa(tanggallahirsiswa);
               datasiswa.setAlamatsiswa(alamatsiswa);
               datasiswa.setNohpsiswa(nohpsiswa);
        dao.InsertDataSiswa(datasiswa);
        fireOnInsert(datasiswa);
        
    }
        
        public void UpdateDataSiswa() throws SQLException, DataSiswaException{
        DataSiswaDao dao = siputDatabase.getDataSiswaDao();
        DataSiswa datasiswa  = new DataSiswa();
               datasiswa.setNisn(nisn);
               datasiswa.setNamasiswa(namasiswa);
               datasiswa.setKelas(kelas);
               datasiswa.setJeniskelaminsiswa(jeniskelaminsiswa);
               datasiswa.setTempatlahirsiswa(tempatlahirsiswa);
               datasiswa.setTanggallahirsiswa(tanggallahirsiswa);
               datasiswa.setAlamatsiswa(alamatsiswa);
               datasiswa.setNohpsiswa(nohpsiswa);
               datasiswa.setIdsiswa(idsiswa);
        
        dao.updateDataSiswa(datasiswa);
        fireOnUpdate(datasiswa);
        
    }
    
    public void DeleteDataSiswa() throws SQLException, DataSiswaException{
        DataSiswaDao dao = siputDatabase.getDataSiswaDao();
        dao.deleteDataSiswa(idsiswa);
        fireOnDelete();
        
    }
    
    public void resetDataSiswa(){
        setIdsiswa(0);
        setNisn("");
        setNamasiswa("");
        setKelas("");
        setJeniskelaminsiswa("");
        setTempatlahirsiswa("");
        setTanggallahirsiswa("");
        setAlamatsiswa("");
        setNohpsiswa("");

    }
    
    
}
