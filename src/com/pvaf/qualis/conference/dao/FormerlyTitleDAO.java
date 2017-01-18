/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.dao;

import com.pvaf.qualis.conference.entidades.FormerlyTitle;
import com.pvaf.qualis.conference.service.DBLocator;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author douglas
 */
public class FormerlyTitleDAO {
    
    public static ArrayList<FormerlyTitle> getAllFormerlyTitle(){
       ArrayList<FormerlyTitle> listF = new ArrayList<>();
        
        try(Connection conn = DBLocator.getConnection(); 
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM formerly_title")){
                        
            try (ResultSet rs = ps.executeQuery()) {
                FormerlyTitle formerlytitle=null;
                while(rs.next()){
                    int id = rs.getInt("id_pub_venue");
                    
                    /*String strAcronym = "";
                    if(!rs.getString("acronym").isEmpty()){
                        strAcronym = rs.getString("acronym");
                    }*/
                    
                    String strTitle = rs.getString("title");
                    byte [] array = strTitle.getBytes("UTF-8");
                    strTitle = new String(array,"UTF-8");
                    formerlytitle = new FormerlyTitle(id,strTitle);
                    
                    listF.add(formerlytitle);
                }
                return listF;
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(FormerlyTitleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
	}catch(SQLException e){
            System.err.println("Ocorreu uma exceção de SQL. Causa: " + e.getMessage());
	}
	return null;
    }
    
    public static ArrayList<String> getAcronyms(int idPubVenue){
        ArrayList<String> listA = new ArrayList<>();
        int i=1;
        
        try(Connection conn = DBLocator.getConnection(); 
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM formerly_title WHERE id_pub_venue = ?")){
            ps.setInt(i++,idPubVenue);
            
            try (ResultSet rs = ps.executeQuery()) {
                
                while(rs.next()){
                    
                    String strAcronym = rs.getString("acronym");
                    byte [] array = strAcronym.getBytes("UTF-8");
                    strAcronym = new String(array,"UTF-8");
                    
                    listA.add(strAcronym);
                }
                return listA;
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(AcronymDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
	}catch(SQLException e){
            System.err.println("Ocorreu uma exceção de SQL. Causa: " + e.getMessage());
	}
	return null;
    }
}
