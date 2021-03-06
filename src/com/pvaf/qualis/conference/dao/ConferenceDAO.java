/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.dao;

import com.pvaf.qualis.conference.entidades.AreaAvaliacao;
import com.pvaf.qualis.conference.entidades.Conference;
import com.pvaf.qualis.conference.entidades.FormerlyTitle;
import com.pvaf.qualis.conference.entidades.Title;
import com.pvaf.qualis.conference.exceptions.ErrorException;
import com.pvaf.qualis.conference.service.DBLocator;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author douglas
 */
public class ConferenceDAO {
    
    private final static Logger log = Logger.getLogger(ConferenceDAO.class);
    
    private static AreaAvaliacao checkAreaExists(String area) throws ErrorException{
        List<AreaAvaliacao> listA = AreaAvaliacaoDAO.getAllNamesAreaAvaliacao();
        
        for(AreaAvaliacao a: listA){
            if(area.equals(a.getNomeArea())){
                return a; 
            }
        }
        return null;
    }
    
    private static boolean checkAcronymExists(int idPubVenue, Conference conference) throws ErrorException{
        List<String> listA = AcronymDAO.getAcronyms(idPubVenue);
        String acronym = conference.getAcronym();
        acronym = acronym.toLowerCase();
        
        for(String acr: listA){
            String strAcr = acr.toLowerCase();
            
            if(acronym.equals(strAcr)){
                return true; 
            }
        }
        return false;
    }
    
    private static boolean checkAcronymFormerlyTitleExists(int idPubVenue, Conference conference) throws ErrorException{
        List<String> listA = FormerlyTitleDAO.getAcronyms(idPubVenue);
        String acronym = conference.getAcronym();
        acronym = acronym.toLowerCase();
        
        for(String acr: listA){
            String strAcr = acr.toLowerCase();
            
            if(acronym.equals(strAcr)){
                return true; 
            }
        }
        return false;
    }
    
