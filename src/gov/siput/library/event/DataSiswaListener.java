/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.event;

import gov.siput.library.entity.DataSiswa;
import gov.siput.library.model.DataSiswaModel;

/**
 *
 * @author Administrator
 */
public interface DataSiswaListener {
    
    public void onChangeSiswa(DataSiswaModel datasiswa);
    
    public void OnInsertSiswa(DataSiswa datasiswa);
    
    public void onDeleteSiswa();
    
    public void onUpdateSiswa(DataSiswa datasiswa);
    
}
