/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.event;

import gov.siput.library.entity.DataBuku;
import gov.siput.library.model.DataBukuModel;



/**
 *
 * @author Administrator
 */
public interface DataBukuListener {
    
    public void onChangeBuku(DataBukuModel databuku);
    
    public void OnInsertBuku(DataBuku databuku);
    
    public void onDeleteBuku();
    
    public void onUpdateBuku(DataBuku databuku);
    
}
