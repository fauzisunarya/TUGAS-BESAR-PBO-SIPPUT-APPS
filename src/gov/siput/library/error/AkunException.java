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
public class AkunException extends Exception {

    /**
     * Creates a new instance of <code>AkunException</code> without detail
     * message.
     */
    public AkunException() {
    }

    /**
     * Constructs an instance of <code>AkunException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public AkunException(String msg) {
        super(msg);
    }
}
