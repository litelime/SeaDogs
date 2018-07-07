/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author syntel
 */
public class IllegalExpiryDateException extends Exception {

    public IllegalExpiryDateException() {
        System.out.println("The credit card expiry date provided is already expired");
    }
    
}
