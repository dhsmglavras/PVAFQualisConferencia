/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.dao;

import com.pvaf.qualis.conference.service.DBLocator;
import com.pvaf.qualis.conference.entidades.AreaAvaliacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marte
 */
public class AreaAvaliacaoDAO {
    
    public static List<AreaAvaliacao> getAllNamesAreaAvaliacao(){
        List<AreaAvaliacao> listA = new ArrayList<>();
        
        try(Connection conn = DBLocator.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM area_avaliacao");
            ResultSet rs = ps.executeQuery()){
            
            AreaAvaliacao area;
            while(rs.next()){
                area = new AreaAvaliacao(rs.getInt("id_area"),rs.getString("nome_area"));
                listA.add(area);
            }
	}catch(SQLException e){
            System.err.println("Ocorreu uma exceção de SQL. Causa: " + e.getMessage());
	}
	return listA;
    }
}
