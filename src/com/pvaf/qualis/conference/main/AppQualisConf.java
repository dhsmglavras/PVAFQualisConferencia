/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.main;

import com.pvaf.qualis.conference.dao.AcronymDAO;
import com.pvaf.qualis.conference.dao.ConferenceDAO;
import com.pvaf.qualis.conference.dao.TitleDAO;
import com.pvaf.qualis.conference.entidades.Acronym;
import com.pvaf.qualis.conference.entidades.Conference;
import com.pvaf.qualis.conference.entidades.QualisConference;
import com.pvaf.qualis.conference.entidades.Title;
import com.pvaf.qualis.conference.io.ReadQualisConf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author douglas
 */
public class AppQualisConf {
    
    protected static List<QualisConference> conferencesIne = new ArrayList<>();
    protected static List<QualisConference> conferencesExi = new ArrayList<>();    
    protected static List<QualisConference> conferencesUpd = new ArrayList<>();
        
    protected static TreeSet<String> acronymsIne = new TreeSet<>(); // acronyms não existentes no bd    
    protected static TreeSet<String> acronymsExi = new TreeSet<>(); // acronyms existentes no bd
    protected static TreeSet<String> acronymsUpd = new TreeSet<>(); // acronyms existentes no bd
    
    public static Set<String> lerQualis(String path){
        ReadQualisConf rq = new ReadQualisConf(path);
        rq.redQualis();
        return rq.getConferencesQualis();
    }
    
    public static String verifyPubType(String title){
        
        if (title.contains("workshop")) {
            return "W"; 
        }else if(title.contains("conference")){
            return "C";
        }
        return null;
    }
        
    public static List<QualisConference> processQualisConference(Set<String> qualisConference, long year){
        List<QualisConference> qualisConferences = new ArrayList<>();
        
        for(String str: qualisConference){
            String[] token = str.split(";");
            
            String acronym  = token[0];
            String title = token[1];
            String classification = token[2];
            String pubType = verifyPubType(title.toLowerCase());
            
            pubType = (!(pubType==null)) ? pubType : "C";
            
            QualisConference con = new QualisConference(acronym,title,classification,pubType,year);
            qualisConferences.add(con);
        }
        return qualisConferences;
    }
    
    public static Set<String> createSetAcronym(List<QualisConference> list){
        Set<String> acronymCo = new HashSet<>();
        
        for(QualisConference co: list){
            acronymCo.add(co.getSigla());
        }
        return acronymCo;
    }
    
    public static void splitConferences(Set<Acronym> acronyms, List<QualisConference> conferences){
        
        String fileStopWords = "./XMLs/configuracao/conjunto-de-stopWords.txt";
        String fileWordsToReplace = "./XMLs/configuracao/conjunto-de-palavras-a-serem-substituidas.txt";
        
        Clear clear = new Clear(fileStopWords, fileWordsToReplace);
        
        for(QualisConference qc: conferences){
            boolean siglaExist = false;
            boolean simIgual = false;
            boolean simMaior = false;
            boolean casIgual = false;
            int cont = 0;
            
            String sigla = qc.getSigla();
                        
            for (Acronym acronym : acronyms) {
                String acr = acronym.getAcronym();
                
                if(sigla.toLowerCase().equals(acr.toLowerCase())){
                    siglaExist = true;
                    ArrayList<String> titles1 = clear.clear(clear.clearBarraHifen(qc.getTitle()));
                    ArrayList<String> titles2 = clear.clear(clear.clearBarraHifen(TitleDAO.getAtributoTitles(acronym.getIdPubVenue())));
                    Similarity similarity = new Similarity();
                    if((similarity.greaterSimilarityMaior(titles1,titles2,clear)>=0.8)){
                        simMaior = true;
                        qc.setIdPubVenue(acronym.getIdPubVenue());
                        conferencesExi.add(qc);
                        acronymsExi.add(qc.getSigla());
                    }else{
                        cont++;
                        if(simMaior){
                           cont = 0; 
                        }
                    }
                }else {
                    cont++;
                }
            }
            
            if(!siglaExist){
                ArrayList<String> titles1 = clear.clear(clear.clearBarraHifen(qc.getTitle()));
                ArrayList<Title> titles2 = TitleDAO.getAllTitles();
                Similarity similarity = new Similarity();
                
                if((similarity.greaterSimilarityIgual(titles1,titles2,clear))==1.0){
                    simIgual=true;
                    qc.setIdPubVenue(similarity.getIdPubVenue());
                    conferencesUpd.add(qc);
                    acronymsUpd.add(qc.getSigla());
                    cont=0;
                }
            }
            
            if(siglaExist && !simMaior && !simIgual){
                String title1 = clear.normalizaDouglas(qc.getTitle());
                title1 = title1.toLowerCase();
                
                ArrayList<Title> titles2 = TitleDAO.getAllTitles();
                
                for(Title t2: titles2){
                    String title2 = t2.getTitle();
                    title2 = title2.toLowerCase();
                    
                    if(title1.equals(title2)){
                        casIgual=true;
                        qc.setIdPubVenue(t2.getIdPubVenue());
                        conferencesExi.add(qc);
                        acronymsExi.add(qc.getSigla());
                        cont=0;
                    }
                }
            }
                        
            if(!siglaExist && !simIgual && !casIgual){
                conferencesIne.add(qc);
                acronymsIne.add(qc.getSigla());
                cont=0;
            }
            
            if(acronyms.size()==cont){
                conferencesIne.add(qc);
                acronymsIne.add(qc.getSigla());
            }
        }
        
        CompararConfrencesTitulos comT = new CompararConfrencesTitulos();
        Collections.sort(conferencesIne, comT);
        
        Collections.sort(conferencesExi, comT);
        
        Collections.sort(conferencesUpd, comT);
    }
    
