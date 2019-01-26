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
public class DataSiswaException extends Exception {

    /**
     * Creates a new instance of <code>DataSiswaException</code> without detail
     * message.
     */
    public DataSiswaException() {
    }

    /**
     * Constructs an instance of <code>DataSiswaException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DataSiswaException(String msg) {
        super(msg);
    }
}
