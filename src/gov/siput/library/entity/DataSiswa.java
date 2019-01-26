/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.entity;

import java.util.Objects;

/**
 *
 * @author Administrator
 */
public class DataSiswa {
    
    public int idsiswa;
    public String nisn;
    public String namasiswa;
    public String kelas;
    public String jeniskelaminsiswa;
    public String tempatlahirsiswa;
    public String tanggallahirsiswa;
    public String alamatsiswa;
    public String nohpsiswa;
    
    public DataSiswa (){
        
    }

    public DataSiswa(int idsiswa, String nisn, String namasiswa, String kelas, String jeniskelaminsiswa, String tempatlahirsiswa, String tanggallahirsiswa, String alamatsiswa, String nohpsiswa) {
        this.idsiswa = idsiswa;
        this.nisn = nisn;
        this.namasiswa = namasiswa;
        this.kelas = kelas;
        this.jeniskelaminsiswa = jeniskelaminsiswa;
        this.tempatlahirsiswa = tempatlahirsiswa;
        this.tanggallahirsiswa = tanggallahirsiswa;
        this.alamatsiswa = alamatsiswa;
        this.nohpsiswa = nohpsiswa;
    }

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.idsiswa;
        hash = 19 * hash + Objects.hashCode(this.nisn);
        hash = 19 * hash + Objects.hashCode(this.namasiswa);
        hash = 19 * hash + Objects.hashCode(this.kelas);
        hash = 19 * hash + Objects.hashCode(this.jeniskelaminsiswa);
        hash = 19 * hash + Objects.hashCode(this.tempatlahirsiswa);
        hash = 19 * hash + Objects.hashCode(this.tanggallahirsiswa);
        hash = 19 * hash + Objects.hashCode(this.alamatsiswa);
        hash = 19 * hash + Objects.hashCode(this.nohpsiswa);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataSiswa other = (DataSiswa) obj;
        if (this.idsiswa != other.idsiswa) {
            return false;
        }
        if (!Objects.equals(this.nisn, other.nisn)) {
            return false;
        }
        if (!Objects.equals(this.namasiswa, other.namasiswa)) {
            return false;
        }
        if (!Objects.equals(this.kelas, other.kelas)) {
            return false;
        }
        if (!Objects.equals(this.jeniskelaminsiswa, other.jeniskelaminsiswa)) {
            return false;
        }
        if (!Objects.equals(this.tempatlahirsiswa, other.tempatlahirsiswa)) {
            return false;
        }
        if (!Objects.equals(this.tanggallahirsiswa, other.tanggallahirsiswa)) {
            return false;
        }
        if (!Objects.equals(this.alamatsiswa, other.alamatsiswa)) {
            return false;
        }
        if (!Objects.equals(this.nohpsiswa, other.nohpsiswa)) {
            return false;
        }
        return true;
    }
    
    
    
    


    
    
    
}
