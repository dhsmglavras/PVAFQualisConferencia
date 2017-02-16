/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.service;

import com.pvaf.qualis.conference.exceptions.ErrorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author douglas
 */
public class DBLocator {
    
    private final static Logger log = Logger.getLogger(DBLocator.class);
    
    static{
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());			
	}catch(Exception e){
            log.error("Erro ao registrar Driver JDBC MySql.", e.fillInStackTrace());
	}		
    }
	
    public static java.sql.Connection getConnection() throws ErrorException{
        try {
            Login login = new Login();
            Connection conn;
            conn = DriverManager.getConnection(login.getUrl(), login.getUser(), login.getPassword());
            conn.setAutoCommit(false);		
            return conn;
        } catch (SQLException e) {
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        }
    }
}
