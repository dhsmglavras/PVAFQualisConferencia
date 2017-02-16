/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.main;

import com.pvaf.qualis.conference.common.HashStopWords;
import com.pvaf.qualis.conference.common.Strings;
import com.pvaf.qualis.conference.exceptions.ErrorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import org.apache.log4j.Logger;


/**
 *
 * @author maneul
 */
public class Clear implements Serializable {

    private HashStopWords stopWords;
    private String[] wordsToReplace;
    private final static Logger log = Logger.getLogger(Clear.class);

    public Clear(String fileStopWords, String fileWordsToReplace) throws ErrorException {

        try {
            stopWords = new HashStopWords(fileStopWords);
            wordsToReplace = getWordsToReplace(fileWordsToReplace);
        } catch (IOException ex) {
            //Logger.getLogger(Clear.class.getName()).log(Level.SEVERE, "StopWords and Words to replace was not loaded.", ex);
            //System.out.println("StopWords and Words to replace was not loaded: "+new File("./").getAbsolutePath());
            log.error("StopWords and Words to replace was not loaded: " + new File("./").getAbsolutePath(), ex.fillInStackTrace());
            stopWords = new HashStopWords();
            wordsToReplace = new String[0];
            throw new ErrorException("Ocorreu um Erro Interno");
        }

    }

    public Clear() {
        stopWords = new HashStopWords();
        wordsToReplace = new String[0];
    }

    public void setStopWords(HashStopWords stopWords) {
        this.stopWords = stopWords;
    }

    public String normalizaDenilson(String str) {

        String rem[] = {"national", "biennial", "international", "annual"};
        str = Strings.removeAllBME(str, rem);

        // expand common abbreviations
        String rep[] = {"proc\\.", "proceedings ", "proc ", "proceedings ", "procs\\.", "proceedings ", "conf\\.", "conference ", "conf ", "conference ", "symp\\.", "symposium ", "symp ", "symposium ", "worksh\\.", "workshop ", "worksh ", "workshop ", "wksp\\. ", "workshop ", "wksp ", "workshop ", "jorn\\. ", "journal ", "jorn ", "journal ", "j\\. ", "journal ", "trans ", "transactions ", "trans\\.", "transactions ", "ann\\.", "annual ", "intern\\.", "international ", "intern ", "international ", "inter\\.", "international ", "inter ", "international ", "int.", "international ", "int ", "international ", "intl.", "international ", "intl ", "international ", "int'l ", "international "};
        str = Strings.replaceAll(str, rep);

        String[] rem2 = {"of", "the", "in", "on", "and", "with", "for", "from", "at", "a", "an", "to", "&"};
        str = Strings.removeAllBME(str, rem2);
        // remove proceedings ...

        String[] rep2 = {"proceedings of the", " ", "proceedings of", " ", "proceedings the", " ", "proceedings", " ", "proceeding of the", " ", "proceeding of", " ", "proceeding the", " ", "proceeding", " "};
        str = Strings.replaceAll(str, rep2);

        // remove edition of the venue
        String[] rem4 = {"st", "nd", "rd", "th", "first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth", "eleventh", "twelfth", "thirteenth", "fourteenth", "fifteenth", "sixteenth", "seventeenth", "eighteenth", "nineteenth", "twentieth", "twenty", "twenty-", "thirty", "thirty-", "forty", "forty-"};
        str = Strings.removeAll(str, rem4);
        // remove some common phrases

        String[] rep5 = {"table of contents", " ", "table contents", " ", "browse", " ", "poster", " ", "revised", " ", "papers", " ", "paper", " ", "articles", " ", "article", " ", "publication", " ", "submitted", " ", "to appear", " ", "committee", " ", "series", " ", "archive", " ", "available", " ", "homepage", " "};
        str = Strings.replaceAll(str, rep5);
        // remove words in ()
        str = str.replaceAll("\\([^\\)]+\\)", " ");

        // remove words in []
        str = str.replaceAll("\\[[^\\)]+\\]", " ");

        // remove words in {}
        str = str.replaceAll("\\{[^\\)]+\\}", " ");

        // remove months of the year
        String[] rem6 = {"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december", "jan ", "feb ", "mar ", "apr ", "may", "jun ", "jul ", "aug ", "sep ", "sept ", "oct ", "nov ", "dec "};
        str = Strings.removeAllBME(str, rem6);
        return str;
    }

