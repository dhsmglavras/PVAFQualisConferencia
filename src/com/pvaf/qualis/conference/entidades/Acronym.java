/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.entidades;

/**
 *
 * @author douglas
 */
public class Acronym {
    
    private int idPubVenue;
    private String acronym;
    
    public Acronym(String acronym){
        this.acronym = acronym;
    }
    
    public Acronym(int idPubVenue, String acronym){
        this.idPubVenue = idPubVenue;
        this.acronym = acronym;
    }

    /**
     * @return the idPubVenue
     */
    public int getIdPubVenue() {
        return idPubVenue;
    }

    /**
     * @param idPubVenue the idPubVenue to set
     */
    public void setIdPubVenue(int idPubVenue) {
        this.idPubVenue = idPubVenue;
    }

    /**
     * @return the acronym
     */
    public String getAcronym() {
        return acronym;
    }

    /**
     * @param acronym the acronym to set
     */
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
    
    @Override
    public String toString(){
        return this.idPubVenue+";"+this.acronym;
    }
}
