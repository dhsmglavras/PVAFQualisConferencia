/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.dao;

import com.pvaf.qualis.conference.entidades.Acronym;
import com.pvaf.qualis.conference.exceptions.ErrorException;
import com.pvaf.qualis.conference.service.DBLocator;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import org.apache.log4j.Logger;


/**
 *
 * @author douglas
 */
public class AcronymDAO {
    
    private final static Logger log = Logger.getLogger(AcronymDAO.class);
    
    public static HashSet<Acronym> acronymDB() throws ErrorException {
        HashSet<Acronym>  acronyms = new HashSet<>();
        Connection conn = null;
        
        try{
            conn = DBLocator.getConnection();         
            Statement statement = conn.createStatement();
            
            ResultSet publicationvenue = statement.executeQuery("select * from publicationvenue");
            ResultSet acronymRs;
            int idPubVenue;
            
            publicationvenue.first();
            
            do{
                // In the past the id starts at 0, but now at 1. So the less 1 in the calculation is for compatibility.
                idPubVenue = publicationvenue.getInt("id_pub_venue");
                //publication.setId(idPubVenue - 1);               
                        
                String pub_type = publicationvenue.getString("pub_type");                               
                if (pub_type.equals("C") || pub_type.equals("W")){
                    
                    try{
                        statement = conn.createStatement();
                        acronymRs = statement.executeQuery("select * from acronym where id_pub_venue = " + idPubVenue + " order by acronym");
                        //acronymRs.first();
                    
                        while(acronymRs.next()){
                            
                            String sigla = acronymRs.getString("acronym");
                            try {
                                byte array[] = sigla.getBytes("UTF-8");
                                sigla = new String(array,"UTF-8");
                            } catch (UnsupportedEncodingException u) {
                                log.error("Ocorreu uma exceção de codificação de caracteres.", u.fillInStackTrace());
                                throw new ErrorException("Ocorreu um Erro Interno");
                            }
                                    
                            Acronym acronym = new Acronym(acronymRs.getInt("id_pub_venue"),sigla);
                            acronyms.add(acronym);
                        }
                    } catch (SQLException sePublication) {
                        log.error("Ocorreu uma exceção de SQL.", sePublication.fillInStackTrace());
                        throw new ErrorException("Ocorreu um Erro Interno");
                    }
                }
                
            }while(publicationvenue.next());            
        } catch (SQLException e) {
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        }finally{
            if(conn !=null){
		try{
                    conn.close();
                }catch (SQLException e) {
                    log.error("Exceção ao fechar a conexão.", e.fillInStackTrace());
                    throw new ErrorException("Ocorreu um Erro Interno");
                }
            }
	}
        return acronyms;
    }
    
    public static ArrayList<String> getAcronyms(int idPubVenue) throws ErrorException{
        ArrayList<String> listA = new ArrayList<>();
        int i=1;
        
        try(Connection conn = DBLocator.getConnection(); 
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM acronym WHERE id_pub_venue = ?")){
            ps.setInt(i++,idPubVenue);
            
            try (ResultSet rs = ps.executeQuery()) {
                
                while(rs.next()){
                    
                    String strAcronym = rs.getString("acronym");
                    byte [] array = strAcronym.getBytes("UTF-8");
                    strAcronym = new String(array,"UTF-8");
                    
                    listA.add(strAcronym);
                }
                
            } catch (UnsupportedEncodingException u) {
                log.error("Ocorreu uma exceção de codificação de caracteres.", u.fillInStackTrace());
                throw new ErrorException("Ocorreu um Erro Interno");
            }
            
	} catch (SQLException e) {
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        }
        
	return listA;
    }
}