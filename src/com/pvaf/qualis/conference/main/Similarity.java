/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.main;

import com.pvaf.qualis.conference.entidades.Title;
import irfunctions.JaccardCoefficient;
import java.util.ArrayList;

/**
 *
 * @author maneul
 */
public class Similarity {
    
    private static int idPubVenue;
    
    public Similarity(){
        idPubVenue = 0;
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
        Similarity.idPubVenue = idPubVenue;
    }
    
    public double greaterSimilarityMaior(ArrayList<String> titles1, ArrayList<String> titles2, Clear clear) {
        double aux, maior = 0;

        for(String t1: titles1){
            for(String t2: titles2){
                String t1Aux = t1.toLowerCase();
                t1Aux = clear.normalizaDouglas(t1Aux);
                t1Aux = t1Aux.replace(",","");
                
                String t2Aux = t2.toLowerCase();
                t1Aux = clear.normalizaDouglas(t1Aux);
                t2Aux = t2Aux.replace(",","");

                String[] str1 = t1Aux.split("\\s");
                String[] str2 = t2Aux.split("\\s");
                aux = JaccardCoefficient.getCoefficient(str1, str2);

                if (aux > maior) {
                    maior = aux;
                }
            }
        }
        return maior;
    }
    
    public double greaterSimilarityIgual(ArrayList<String> titles1, ArrayList<Title> titles2, Clear clear) {
        double aux, maior = 0;
        
        for(String t1: titles1){
            String t1Aux = t1.toLowerCase();
            t1Aux = clear.normalizaDouglas(t1Aux);
            t1Aux = t1Aux.replace(",","");
            
            
            for (Title title : titles2) {
                ArrayList<String> t2 = clear.clear(clear.clearBarraHifen(title.getTitle()));
                int idPubV = title.getIdPubVenue();
                
                String t2Aux = t2.toString();
                t2Aux = clear.normalizaDouglas(t2Aux);
                t2Aux = t2Aux.replace("[","");
                t2Aux = t2Aux.replace("]","");
                t2Aux = t2Aux.replace(",","");
                t2Aux = t2Aux.toLowerCase();
                

                String[] str1 = t1Aux.split("\\s");
                String[] str2 = t2Aux.split("\\s");
                aux = JaccardCoefficient.getCoefficient(str1, str2);

                if (aux > maior) {
                    maior = aux;
                    setIdPubVenue(idPubV);
                }
            }
        }
        return maior;
    }
}