    public static List<Conference> createListQualis(Set<String> acronyms){ //retornar
        List<Conference> listQualis = new ArrayList<>();
        
        for(String acronym: acronyms){
            Conference c = new Conference(acronym);
            listQualis.add(c);
        }
        
        return listQualis;
    }
    
    public static void addAttributesConferences(List<Conference> listQualis, List<QualisConference> listQC){
        for(int i=0; i < listQualis.size(); i++){
            Conference conference = listQualis.get(i);
            HashSet<String> titles;
            
            for(QualisConference qc: listQC){
                
                if(conference.getAcronym().toLowerCase().equals(qc.getSigla().toLowerCase())){
                    
                    conference.setIdPubVenue(qc.getIdPubVenue());
                    
                    titles = conference.getTitles();
                    titles.add(qc.getTitle());
                    conference.setTitles(titles);
                    
                    conference.setClassification(qc.getClassification());
                    
                    conference.setPubType(qc.getPubType());
                    
                    conference.setYear(qc.getYear());
                }
            }
        }
    }
    
    public static void main(String[] args) {
        
        Set<String> setConferencesQualis = lerQualis("qualis-conf-cc-2012.xls");
                
        List<QualisConference> listQualisConferences = processQualisConference(setConferencesQualis,2012);
        
        Set<Acronym> setAcronymDB = AcronymDAO.acronymDB();
        
        splitConferences(setAcronymDB, listQualisConferences);
        
        setConferencesQualis.clear();
        listQualisConferences.clear();
        setAcronymDB.clear();
        
        List<Conference> qualisConfExi = createListQualis(acronymsExi);
        addAttributesConferences(qualisConfExi,conferencesExi);
        conferencesExi.clear();
        acronymsExi.clear();
        
        List<Conference> qualisConfIne = createListQualis(acronymsIne);
        addAttributesConferences(qualisConfIne,conferencesIne);
        conferencesIne.clear();
        acronymsIne.clear();
        
        List<Conference> qualisConfUpd = createListQualis(acronymsUpd);
        addAttributesConferences(qualisConfUpd,conferencesUpd);
        conferencesUpd.clear();
        acronymsUpd.clear();
        
        for(Conference c: qualisConfExi){
            ConferenceDAO.updateQualis(c);
        }
        
        for(Conference c: qualisConfUpd){
           ConferenceDAO.updateQualisAndAcronym(c);
        }
        
        for(Conference c: qualisConfIne){
            ConferenceDAO.insertConference(c);
        }
        
        System.out.println(qualisConfExi.size());
        System.out.println(qualisConfUpd.size());
        System.out.println(qualisConfIne.size());
    }
    
    private static class CompararConfrencesTitulos implements Comparator<QualisConference>{      
        @Override
        public int compare(QualisConference o1, QualisConference o2) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
    }
}
