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
public class QualisConference {
    
    private final String sigla;
    private String title;
    private String classification;
    private String pubType;
    private long year;
    private int idPubVenue;
    
    public QualisConference(String sigla, String title,String classification, String pubType, long year){
        this.sigla = sigla;
        this.title = title;
        this.classification = classification;
        this.pubType = pubType;
        this.year = year;
        this.idPubVenue = 0;
    }

    /**
     * @return the sigla
     */
    public String getSigla() {
        return sigla;
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
    
    @Override
    public String toString(){
        return this.sigla + ";" + this.title + ";" +this.pubType+";"+ this.year;
    }
}
