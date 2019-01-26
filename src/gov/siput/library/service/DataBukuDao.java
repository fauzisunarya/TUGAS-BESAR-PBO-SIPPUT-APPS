/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.service;


import gov.siput.library.entity.DataBuku;
import gov.siput.library.error.DataBukuException;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface DataBukuDao {
    
    public void InsertDataBuku (DataBuku databuku) throws DataBukuException;
    public void updateDataBuku (DataBuku databuku) throws DataBukuException;
    public void deleteDataBuku (Integer id) throws DataBukuException;
    public DataBuku getDataBuku(Integer id) throws DataBukuException;
    public DataBuku getDataBuku(String kodebuku) throws DataBukuException;
    public List<DataBuku> selectAllDataBuku() throws DataBukuException;
    
}
