/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author douglas
 */
public class DBLocator {
    
    static{
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());			
	}catch(SQLException e){
            System.err.println("Erro ao registrar Driver JDBC MySql. Causa: " + e.getMessage());
	}		
    }
	
    public static java.sql.Connection getConnection() throws SQLException{
        Login login = new Login();
	Connection conn; 
	conn = DriverManager.getConnection(login.getUrl(), login.getUser(), login.getPassword());
	conn.setAutoCommit(false);
	return conn;		
    }
}
