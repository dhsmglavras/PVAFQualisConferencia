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
public class Title {
    
    private int idPubVenue;
    private String title;
    
    public Title(String title){
        this.title = title;
    }
    
    public Title(int idPubVenue, String title){
        this.idPubVenue = idPubVenue;
        this.title = title;
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String toString(){
        return this.idPubVenue+";"+this.title;
    }
    
}
