/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.entidades;

/**
 *
 * @author marte
 */
public class AreaAvaliacao {
    
    private int idArea;
    private String nomeArea;
    
    public AreaAvaliacao(int idArea, String nomeArea){
        this.idArea = idArea;
        this.nomeArea = nomeArea;
    }

    /**
     * @return the idArea
     */
    public int getIdArea() {
        return idArea;
    }

    /**
     * @param idArea the idArea to set
     */
    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    /**
     * @return the nomeArea
     */
    public String getNomeArea() {
        return nomeArea;
    }

    /**
     * @param nomeArea the nomeArea to set
     */
    public void setNomeArea(String nomeArea) {
        this.nomeArea = nomeArea;
    }
    
    public String toString(){
        return this.idArea+";"+this.nomeArea;
    }
    
}
