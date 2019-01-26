/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.event;

import gov.siput.library.entity.Akun;
import gov.siput.library.model.AkunModel;

/**
 *
 * @author Administrator
 */
public interface AkunListener {
    
    public void onChange(AkunModel akun);
    
    public void OnInsert(Akun akun);
    
    public void onDelete();
    
    public void onUpdate(Akun akun);
    
}
