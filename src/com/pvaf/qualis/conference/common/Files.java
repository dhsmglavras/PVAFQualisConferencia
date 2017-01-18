/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.common;
//comparar removendo as stop words
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
//comparar tambem as siglas




/**
 *
 * @author maneul
 */
public class Files implements Serializable {

        /**
         * Get the content of a file
         * @param filePath the address of file
         * @return A string with the content of a file
         * @throws FileNotFoundException - if the file was not found
         * @throws IOException - if an I/O error occurs
         */
   public static String getContentFile(String filePath) throws FileNotFoundException, IOException{
       
            File fi = new File(filePath);
            FileInputStream fis = new FileInputStream(fi);
            byte [] b = new byte[(int)fi.length()];
            fis.read(b);
            return new String(b);

    }
        /**
         * Get the content of a file
         * @param file  the file
         * @return A string with the content of a file
         * @throws FileNotFoundException - if the file was not found
         * @throws IOException - if an I/O error occurs
         */
   public static String getContentFile(File file) throws FileNotFoundException, IOException{


            FileInputStream fis = new FileInputStream(file);
            byte [] b = new byte[(int)file.length()];
            fis.read(b);

            return new String(b);

    }
   /** get the number of lines in file
    *
    * @param file to get lines
    * @return number of lines in file
    * @throws IOException
    */
   public static int getNumLinesInFile(File file) throws IOException{
           FileInputStream fis = new FileInputStream(file);
            byte [] bs = new byte[(int)file.length()];
             fis.read(bs);
            byte space = '\n';
            int lines = 0;
            for(byte b: bs){
                    if(b == space){
                            ++lines;
                    }
            }
            return lines+1;
   }

   


/**
 * An array with full name of files in path
 * @param path with the files to get full path
 * @return the files with full path name
 */

    public static String[] getFilesPathInFolder(String path) {
        if (!path.endsWith("/")) {
            path += "/";
        }
        String[] files = new File(path).list();
        for (int i = 0; i < files.length; ++i) {
            files[i] = path + files[i];
        }
        return files;
    }

    /**
     * Creat a new file
     * @param path to the new file
     * @param content of the new file
     * @return true if has sucess to creat the file.
     * @throws IOException - if some I/O error occurs
     */
    public static boolean creatNewFile(String path, String content){
        FileWriter fw = null;
        try {
            fw = new FileWriter(new File(path));
            fw.write(content);
            fw.close();
            return true;
        } catch (IOException ex) {
//            Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                return false;
//                Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }

    public static void creatNewFile(String path, byte[] Content) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(path));
        fos.write(Content);
        fos.close();
    }



/**
 * Rename the older file to the newer file
 * @param older the actual name
 * @param newer the new name
 * @return true if sucess, false otherwise
 */
    public boolean rename(String older, String newer) {
        return new File(older).renameTo(new File(newer));
    }



/**
 * Get the number of ocorrences of words in string
 * @param string with the words
 * @return A hash with the word in a key and the number of ocorrences in value
 */
        static HashMap<String, Integer> wordCount(String string) {

                HashMap<String, Integer> hm = new HashMap<String, Integer>();

                Integer i = 0;
                for (String s : string.split(" ")) {
                        if ((i = hm.get(s)) == null) {
                                hm.put(s, 1);
                        } else {
                                hm.put(s, i + 1);
                        }
                }

                return hm;
        }

        /**
         * Creat a file with the object as content
         * @param object to save
         * @param filePath the path of file to save the object
         * @throws IOException
         */
    public static void saveObject(Object object, String filePath) throws IOException {

            File arquivo = new File(filePath);

            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            FileOutputStream fOut = new FileOutputStream(arquivo);
            ObjectOutputStream objOut = new ObjectOutputStream(fOut);
            objOut.writeObject(object);
            fOut.close();

    }

    /**
     * Load an object saved in the file fileName
     * @param fileName with the object
     * @return the object found in file
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object loadObject(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {

            FileInputStream fIn;
            File arquivo = new File(fileName);
            fIn = new FileInputStream(arquivo);
            ObjectInputStream objIn = new ObjectInputStream(fIn);
            return objIn.readObject();

    }

      /**
         * remove as linhas iguais em uma arquivo e salva um terceiro arquivo com as linhas
         * restanetes
         * @param file1
         * @param file2
         * @param file3
         * @throws FileNotFoundException
         * @throws IOException
         */

        public static void removeEqualLines(String file1, String file2, String file3) throws FileNotFoundException, IOException{

                String content1 = Files.getContentFile(file1);
                String content2 = Files.getContentFile(file2);

                for(String line : content1.split("\n")){
                        content2 = content2.replaceFirst(line +"\n", "");
                }

                Files.creatNewFile(file3, content2);

        }

        public static void printNumberWordsFile(String file) throws FileNotFoundException, IOException{
                StringBuilder sb = new StringBuilder();
                /* for(String s: Files.getFilesPathInFolder("/home/maneul/Dropbox/NetBeansProjects/LuceneDemo/XMLs/cfc-xml")){
                sb.append(Files.getContentFile(s).replaceAll("<[^>]+>", " ").replaceAll("\\p{Punct}"," "));
                }*/
                HashMap<String,Integer> has = Files.wordCount(new String(sb));
                TreeMap<Integer, String> tm = new TreeMap<Integer, String>();
                for(String s: has.keySet()){
                        tm.put(has.get(s), s);
                }
                for(Integer i: tm.keySet()){
                        System.out.println(tm.get(i) +": "+i);
                }
        }
        public static void main(String [] args) throws FileNotFoundException, IOException{
            //  String file = "/home/maneul/Dropbox/lixo/lixo";
//                int lines = Files.getNumLinesInFile(new File(file));
           //     int lines = Files.getNumLinesInFile(new File(file));
            //    System.out.println(lines);
        }
  public static HashMap<String, Integer> wordCount(String [] string) {

                HashMap<String, Integer> hm = new HashMap<String, Integer>();

                Integer i = 0;
                for (String s : string) {
                        if ((i = hm.get(s)) == null) {
                                hm.put(s, 1);
                        } else {
                                hm.put(s, i + 1);
                        }
                }

                return hm;
        }

}
class WordCount implements Comparable<Object> {

    String name = null;
    int count = 0;

    public WordCount(String s) {
        name = s;
        count = 1;
    }

    public int compareTo(Object arg0) {
        WordCount b = (WordCount) arg0;
        if (count < b.count) {
            return 1;
        }
        if (count > b.count) {
            return -1;
        }
        return 0;
    }


}

