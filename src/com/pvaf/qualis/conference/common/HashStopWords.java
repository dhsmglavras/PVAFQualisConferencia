/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pvaf.qualis.conference.common;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author claudiane
 */
public class HashStopWords implements Serializable{

    public static HashSet<String> hw = new HashSet<String>(700);
    
    public HashStopWords(){
        
    }
    public HashStopWords(String file) throws IOException{
        addFile(file);
    }
    
    public void remove(String word){
        hw.remove(word);
    }

    public void addFile(String filePath) throws FileNotFoundException, IOException{
        hw.addAll(Arrays.asList(Files.getContentFile(filePath).split("\\s")));
    }
    public boolean contain(String token){
            return hw.contains(token);
    }

    public static HashStopWords load(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException{

            return (HashStopWords) Files.loadObject(filePath);

    }

    public void save(String filePath) throws IOException{
            Files.saveObject(this, filePath);
    }

    public void add(String token){
            hw.add(token);
    }
    
    public String removeStopWords(String s1) {

        String aux = "";
        for (String s : s1.split(" ")) {
            if (!hw.contains(s.toLowerCase())) {
                aux += s + " ";
            }
        }
        aux = aux.replaceFirst(" +$", "");
        return aux;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException  {

            HashMap<String, String> hm = new HashMap<String,String>();
            Set<String> hs = new HashSet<String>();
            int size = 3000000;

            double time1;
            double time2;
            double time3;
            double time4;
            double time5;
            double time6;




            time4 = System.currentTimeMillis();
            for(int i = 0; i < size; ++i){
                    hs.add(""+i);
            }
            time5= System.currentTimeMillis();

            for(int i = 0; i < size; ++i){
                    hs.contains(""+i);
            }
            time6 = System.currentTimeMillis();


            //temp de insersao
            time1 = System.currentTimeMillis();
            for(int i = 0; i < size; ++i){
                    hm.put(""+i, null);
            }
            //tempo de busca
            time2 = System.currentTimeMillis();
            for(int i = 0; i < size; ++i){
                    hm.get(""+i);
            }
            //tempo de insersao
            time3 = System.currentTimeMillis();







            System.out.println("Insersao HashMap:" + (time2-time1)/1000);
            System.out.println("Busca HashMap:"+ (time3-time2)/1000);
            System.out.println("Insersao HashSet:"+ (time5-time4)/1000);
            System.out.println("busca HashSet:"+ (time6-time5)/1000);



    }

    public boolean isStopWord(String straux) {
        return hw.contains(straux);
    }

}
