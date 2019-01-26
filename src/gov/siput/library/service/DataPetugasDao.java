/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.service;

import gov.siput.library.entity.DataPetugas;
import gov.siput.library.error.DataPetugasException;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface DataPetugasDao {
    
    public void InsertDataPetugas (DataPetugas datapetugas) throws DataPetugasException;
    public void UpdateDataPetugas (DataPetugas datapetugas) throws DataPetugasException;
    public void DeleteDataPetugas (Integer idpetugas) throws DataPetugasException;
    public DataPetugas getDataPetugas (Integer idpetugas) throws DataPetugasException;
    public DataPetugas getDataPetugas (String kodepetugas) throws DataPetugasException;
    public List<DataPetugas> selectAllDataPetugas() throws DataPetugasException;
    
}