    private static boolean checkFormerlyTitleExists(Conference conference) throws ErrorException{
        List<FormerlyTitle> listF = FormerlyTitleDAO.getAllFormerlyTitle();
        int cont=0;
        boolean test = false;
        for(String title1: conference.getTitles()){
            String t1 = title1.toLowerCase();
                       
            for(FormerlyTitle formerlyTitle: listF){
                String t2 = formerlyTitle.getTitle();
                t2 = t2.toLowerCase();
                        
                if(t1.equals(t2)){
                    if(checkAcronymExists(formerlyTitle.getIdPubVenue(),conference) || checkAcronymFormerlyTitleExists(formerlyTitle.getIdPubVenue(),conference)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private static int getIndice(String estrato){
        String[] estratos = {"C","B5","B4","B3","B2","B1","A2","A1"};
        int indice;
        indice=0;
        for(String s: estratos){
            if (s.equals(estrato)){
                return indice;
            }
            indice++;
        }
        return indice;
    }
    
    public static void insertConference(Conference conference) throws ErrorException{
        Connection conn = null;
        try{
            
            if(checkFormerlyTitleExists(conference)){
                return;                
            }
            
            int i=1;
            conn = DBLocator.getConnection();
            
            // Inserir
            PreparedStatement ps;
            ps = conn.prepareStatement("INSERT INTO publicationvenue (pub_type,publisher) VALUES (?,?)");
            ps.setString(i++,String.valueOf(conference.getPubType()));
            ps.setNull(i++, java.sql.Types.INTEGER);
            ps.executeUpdate();
            ps.close();
            
            //Selecionar
            ps = conn.prepareStatement("SELECT id_pub_venue FROM publicationvenue ORDER BY id_pub_venue");
            int idPubVenue=0;
            try (ResultSet publicationVenue = ps.executeQuery()) {
                if(publicationVenue.last()){
                    idPubVenue = publicationVenue.getInt("id_pub_venue");
                }
            }            
            ps.close();
            
            // Inserir acronym
            i=1;
            ps = conn.prepareStatement("INSERT INTO acronym (id_pub_venue,acronym) VALUES (?,?)");
            ps.setInt(i++,idPubVenue);
            ps.setString(i++, conference.getAcronym());
            ps.executeUpdate();
            ps.close();
            
            // inseri qualis
            int idArea = 0;
            AreaAvaliacao area = checkAreaExists("CIÊNCIA DA COMPUTAÇÃO");
                        
            if(!(area==null)){
                idArea = area.getIdArea();
            }
            
            i=1;
            ps = conn.prepareStatement("INSERT INTO qualis (id_pub_venue,id_area,year,qualis) VALUES (?,?,?,?)");
            ps.setInt(i++,idPubVenue);
            ps.setInt(i++,idArea);
            ps.setBigDecimal(i++, BigDecimal.valueOf(conference.getYear()));
            ps.setString(i++,conference.getClassification());
            ps.executeUpdate();
            ps.close();
            
            // Inserir titulos
            for (String t : conference.getTitles()) {
                
                int idPubVenueAux = 0;

                idPubVenueAux = TitleDAO.checkIdPubVenue(idPubVenue, t);

                if (idPubVenue != idPubVenueAux) {
                    i = 1;
                    ps = conn.prepareStatement("INSERT INTO title (id_pub_venue, title) VALUES (?,?)");
                    ps.setInt(i++, idPubVenue);
                    ps.setString(i++, t);
                    ps.executeUpdate();
                    ps.close();
                }
                conn.commit();
            }
            
            conn.commit();            
        }catch(SQLException e){
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            if(conn !=null){
		try{
                    conn.rollback();
		}catch(SQLException e1){                   
                    log.error("Exceção ao realizar rollback.", e1.fillInStackTrace());
                    throw new ErrorException("Ocorreu um Erro Interno");
		}
            }
            throw new ErrorException("Ocorreu um Erro Interno");
        }finally{
            if(conn !=null){
		try{
                    conn.close();
		}catch(SQLException e){
                    log.error("Exceção ao fechar a conexão.", e.fillInStackTrace());
                    throw new ErrorException("Ocorreu um Erro Interno");
		}
            }
	}
    }
    
    public static void updateQualisAndAcronym(Conference conference) throws ErrorException{
        Connection conn = null;
        
        try{
            conn = DBLocator.getConnection();
            int idPubVenue = conference.getIdPubVenue();
            PreparedStatement ps;
            
            int i=1;
            ps = conn.prepareStatement("SELECT * FROM acronym WHERE id_pub_venue = ? AND acronym = ?");
            ps.setInt(i++,idPubVenue);
            ps.setString(i++, conference.getAcronym());
            
            try(ResultSet tableAcronym = ps.executeQuery()){
                if(tableAcronym.first()){
                    i=1;
                    ps = conn.prepareStatement("UPDATE acronym SET acronym = ? WHERE id_pub_venue = ?");
                    ps.setString(i++, conference.getAcronym());
                    ps.setInt(i++,idPubVenue);
                    ps.executeUpdate();
                    ps.close();
                }else{
                    i=1;
                    ps = conn.prepareStatement("INSERT INTO acronym (id_pub_venue,acronym) VALUES (?,?)");
                    ps.setInt(i++,idPubVenue);
                    ps.setString(i++, conference.getAcronym());
                    ps.executeUpdate();
                    ps.close();
                }
            }
            ps.close();
            
            int idArea = 0;
            AreaAvaliacao area = checkAreaExists("CIÊNCIA DA COMPUTAÇÃO");

            if (!(area == null)) {
                idArea = area.getIdArea();
            }
            
            i = 1;
            ps = conn.prepareStatement("SELECT * FROM qualis WHERE id_pub_venue = ? AND id_area = ? AND year = ?");
            ps.setInt(i++, idPubVenue);
            ps.setInt(i++, idArea);
            ps.setBigDecimal(i++, BigDecimal.valueOf(conference.getYear()));

            try (ResultSet tableQualis = ps.executeQuery()) {
                if(tableQualis.first()){
                    String qualis = tableQualis.getString("qualis");
                    int indicePVAF = getIndice(qualis);
                    int indiceNew = getIndice(conference.getClassification());
                    if (indicePVAF < indiceNew) {
                        i = 1;
                        ps = conn.prepareStatement("UPDATE qualis SET qualis = ? WHERE id_pub_venue = ? AND id_area = ? AND year = ?");
                        ps.setString(i++, conference.getClassification());
                        ps.setInt(i++, idPubVenue);
                        ps.setInt(i++, idArea);
                        ps.setBigDecimal(i++, BigDecimal.valueOf(conference.getYear()));
                        ps.executeUpdate();
                        ps.close();
                    }
                } else {
                    i = 1;
                    ps = conn.prepareStatement("INSERT INTO qualis (id_pub_venue,id_area,year,qualis) VALUES (?,?,?,?)");
                    ps.setInt(i++, idPubVenue);
                    ps.setInt(i++, idArea);
                    ps.setBigDecimal(i++, BigDecimal.valueOf(conference.getYear()));
                    ps.setString(i++, conference.getClassification());
                    ps.executeUpdate();
                    ps.close();
                }
            }
            ps.close();
            
            conn.commit();
        } catch (SQLException e) {
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    log.error("Exceção ao realizar rollback.", e1.fillInStackTrace());
                    throw new ErrorException("Ocorreu um Erro Interno");
                }
            }
            throw new ErrorException("Ocorreu um Erro Interno");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error("Exceção ao fechar a conexão.", e.fillInStackTrace());
                    throw new ErrorException("Ocorreu um Erro Interno");
                }
            }
        }
    }
    
    public static void updateQualis(Conference conference)throws ErrorException{
        Connection conn = null;
        
        try{
            conn = DBLocator.getConnection();
            int idPubVenue = conference.getIdPubVenue();
            PreparedStatement ps;
                        
            List<Title> titles = new ArrayList<>();
            
            int idArea = 0;
            AreaAvaliacao area = checkAreaExists("CIÊNCIA DA COMPUTAÇÃO");

            if (!(area == null)) {
                idArea = area.getIdArea();
            }

            int i = 1;
            ps = conn.prepareStatement("SELECT * FROM qualis WHERE id_pub_venue = ? AND id_area = ? AND year = ?");
            ps.setInt(i++, idPubVenue);
            ps.setInt(i++, idArea);
            ps.setBigDecimal(i++, BigDecimal.valueOf(conference.getYear()));

            try (ResultSet tableQualis = ps.executeQuery()) {
                if (tableQualis.first()) {
                    String qualis = tableQualis.getString("qualis");
                    int indicePVAF = getIndice(qualis);
                    int indiceNew = getIndice(conference.getClassification());
                    if (indicePVAF < indiceNew) {
                        i = 1;
                        ps = conn.prepareStatement("UPDATE qualis SET qualis = ? WHERE id_pub_venue = ? AND id_area = ? AND year = ?");
                        ps.setString(i++, conference.getClassification());
                        ps.setInt(i++, idPubVenue);
                        ps.setInt(i++, idArea);
                        ps.setBigDecimal(i++, BigDecimal.valueOf(conference.getYear()));
                        ps.executeUpdate();
                        ps.close();
                    }
                } else {
                    i = 1;
                    ps = conn.prepareStatement("INSERT INTO qualis (id_pub_venue,id_area,year,qualis) VALUES (?,?,?,?)");
                    ps.setInt(i++, idPubVenue);
                    ps.setInt(i++, idArea);
                    ps.setBigDecimal(i++, BigDecimal.valueOf(conference.getYear()));
                    ps.setString(i++, conference.getClassification());
                    ps.executeUpdate();
                    ps.close();
                }
            }
            ps.close();
            
            if(idPubVenue!=0){
                titles = TitleDAO.getTitles(idPubVenue);
            }
            
            for(String journalTitle: conference.getTitles()){
                                
                int idPubVenueAux = 0;
                
                idPubVenueAux = TitleDAO.checkIdPubVenue(idPubVenue, journalTitle);
                
                if (idPubVenue != idPubVenueAux) {
                    i=1;
                    ps = conn.prepareStatement("INSERT INTO title (id_pub_venue, title) VALUES (?,?)");
                    ps.setInt(i++,idPubVenue);
                    ps.setString(i++, journalTitle);
                    ps.executeUpdate();
                    ps.close();
                }    
                conn.commit();
            }
            
        } catch (SQLException e) {
            log.error("Ocorreu uma exceção de SQL.", e.fillInStackTrace());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    log.error("Exceção ao realizar rollback.", e1.fillInStackTrace());
                    throw new ErrorException("Ocorreu um Erro Interno");
                }
            }
            throw new ErrorException("Ocorreu um Erro Interno");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error("Exceção ao fechar a conexão.", e.fillInStackTrace());
                    throw new ErrorException("Ocorreu um Erro Interno");
                }
            }
        }
    }
}
