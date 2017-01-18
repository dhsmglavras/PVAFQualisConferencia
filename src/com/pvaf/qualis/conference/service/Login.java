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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Login {
	
    private String user;

    private String password;
	
    private String url;
		
    public Login(){
        try{
            Properties props = new Properties();
            try (FileInputStream file = new FileInputStream("./properties/login.properties")) {
                props.load(file);
                
                this.password = props.getProperty("password").toString().trim();
                this.user = props.getProperty("user").toString().trim();
                this.url = props.getProperty("url").toString().trim();
            }
        }catch(FileNotFoundException e){
            System.err.println();
        }catch(IOException e){
            System.err.println();
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

