/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.entidades;

import java.util.HashSet;

/**
 *
 * @author douglas
 */
public class Conference {
    
    private String acronym;
    
    private int idPubVenue;
    
    private HashSet<String> titles = new HashSet<>();
    
    private String pubType;
    
    private String classification;

    private long year;

    public Conference() {

    }
    
    public Conference(String acronym) {
        this.acronym = acronym;
    }

    /**
     * @return the acronym
     */
    public String getAcronym() {
        return acronym;
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
     * @return the titles
     */
    public HashSet<String> getTitles() {
        return titles;
    }

    /**
     * @param titles the titles to set
     */
    public void setTitles(HashSet<String> titles) {
        this.titles = titles;
    }
    
    /**
     * @return the year
     */
    public long getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(long year) {
        this.year = year;
    }
    
    /**
     * @return the pubType
     */
    public String getPubType() {
        return pubType;
    }

    /**
     * @param pubType the pubType to set
     */
    public void setPubType(String pubType) {
        this.pubType = pubType;
    }
    
    /**
     * @return the classification
     */
    public String getClassification() {
        return classification;
    }

    /**
     * @param classification the classification to set
     */
    public void setClassification(String classification) {
        this.classification = classification;
    }
    
    @Override
    public String toString(){
        return this.acronym+";"+this.idPubVenue+";"+this.titles.toString()+";"+this.pubType;
    }
    
    /*public String toString(){
        StringBuilder str = new StringBuilder();
        
        str.append(this.acronym);
        str.append(" ");
        str.append(this.getPubType());
        str.append(" ");
        str.append(this.year);
        str.append( "\n");
                
        str.append("====================================\n");
        if(!(this.titles == null)){
            for(String s: this.titles){
                str.append(s);
                str.append("\n");
            }
        }
        
        str.append("====================================\n");
        if(!(this.classification == null)){
            str.append(this.classification);
            str.append("\n");
        }        
        return str.toString();
    }*/
}
