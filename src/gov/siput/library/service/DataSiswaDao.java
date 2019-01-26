/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.service;


import gov.siput.library.entity.DataSiswa;
import gov.siput.library.error.DataSiswaException;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface DataSiswaDao {
    
    public void InsertDataSiswa (DataSiswa datasiswa) throws DataSiswaException;
    public void updateDataSiswa (DataSiswa datasiswa) throws DataSiswaException;
    public void deleteDataSiswa (Integer id) throws DataSiswaException;
    public DataSiswa getDataSiswa(Integer id) throws DataSiswaException;
    public DataSiswa getDataSiswa(String nisn) throws DataSiswaException;
    public List<DataSiswa> selectAllDataSiswa() throws DataSiswaException;
    
}