    public ArrayList<String> clear(ArrayList<String> titles) {
        ArrayList<String> aux = new ArrayList<String>();
        for (String title : titles) {
            aux.add(clear(title));
        }
        return aux;
    }
    
    public String normalizaDouglas(String str) {

        str=str.replace(" on ", " on ");
        str=str.replace("“", "\"");
        str=str.replace("”", "\"");
        str=str.replace(" and P2P ", " and P2P ");
        str=str.replace(" Conference ", " Conference ");
        str=str.replace(" of XML ", " of XML ");
        str=str.replace(" for QoS:", " for QoS:");
        str=str.replace("ACL Workshop", "ACL Workshop");
        str=str.replace(" the WWW and", " the WWW and");
        str=str.replace("International XML Database", "International XML Database");
        
        return str;
    }

    public String clear(String str) {


        //replace delimiters by whitespace
        str = str.replaceAll("[-/\\ ']+", " ");

        //replace all ()[]{} by whitespace
        str = str.replaceAll("[\\(\\)\\[\\]\\{\\}]", " ");

        //replace words to correspondent
        str = Strings.replaceAll(str, wordsToReplace);

        //Transforme todas as letras para minúsculas.
        //transform to lowerCase.
        str = str.toLowerCase();

        //remove acrons inside of ()[]{}
//        str = str.replaceAll("[\\(\\{\\[]\\s*(\\d*\\p{Punct}*[A-Za-z]+\\p{Punct}*\\d*)+\\s*[\\)\\]\\}]", " $1 ");
//        str = str.replaceAll("[\\(\\{\\[]([A-Za-z]+\\s*\\d+).*[\\)\\]\\}]", " $1 ");
//        //remove all inside ()[]{}
//        str = str.replaceAll("[\\(\\{\\[][^\\)\\}\\]]+[\\)\\]\\}]", " ");
        //Separar os tokens de cada instância por espaço em branco.
        //Eliminar sinais de pontuação .,;!? que aparecem no final dos tokens.
        str = str.replaceFirst(" +", " ").replaceFirst("^ +", "").replaceFirst(" +$", "").replaceAll("\\p{Punct}", "");

        str = str.replaceAll(" \\d+ ", "");
        str = str.replaceAll("^\\d+ ", "");
        str = str.replaceAll(" \\d+$", "");

        ArrayList<String> ret = new ArrayList<String>();
        for (String straux : Strings.removeRepetidos(str.split(" "))) {
            if (!stopWords.isStopWord(straux) && !straux.replaceAll(" ", "").isEmpty()) {
                //se nao for stop words, entao adiciona
                ret.add(straux);

            }
        }
        String[] retorno = ret.toArray(new String[ret.size()]);
//        Arrays.sort(retorno);

        return Strings.vetToFrase(retorno);

    }

    public String clearSort(String str) {

        //replace delimiters by whitespace
        str = str.replaceAll("[-/\\ ']+", " ");

        //replace all ()[]{} by whitespace
        str = str.replaceAll("[\\(\\)\\[\\]\\{\\}]", " ");

        //replace words to correspondent
        str = Strings.replaceAll(str, wordsToReplace);

        //Transforme todas as letras para minúsculas.
        //transform to lowerCase.
        str = str.toLowerCase();

        //remove acrons inside of ()[]{}
//        str = str.replaceAll("[\\(\\{\\[]\\s*(\\d*\\p{Punct}*[A-Za-z]+\\p{Punct}*\\d*)+\\s*[\\)\\]\\}]", " $1 ");
//        str = str.replaceAll("[\\(\\{\\[]([A-Za-z]+\\s*\\d+).*[\\)\\]\\}]", " $1 ");
//        //remove all inside ()[]{}
//        str = str.replaceAll("[\\(\\{\\[][^\\)\\}\\]]+[\\)\\]\\}]", " ");
        //Separar os tokens de cada instância por espaço em branco.
        //Eliminar sinais de pontuação .,;!? que aparecem no final dos tokens.
        str = str.replaceFirst(" +", " ").replaceFirst("^ +", "").replaceFirst(" +$", "").replaceAll("\\p{Punct}", "");

        str = str.replaceAll(" \\d+ ", "");
        str = str.replaceAll("^\\d+ ", "");
        str = str.replaceAll(" \\d+$", "");

        ArrayList<String> ret = new ArrayList<String>();
        for (String straux : Strings.removeRepetidos(str.split(" "))) {
            if (!stopWords.isStopWord(straux) && !straux.replaceAll(" ", "").isEmpty()) {
                //se nao for stop words, entao adiciona
                ret.add(straux);

            }
        }
        String[] retorno = ret.toArray(new String[ret.size()]);
        Arrays.sort(retorno);

        return Strings.vetToFrase(retorno);

    }

