/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.model;

import gov.siput.library.entity.DataPetugas;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administrator
 */
public class TableDataPetugasModel extends AbstractTableModel{
    
    private List<DataPetugas> list = new ArrayList<DataPetugas>();

    public void setList(List<DataPetugas> list) {
        this.list = list;
    }
    
    

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }
    
    public boolean add(DataPetugas e) {
        try{
            return list.add(e);
        }finally{
            fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
    }
  }

    public DataPetugas get(int index) {
        return list.get(index);
    }

    public DataPetugas set(int index, DataPetugas element) {
        try{
            return list.set(index, element); 
        }finally{
            fireTableRowsUpdated(index, index);        
        }      
    }

    public DataPetugas remove(int index) {
        try{
            return list.remove(index);
        }finally{
            fireTableRowsDeleted(index, index);    
        }   
    }

    
    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0 :
                return "ID";
            case 1 :
                return "KODE PETUGAS";
            case 2 :
                return "NAMA";
            case 3 :
                return "JENIS KELAMIN";
            case 4 :
                return "TEMPAT LAHIR"; 
            case 5 :
                return "TANGGAL LAHIR";
            case 6 :
                return "ALAMAT";
            case 7 :
                return "NO HP";
            default :
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0 : 
                return list.get(rowIndex).getIdpetugas();
            case 1 : 
                return list.get(rowIndex).getKodepetugas();
            case 2 : 
                return list.get(rowIndex).getNamapetugas();
            case 3 : 
                return list.get(rowIndex).getJeniskelaminpetugas();
            case 4 : 
                return list.get(rowIndex).getTempatlahirpetugas();
            case 5 :
                return list.get(rowIndex).getTanggallahirpetugas();
            case 6 :
                return list.get(rowIndex).getAlamatpetugas();
            case 7 :
                return list.get(rowIndex).getNohppetugas();
            default:
                return null;
    }
        
    }
    
}
