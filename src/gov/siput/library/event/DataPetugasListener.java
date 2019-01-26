/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.event;

import gov.siput.library.entity.DataPetugas;
import gov.siput.library.model.DataPetugasModel;

/**
 *
 * @author Administrator
 */
public interface DataPetugasListener {
    
    public void onChangePetugas (DataPetugasModel datapetugas);
    
    public void onInsertPetugas (DataPetugas datapetugas);
    
    public void onDeletePetugas();
    
    public void onUpdatePetugas (DataPetugas datapetugas);
    
    
}
