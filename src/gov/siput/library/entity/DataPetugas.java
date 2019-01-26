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
public class DataPetugas {
    
    public Integer idpetugas;
    public String kodepetugas;
    public String namapetugas;
    public String jeniskelaminpetugas;
    public String tempatlahirpetugas;
    public String tanggallahirpetugas;
    public String alamatpetugas;
    public String nohppetugas;
    
    public DataPetugas (){
        
    }

    public DataPetugas(Integer idpetugas, String kodepetugas, String namapetugas, String jeniskelaminpetugas, String tempatlahirpetugas, String tanggallahirpetugas, String alamatpetugas, String nohppetugas) {
        this.idpetugas = idpetugas;
        this.kodepetugas = kodepetugas;
        this.namapetugas = namapetugas;
        this.jeniskelaminpetugas = jeniskelaminpetugas;
        this.tempatlahirpetugas = tempatlahirpetugas;
        this.tanggallahirpetugas = tanggallahirpetugas;
        this.alamatpetugas = alamatpetugas;
        this.nohppetugas = nohppetugas;
    }

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.idpetugas);
        hash = 11 * hash + Objects.hashCode(this.kodepetugas);
        hash = 11 * hash + Objects.hashCode(this.namapetugas);
        hash = 11 * hash + Objects.hashCode(this.jeniskelaminpetugas);
        hash = 11 * hash + Objects.hashCode(this.tempatlahirpetugas);
        hash = 11 * hash + Objects.hashCode(this.tanggallahirpetugas);
        hash = 11 * hash + Objects.hashCode(this.alamatpetugas);
        hash = 11 * hash + Objects.hashCode(this.nohppetugas);
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
        final DataPetugas other = (DataPetugas) obj;
        if (!Objects.equals(this.kodepetugas, other.kodepetugas)) {
            return false;
        }
        if (!Objects.equals(this.namapetugas, other.namapetugas)) {
            return false;
        }
        if (!Objects.equals(this.jeniskelaminpetugas, other.jeniskelaminpetugas)) {
            return false;
        }
        if (!Objects.equals(this.tempatlahirpetugas, other.tempatlahirpetugas)) {
            return false;
        }
        if (!Objects.equals(this.tanggallahirpetugas, other.tanggallahirpetugas)) {
            return false;
        }
        if (!Objects.equals(this.alamatpetugas, other.alamatpetugas)) {
            return false;
        }
        if (!Objects.equals(this.nohppetugas, other.nohppetugas)) {
            return false;
        }
        if (!Objects.equals(this.idpetugas, other.idpetugas)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
