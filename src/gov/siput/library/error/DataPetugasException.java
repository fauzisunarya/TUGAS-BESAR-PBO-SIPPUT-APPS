/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.error;

/**
 *
 * @author Administrator
 */
public class DataPetugasException extends Exception {

    /**
     * Creates a new instance of <code>DataPetugasException</code> without
     * detail message.
     */
    public DataPetugasException() {
    }

    /**
     * Constructs an instance of <code>DataPetugasException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DataPetugasException(String msg) {
        super(msg);
    }
}
