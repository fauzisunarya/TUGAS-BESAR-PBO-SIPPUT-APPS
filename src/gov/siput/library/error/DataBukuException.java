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
public class DataBukuException extends Exception {

    /**
     * Creates a new instance of <code>DataBukuException</code> without detail
     * message.
     */
    public DataBukuException() {
    }

    /**
     * Constructs an instance of <code>DataBukuException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DataBukuException(String msg) {
        super(msg);
    }
}
