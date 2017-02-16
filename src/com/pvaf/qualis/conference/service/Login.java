/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.service;

/**
 *
 * @author douglas
 */

import com.pvaf.qualis.conference.exceptions.ErrorException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class Login {
    
    private final static Logger log = Logger.getLogger(Login.class);
	
    private String user;

    private String password;
	
    private String url;
		
    public Login() throws ErrorException{
        try{
            Properties props = new Properties();
            try (FileInputStream file = new FileInputStream("./properties/login.properties")) {
                props.load(file);
                
                this.password = props.getProperty("password").toString().trim();
                this.user = props.getProperty("user").toString().trim();
                this.url = props.getProperty("url").toString().trim();
            }
        } catch (FileNotFoundException e) {
            log.error("Arq. nao existe.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        } catch (IOException e) {
            log.error("Erro de E/S.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        }
    }
	
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }
}

