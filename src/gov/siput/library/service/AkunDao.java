/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.service;

import gov.siput.library.entity.Akun;
import gov.siput.library.error.AkunException;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AkunDao {
    
    public void InsertAkun (Akun akun) throws AkunException;
    public void updateAkun(Akun akun) throws AkunException;
    public void deleteAKun(Integer id) throws AkunException;
    public Akun getAkun(Integer id) throws AkunException;
    public Akun getAkun(String email) throws AkunException;
    public List<Akun> selectAllAKun() throws AkunException;
    
}
