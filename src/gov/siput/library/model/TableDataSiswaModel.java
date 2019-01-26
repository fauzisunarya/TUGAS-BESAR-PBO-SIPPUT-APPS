/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.model;

import gov.siput.library.entity.DataSiswa;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administrator
 */
public class TableDataSiswaModel extends AbstractTableModel {
    
    private List<DataSiswa> list = new ArrayList<DataSiswa>();

    public void setList(List<DataSiswa> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }
    
    public boolean add(DataSiswa e) {
        try{
            return list.add(e);
        }finally{
            fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
    }
  }

    public DataSiswa get(int index) {
        return list.get(index);
    }

    public DataSiswa set(int index, DataSiswa element) {
        try{
            return list.set(index, element); 
        }finally{
            fireTableRowsUpdated(index, index);        
        }      
    }

    public DataSiswa remove(int index) {
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
                return "NISN";
            case 2 :
                return "NAMA";
            case 3 :
                return "KELAS";
            case 4 :
                return "JENIS KELAMIN"; 
            case 5 :
                return "TEMPAT LAHIR";
            case 6 :
                return "TANGGAL LAHIR";
            case 7 :
                return "ALAMAT";
            case 8 :
                return "N0 HP";
            default :
                return null;
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       switch(columnIndex){
            case 0 : 
                return list.get(rowIndex).getIdsiswa();
            case 1 : 
                return list.get(rowIndex).getNisn();
            case 2 : 
                return list.get(rowIndex).getNamasiswa();
            case 3 : 
                return list.get(rowIndex).getKelas();
            case 4 : 
                return list.get(rowIndex).getJeniskelaminsiswa();
            case 5 :
                return list.get(rowIndex).getTempatlahirsiswa();
            case 6 :
                return list.get(rowIndex).getTanggallahirsiswa();
            case 7 :
                return list.get(rowIndex).getAlamatsiswa();
            case 8 :
                return list.get(rowIndex).getNohpsiswa();
            default:
                return null;
        }
    }
    
}
