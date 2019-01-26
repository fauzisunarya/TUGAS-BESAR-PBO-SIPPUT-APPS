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
public class DataBuku {
    public Integer id;
    public String kodebuku;
    public String judulbuku;
    public String pengarang;
    public String tahunterbit;
    public String penerbit;
    public String jumlahbuku;
    public String koderak;
    
    public DataBuku () {
    
}

    public DataBuku(Integer id, String kodebuku, String judulbuku, String pengarang, String tahunterbit, String penerbit, String jumlahbuku, String koderak) {
        this.id = id;
        this.kodebuku = kodebuku;
        this.judulbuku = judulbuku;
        this.pengarang = pengarang;
        this.tahunterbit = tahunterbit;
        this.penerbit = penerbit;
        this.jumlahbuku = jumlahbuku;
        this.koderak = koderak;
    }
    
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.kodebuku);
        hash = 17 * hash + Objects.hashCode(this.judulbuku);
        hash = 17 * hash + Objects.hashCode(this.pengarang);
        hash = 17 * hash + Objects.hashCode(this.tahunterbit);
        hash = 17 * hash + Objects.hashCode(this.penerbit);
        hash = 17 * hash + Objects.hashCode(this.jumlahbuku);
        hash = 17 * hash + Objects.hashCode(this.koderak);
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
        final DataBuku other = (DataBuku) obj;
        if (!Objects.equals(this.kodebuku, other.kodebuku)) {
            return false;
        }
        if (!Objects.equals(this.judulbuku, other.judulbuku)) {
            return false;
        }
        if (!Objects.equals(this.pengarang, other.pengarang)) {
            return false;
        }
        if (!Objects.equals(this.tahunterbit, other.tahunterbit)) {
            return false;
        }
        if (!Objects.equals(this.penerbit, other.penerbit)) {
            return false;
        }
        if (!Objects.equals(this.jumlahbuku, other.jumlahbuku)) {
            return false;
        }
        if (!Objects.equals(this.koderak, other.koderak)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