    public String makeTitle(String title) {
        String maked = "";
        String[] words = title.split(" ");
        if (words.length <= 1) {
            return null;
        }
        for (int i = 0; i < words.length - 1; ++i) {
            maked += words[i] + words[i + 1] + " ";
        }
        return maked.replaceFirst(" +$", "");
    }

    public String[] makeTitle(String[] words) {
        String[] maked = new String[words.length - 1];

        if (words.length <= 1) {
            return null;
        }

        for (int i, j = i = 0; i < words.length - 1; ++i) {
            maked[j++] = words[i] + words[i + 1];
        }
        return maked;
    }

    public Iterable<String> makeTitles(Iterable<String> titles) {
        LinkedList<String> list = new LinkedList();
        for (String title : titles) {
            list.add(title);
            list.add(makeTitle(title));
        }
        return list;
    }

    private String[] getWordsToReplace(String file) throws IOException {
        ArrayList<String> wtr = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(new File(file)));

        String line = br.readLine();
        int i = 0;
        String a, b;
        try {
            while (line != null && !line.trim().isEmpty()) {
                ++i;

                //transform line to lowercase
                //line = line.toLowerCase();
                a = line.split(" , ")[0];
                try {
                    b = line.split(" , ")[1];
                } catch (IndexOutOfBoundsException ex) {
                    b = "";
                }
//                System.out.println( "#"+a+"# e #"+b+"#");
                if (a.startsWith("cns:")) {
                    a = Strings.upLowerPattern(a.replaceFirst("cns:", ""));
                }
                wtr.add(" " + a + " ");
                wtr.add(" " + b + " ");

                wtr.add("^" + a + " ");
                wtr.add(b + " ");

                wtr.add(" " + a + "$");
                wtr.add(" " + b);

                wtr.add("^" + a + "$");
                wtr.add(b);

                line = br.readLine();

            }
        } catch (Exception ex) {
            //Logger.getLogger(Clear.class.getName()).log(Level.SEVERE, null, ex);
            log.error("Exceção: "+ex.getMessage(), ex.fillInStackTrace());
            throw new IllegalArgumentException("erro na linha " + i + "do arquivo " + file + "\n" + ex);
        }
        return wtr.toArray(new String[wtr.size()]);
    }

    /**
     * Where exist an hifen or bar separating an word, the method creat three
     * words, the word before hifen or bar, the word before and the word
     * resulting by the junction of two. e.g: ACM-AAAI -> ACM, ACMAAAI, AAAI.
     *
     * @param words
     * @return
     */
    public ArrayList<String> clearBarraHifen(ArrayList<String> words) {
        ArrayList<String> wordsRet = new ArrayList<String>();
        for (String word : words) {
            wordsRet.addAll(clearBarraHifen(word));

        }
        return wordsRet;
    }

    public ArrayList<String> clearBarraHifen(String word) {
        String[] words = word.split("-");
        ArrayList<String> wordsRet = new ArrayList<String>();
        String str;
        int k = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i; j < words.length; j++, k++) {
                str = "";
                for (int l = i; l <= j; l++) {
                    str += words[l];

                }
                if (!str.isEmpty()) {
                    wordsRet.add(str);
                }
            }
        }

        return wordsRet;
    }

}
