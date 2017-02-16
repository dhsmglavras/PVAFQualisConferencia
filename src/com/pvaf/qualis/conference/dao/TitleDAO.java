/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.dao;

import com.pvaf.qualis.conference.entidades.Title;
import com.pvaf.qualis.conference.exceptions.ErrorException;
import com.pvaf.qualis.conference.service.DBLocator;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


/**
 *
 * @author marte
 */
public class TitleDAO {
    
    private final static Logger log = Logger.getLogger(TitleDAO.class);
    
    public static ArrayList<String> getAtributoTitles(int idPubVenue) throws ErrorException {
        ArrayList<String> listT = new ArrayList<>();
        int i=1;
        
        try(Connection conn = DBLocator.getConnection(); 
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM title WHERE id_pub_venue = ?")){
            ps.setInt(i++,idPubVenue);
            
            try (ResultSet rs = ps.executeQuery()) {
                String title;
                while(rs.next()){
                    title = rs.getString("title");
                    byte [] array = title.getBytes("UTF-8");
                    String str = new String(array,"UTF-8");
                    listT.add(str);
                }
            }
            
	} catch (SQLException e) {
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        } catch (UnsupportedEncodingException u) {
            log.error("Ocorreu uma exceção de codificação de caracteres.", u.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        }
	return listT;
    }

    public static ArrayList<String> getAllAtributoTitles() throws ErrorException {
        ArrayList<String> listT = new ArrayList<>();
        int i=1;
        
        try(Connection conn = DBLocator.getConnection(); 
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM title")){
                        
            try (ResultSet rs = ps.executeQuery()) {
                String title;
                while(rs.next()){
                    title = rs.getString("title");
                    byte [] array = title.getBytes("UTF-8");
                    String str = new String(array,"UTF-8");
                    listT.add(str);
                }
            }
            
	} catch (SQLException e) {
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        } catch (UnsupportedEncodingException u) {
            log.error("Ocorreu uma exceção de codificação de caracteres.", u.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        }
	return listT;
    }
    
    public static List<Title> getTitles(int idPubVenue) throws ErrorException{
        List<Title> listT = new ArrayList<>();
        int i=1;
        
        try(Connection conn = DBLocator.getConnection(); 
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM title WHERE id_pub_venue = ?")){
            ps.setInt(i++,idPubVenue);
            
            try (ResultSet rs = ps.executeQuery()) {
                Title title;
                while(rs.next()){
                    int id = rs.getInt("id_pub_venue");
                    String strTitle = rs.getString("title");
                    byte [] array = strTitle.getBytes("UTF-8");
                    strTitle = new String(array,"UTF-8");
                    title = new Title(id,strTitle);
                    listT.add(title);
                }
            } catch (UnsupportedEncodingException u) {
                log.error("Ocorreu uma exceção de codificação de caracteres.", u.fillInStackTrace());
                throw new ErrorException("Ocorreu um Erro Interno");
            }
            
	} catch (SQLException e) {
                log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
                throw new ErrorException("Ocorreu um Erro Interno");
        }
	return listT;
    }
    
    public static ArrayList<Title> getAllTitles() throws ErrorException {
        ArrayList<Title> listT = new ArrayList<>();
        
        try(Connection conn = DBLocator.getConnection(); 
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM title")){
                        
            try (ResultSet rs = ps.executeQuery()) {
                Title title;
                while(rs.next()){
                    int id = rs.getInt("id_pub_venue");
                    String strTitle = rs.getString("title");
                    byte [] array = strTitle.getBytes("UTF-8");
                    strTitle = new String(array,"UTF-8");
                    title = new Title(id,strTitle);
                    listT.add(title);
                }
            } catch (UnsupportedEncodingException u) {
                log.error("Ocorreu uma exceção de codificação de caracteres.", u.fillInStackTrace());
                throw new ErrorException("Ocorreu um Erro Interno");
            }

        } catch (SQLException e) {
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        }
	return listT;
    }
    
    public static Integer checkIdPubVenue(Integer idPubVenue, String journalTitle) throws ErrorException{
        
        int idPubVenueAux = 0;
        try(Connection conn = DBLocator.getConnection()){ 
            
            PreparedStatement ps = conn.prepareStatement("SELECT id_pub_venue FROM title WHERE id_pub_venue = ? AND title = ?");
            
            int i = 1;
            ps.setInt(i++, idPubVenue);
            ps.setString(i++, journalTitle);
            
            ResultSet title = ps.executeQuery();
            if (title.first()) {
                idPubVenueAux = title.getInt("id_pub_venue");
            }
            title.close();
            ps.close();
            
        } catch (SQLException e) {
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        }
        return idPubVenueAux;
    }
}
