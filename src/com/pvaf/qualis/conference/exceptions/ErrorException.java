/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.exceptions;

/**
 *
 * @author douglas
 */
public class ErrorException extends Exception{
    
    private String message;
    
    public ErrorException(String message){
        this.message = message;    
    }
    
    @Override
    public String getMessage(){
        return message;
    }
}
