/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author maneul
 */
public class Strings implements Serializable {

    /**
     *
     * @param string codigo a ser analizado
     * @param pattern padrao regex a ser encontrado no codigo
     * @return Array of String which contain each patter find in codpage
     */
    public static String[] getArrayPattern(String string, String pattern) {
        Pattern padrao = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher matcher = padrao.matcher(string);
        ArrayList<String> result = new ArrayList<String>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result.toArray(new String[result.size()]);
    }

    /**
     * conta o numero de palavras em uma frase, considerando o espaco como
     * delimitador
     *
     * @param str word to cont the number of words
     * @return the number of words in the str
     */
    public static int countWords(String str) {
        return str.split(" ").length;
    }

    public static String repeat(String repeat, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(repeat);

        }
        return new String(sb);
    }

    /**
     * Retorna o primeiro padrão econtrado na string
     *
     * @param string código a ser analizado
     * @param pattern padrao regex a ser encontrado no codigo
     * @return o primeiro padrã0 econtrado na string
     */
    public static String getFirstPattern(String string, String pattern) {
        Pattern padrao = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher matcher = padrao.matcher(string);
        String result = "";
        if (matcher.find()) {
            return matcher.group();
        }
        return result;
    }

    /**
     * retorna o ultimo padrao encontrado na string
     *
     * @param string codigo a ser analizado
     * @param pattern padrao regex a ser encontrado no codigo
     * @return o ultimo padrao encontrado na pagina ou a string vazio caso
     * nenhum padrão seja econtrado
     */
    public static String getLastPattern(String string, String pattern) {
        Pattern padrao = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher matcher = padrao.matcher(string);
        String result = null;
        while (matcher.find()) {
            result = matcher.group();
        }
        return result;
    }

    /**
     * Encontra na string o padrao em linguagem regular.
     *
     * @param string codigo a ser analizado
     * @param pattern padrao regex a ser encontrado no codigo
     * @return um string contendo todos os padroes encontrados, estando cada
     * padrao separado por um \n ou a string vazio caso não seja encontrado
     * nada.
     */
    public static String getPattern(String string, String pattern) {
        Pattern padrao = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher matcher = padrao.matcher(string);
        String result = "";
        while (matcher.find()) {
            result += matcher.group() + "\n";
        }
        return result;
    }

    /*
     * este método recebe uma string e aplica sequencialmente os regex contidos
     * na string regex, de forma que o resultado do primeiro e usado no segundo regex.
     * e apos aplicar o regexs, faz uma sequencia re replace all cons as strings contidas no vetor
     * de string replaceAll, o resultado do primeiro replaceAll é usado no segundo relplaceAll.
     * No vetor string replaceAll a string na posicao par será substituida pela string na
     * posição par + 1.
     */
    public static String getValue(String value, String[] regex, String[] replaceAll) {
        try {
            for (int i = 0; i < regex.length; ++i) {
                value = getPattern(value, regex[i]);
            }

            for (int i = 0; i < replaceAll.length; i += 2) {
                value = value.replaceAll(replaceAll[i], replaceAll[i + 1]);
            }
        } catch (Exception e) {
//            System.out.println(e);
        }

        return value;
    }

    /**
     * dada uma frase str e um substring with, é retornado um frase contendo
     * todas as palavras que nao possua a substring with
     *
     * @param str frase a ser analizada
     * @param with substring a qual a palavras na frase deva possuir para ser
     * adicionada ao retorno
     * @return um frase sem as palavras que contenham with como substring
     */
    public static String getWordWith(String str, String with) {
        String[] aux = str.split(" ");
        String result = "";
        for (String s : aux) {
            if (s.matches(".*" + with + ".*")) {
                result += s + " ";
            }
        }
        return result.replaceFirst(" $", "");

    }

    public static int indexOf(String[] simbs, String simbol) {
        for (int i = 0; i < simbs.length; ++i) {
            if (simbs[i].equals(simbol)) {
                return i;
            }
        }
        return -1;
    }

    public static String[] removeRepetidos(String[] simbols) {
        HashSet<String> hm = new HashSet<String>();

        for (String s : simbols) {
            if (!hm.contains(s)) {
                hm.add(s);
            }
        }

        return hm.toArray(new String[hm.size()]);
    }

    /**
     * Get all strgins that contain that regex
     *
     * @param strings to be filter
     * @param regex to filter the strings
     * @return the strings filtered
     */
    public static String[] filter(String[] strings, String regex) {
        int j = 0;
        for (int i = 0; i < strings.length; ++i) {
            if (strings[i].matches(regex)) {
                strings[j++] = strings[i];
            }
        }
        return Arrays.copyOf(strings, j);
    }

    static String[] concat(String[] a, String[] b) {
        String[] c = new String[a.length + b.length];
        int i;
        for (i = 0; i < a.length; ++i) {
            c[i] = a[i];
        }
        --i;
        for (int j = 0; j < b.length; ++j) {
            c[++i] = b[j];
        }

        return c;
    }

    /**
     * Faz uma limpeza na string, na seguinte ordem: Substitui todos os tipos de
     * pontuações por espaço, Substitui todas as sequências de espaçoes por um
     * espaço apenas, remove todos os espaços no início e final da string
     *
     * @param str string a ser removeSomeHTMLValues
     * @return a string com as remoções e substituições
     */
    public static String clearPunctSpaces(String str) {

        return str.replaceAll("\\p{Punct}", " ").
                replaceAll(" +", " ").
                replaceFirst("^\\s+", "").
                replaceAll("\\s+$", "");

    }

    /**
     * Faz uma limpeza na string, na seguinte ordem: Substitui todas as
     * sequências de espaçoes por um espaço apenas, remove todos os espaços no
     * início e final da string
     *
     * @param str string a ser removeSomeHTMLValues
     * @return a string com as remoções e substituições
     */
    public static String clearSpaces(String str) {

        return str.replaceAll(" +", " ").
                replaceFirst("^\\s+", "").
                replaceAll("\\s+$", "");

    }

    /**
     * Uma extensão do método String.replaceAll, faz uma sequência de replaces
     * na string. A ordem de replace obedece a ordem do vetor.
     *
     * @param string - String a qual será aplicado a sequência de replaces
     * @param replaces - um vetor contendo as strings a ser subtituirdas e seu
     * valores, uma substituicao e um valor, uma substituico e um valor e assim
     * em diante, por consequência o tamnho deste vetor é par. ex: vet =
     * {replace,for,replace,for,......}
     * @return a string de entrada com todos os replaces no vetor aplicado
     */
    public static String replaceAll(String string, String[] replaces) {

        if (replaces.length % 2 == 1) {
            throw new IllegalArgumentException("Replaces invalidos");
        }

        for (int i = 0; i < replaces.length; i += 2) {
//            System.out.println("This: \""+replaces[i]+"\" for: \""+replaces[i+1]+"\"");
            string = string.replaceAll(replaces[i], replaces[i + 1]);
//            System.out.println(string);
        }

        return string;

    }

    /**
     * Susbstitui todas as pontuações de um texto por espaço e coloca em um
     * vetor cada palavra separada por espaço.
     *
     * @param texto o texto contendo as palavras.
     * @return retorna um vetor com todas as palavras econtradas no texto.
     */
    public static String[] getVetWords(String texto) {

        return clearPunctSpaces(texto).split(" ");

    }

    /**
     * Susbstitui todas as pontuações de um texto por espaço e coloca em um
     * vetor cada palavra separada por espaço.
     *
     * @param texto o texto contendo as palavras.
     * @return retorna um conjunto com todas as palavras econtradas no texto sem
     * palavras duplicadas.
     */
    public static Map<Integer, String> getWordsNumber(String texto) {

        HashMap<String, Integer> hash = new HashMap<String, Integer>();

        String[] palavras = clearPunctSpaces(texto).split(" ");

        for (int i = 0; i < palavras.length; ++i) {
            Integer j = 0;
            if ((j = hash.get(palavras[i])) == null) {
                hash.put(palavras[i], 1);

            } else {
                hash.put(palavras[i], j + 1);
            }
        }
        Map<Integer, String> tm = new TreeMap<Integer, String>();
        for (String s : hash.keySet()) {
            tm.put(hash.get(s), s);
        }

        return tm;
    }

    /**
     * este método cria um padrão que reconhece todos as variaçoes que pode ser
     * obtido de uma palavra variando se os caracteres dela para maiúsculo e
     * minúsculo A palavra casa pode gerar Casa cAsa CAsa ..., e o padrao criado
     * será [cC][aA][sS][aA].
     *
     * @param toUpLower a string a ser convertido para o padrao [aA][bB][cC}...
     * ex: casa = [cC][aA][sS][aA]
     * @return o padrão criado para reconhecer as variações
     */
    public static String upLowerPattern(String toUpLower) {
        String aux1 = toUpLower.toLowerCase();
        String aux2 = toUpLower.toUpperCase();
        String result = "";
        char c1, c2;
        for (int i = 0; i < toUpLower.length(); ++i) {
            c1 = aux1.charAt(i);
            c2 = aux2.charAt(i);
            if (c1 != c2) {
                result += "[" + c1 + c2 + "]";
            } else {
                result += c1;

            }
        }
        return result.replaceAll("\\[(.)$1\\]", "$1");
    }

    static void printArray(String[] a) {
        for (String s : a) {
            System.out.println(s);

        }
    }

    public static String sortLexicograf(String toString) {
        String s[] = toString.split(" ");
        Arrays.sort(s);
        String res = "";
        for (String str : s) {
            res += str + " ";
        }
        return res;
    }

    public static String completWithSpace(String str, int size) {
        size = size - str.length();
        for (int i = 0; i < size; ++i) {
            str += " ";
        }
        return str;
    }

    /**
     * Give str1 and str2 the return is a string with last word of str2 concat
     * with str1, observing the lexicograficaly orderly of last word in str1 and
     * last word ins str2. is considerated a word the strings splited by space
     * e.g: str1 = cachorro casa madeira str2 = floresta abacate nada returns is
     * cachorro casa nada madeira
     *
     * @param str1 string one
     * @param str2 string two
     * @return concat of last word of str2 with str1, lexycograficaly in last
     * word str1 e last word str2.
     */
    public static String concatLexicograficalyAtFinal(String str1, String str2) {
        //base case
        if (str1.isEmpty()) {
            return str2;
        } else if (str2.isEmpty()) {
            return str1;
        }
        String str11 = str1.replaceAll("^.* ", "");
        String str21 = str2.replaceAll("^.* ", "");
        if (str11.compareTo(str21) > 0) {
            return str1.replaceFirst(str11 + "$", str21 + " " + str11);
        } else {
            return str1 + " " + str21;
        }

    }

    /**
     * Give str1 and str2 the return is a string with first word of str2 concat
     * with first word of str1, observing the lexicograficaly orderly of first
     * word in str1 and first word ins str2. is considerated a word the strings
     * splited by space e.g: str1 = cachorro casa madeira str2 = floresta
     * abacate nada returns is cachorro casa nada madeira
     *
     * @param str1 string one
     * @param str2 string two
     * @return concat of last word of str2 with str1, lexycograficaly in last
     * word str1 e last word str2.
     */
    public static String concatLexicograficalyAtBegin(String str1, String str2) {
        //base case
        if (str1.isEmpty()) {
            return str2;
        } else if (str2.isEmpty()) {
            return str1;
        }
        String str11 = str1.replaceAll(" .*$", "");
        String str21 = str2.replaceAll(" .*$", "");
        if (str11.compareTo(str21) > 0) {
            return str21 + " " + str1;
        } else {
            return str1.replaceFirst("^" + str11, str11 + " " + str21);
        }

    }

    /**
     * Give str1 and str2 the return is a string with last word of str2 concat
     * with str1, observing the lexicograficaly orderly of last word in str1 and
     * last word ins str2. is considerated a word the strings splited by space
     *
     * e.g: str1 = cachorro casa madeira str2 = floresta abacate nada returns is
     * cachorro casa nada madeira
     *
     * @param str1 string one, must be no empty
     * @param str2 string two, must be no empty
     * @return concat of last word of str2 with str1, lexycograficaly in last
     * word str1 e last word str2.
     */
    public static String concatLexicograficalyByteAtFinal(String str1, String str2) {
        //base case
        if (str1.isEmpty()) {
            return str2;
        } else if (str2.isEmpty()) {
            return str1;
        }
        byte[] str2b = str2.getBytes();
        int pos2 = 0;
        for (int i = str2b.length - 1; i > 0; --i) {
            if (str2b[i] == ' ') {
                pos2 = i + 1;
                break;
            }
        }
        int size2 = str2b.length - pos2;
        int pos1 = 0;
        //temporaly
        pos1 = str1.length();
        byte[] str1b = new byte[pos1 + size2 + 2];
        str1.getBytes(0, pos1, str1b, 0);
        pos1 = 0;
        for (int i = str1b.length - 1; i > 0; --i) {
            if (str1b[i] == ' ') {
                pos1 = i + 1;
                break;
            }
        }
        for (int i = 0; i < size2; ++i) {
            if (str1b[pos1 + i] == str2b[pos2 + i]) {
                continue;
            } else {
                //string 1 é maior que string 2
                if (str1b[pos1 + i] <= str2b[pos2 + i]) {
                    str1b[str1b.length - size2 - 1] = ' ';
//                                        System.out.println("size2: "+size2);
//                                        System.out.println("str1b.lenth: "+str1b.length);

                    for (int k = (str1b.length - size2); k < str1b.length; ++k) {
//                                                System.out.println(str2b[pos2]);
                        str1b[k] = str2b[pos2++];
                    }
//                                        System.out.println("after size: "+str1b.length);
                    return new String(str1b);
                } else {
                    //string 2 é maior que string 1
                    int k;
//                                        System.out.println(pos1);

                    //write final string
                    int size1 = str1b.length - (pos1 + size2 + 2);
                    int temp = size2 + 1;
                    for (k = pos1; k < size1; ++k) {
                        str1b[k + temp] = str1b[k];
                    }
                    //temporaly
                    temp = size2 + pos1;
                    for (k = pos1; k < temp; ++k) {
                        str1b[k] = str2b[pos2++];
                    }
                    str1b[k] = ' ';
                    return new String(str1b);
                }
            }
        }
        return str1;
    }

    public static String getFirstWordBeforeSpace(String key) {
        return key.replaceAll(" .*", "");

//                byte[] bs = new byte[key.length()];
//                int i = 0;
//                for (byte b : key.getBytes()) {
//                        if (b != ' ') {
//                                bs[i++] = b;
//                        } else {
//                                return new String(bs);
//                        }
//                }
//                return new String(bs);
    }

    public static boolean isEqualsOtherWordsAfterFirstSpace(String key1, String key2) {
        int index1 = key1.indexOf(" ");
        int index2 = key2.indexOf(" ");
        if (index1 == -1 || index2 == -1) {
            return true;
        }
        return key1.substring(key1.indexOf(" ")).equals(key2.substring(key2.indexOf(" ")));

//
//                byte[] keybs1 = key1.getBytes();
//                byte[] keybs2 = key2.getBytes();
//                int i;
//                for (i = 0; i < keybs1.length; ++i) {
//                        if (keybs1[i] == ' ') {
//                                break;
//                        }
//                }
//                if (keybs2[i] == ' ') {
//                        try {
//                                for (; i < keybs1.length; ++i) {
//                                        if (keybs1[i] != keybs2[i]) {
//                                                return false;
//                                        }
//                                }
//                        } catch (IndexOutOfBoundsException e) {
//                                return false;
//                        }
//                        if (keybs2.length == keybs1.length) {
//                                return true;
//                        }
//                }
//                return false;
    }

    public static void main(String[] args) throws Exception {

        System.out.println(isEqualsOtherWordsAfterFirstSpace("Journal Journal Ubiquitous Ubiquitous and",
                "International Journal Journal Ubiquitous and"));
//Key2: Journal Journal Ubiquitous Ubiquitous and
//Key1: International Journal Journal Ubiquitous and

    }

    public static void printInOrderWordsCount(String text) {
        Map<Integer, String> words = Strings.getWordsNumber(text);
        for (Integer i : words.keySet()) {
            System.out.println(words.get(i) + " " + i);
        }
    }

    public static String removeAll(String str, String[] rem) {
        for (String s : rem) {
            str = str.replaceAll(s, " ");
        }
        return str;
    }

    public static String removeAllBME(String str, String[] rem) {
        for (String s : rem) {
            str = str.replaceAll("^" + s + " ", "");
            str = str.replaceAll(" " + s + " ", " ");
            str = str.replaceAll(" " + s + "$", "");
        }
        return str;
    }

    public static String removeAllBM(String str, String[] rem) {
        for (String s : rem) {
            str = str.replaceAll("^" + s + " ", "");
            str = str.replaceAll(" " + s + " ", " ");
        }
        return str;
    }

    public static String removeAllBE(String str, String[] rem) {
        for (String s : rem) {
            str = str.replaceAll("^" + s + " ", "");
            str = str.replaceAll(" " + s + "$", "");
        }
        return str;
    }

    public static String removeAllME(String str, String[] rem) {
        for (String s : rem) {
            str = str.replaceAll(" " + s + " ", " ");
            str = str.replaceAll(" " + s + "$", "");
        }
        return str;
    }

    public static String vetToFrase(String[] vetToFrase) {
        String frase = "";
        for (String s : vetToFrase) {
            frase += s + " ";
        }
        return frase.replaceFirst(" $", "");
    }

    public static String toXMLPattern(String f) {
        return f.replaceAll("&", "&amp;");
    }

    public static String[] concat(String[] strs, String text) {
        for (int i = 0; i < strs.length; i++) {
            strs[i] += text;

        }
        return strs;
    }

    public static List<String> concat(List<String> strs, String text) {
        for (int i = 0; i < strs.size(); i++) {
            strs.set(i, strs.get(i) + text);
        }
        return strs;
    }

    public static String spaces(int space) {
        String spaces = "";
        for (int i = 0; i < space; ++i) {
            spaces += " ";
        }
        return spaces;
    }

    public static boolean anyEquals(Iterable<String> strings1, Iterable<String> strings2) {

        for (String s1 : strings1) {
            for (String s2 : strings2) {
                if (s2.equals(s1)) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     *
     * @param array1 with strings
     * @param array2 with other strings
     * @return true if exist at least one string equals ignore case in array1
     * and array2
     */
    public static boolean anyEqualsIgnoreCase(ArrayList<String> array1, ArrayList<String> array2) {

        for (String s : array1) {
            for (String s2 : array2) {
                if (s.equalsIgnoreCase(s2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean anyEqualsIgnoreCase(Collection<String> strings1, Collection<String> strings2) {

        for (String s : strings1) {
            for (String s2 : strings2) {
                if (s.equalsIgnoreCase(s2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String format(String temp, int pos ) {
        String[] lines = temp.split("\n");
        String ret = "";
        int i = 0;
        for (; i < lines.length; i++) {
            String line = lines[i];
            int index = line.indexOf(":");
            ret += line.replaceFirst("(.*):(.*)", Strings.spaces(pos-index)+"$1:$2")+"\n";
            
        }
        
        return ret.replaceFirst("\n\n$", "\n");
    }

    public static String firstUp(String toString) {
        
        return toString.toLowerCase().replaceFirst(toString.substring(0, 1), toString.substring(0, 1).toUpperCase());
    }



}
