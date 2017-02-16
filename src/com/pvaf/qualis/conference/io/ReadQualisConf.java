/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pvaf.qualis.conference.io;

import com.pvaf.qualis.conference.exceptions.ErrorException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import org.apache.log4j.Logger;

/**
 *
 * @author douglas
 */
public class ReadQualisConf {
    
    private final String path;
    private Set<String> conferencesQualis = new TreeSet<>();
    private final static Logger log = Logger.getLogger(ReadQualisConf.class);

    public ReadQualisConf(String path) {
        this.path = path;
    }
    
    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @return the conferencesQualis
     */
    public Set<String> getConferencesQualis() {
        return conferencesQualis;
    }

    public void redQualis() throws ErrorException {
        
        try{            
            File file = new File(path);
            WorkbookSettings ws = new WorkbookSettings();
            ws.setEncoding("Cp1252");
            
            Workbook workbook = Workbook.getWorkbook(file,ws);
            String [] sheetNames = workbook.getSheetNames();
            Sheet sheet = null;
            
            for(int sheetNumber = 1; sheetNumber < sheetNames.length; sheetNumber++){
                sheet = workbook.getSheet(sheetNames[sheetNumber]);
                
                int linhas = sheet.getRows();
                
                Cell cell = sheet.findCell("Sigla");
                
                cell.getRow();
                                
                for(int i = cell.getRow() +1; i < linhas; i++){
                    
                    int column = cell.getColumn();
                    Cell a1 = sheet.getCell(column++, i);
                    Cell a2 = sheet.getCell(column++, i);
                    column++;
                    Cell a3 = sheet.getCell(column++, i);
                    
                    byte array[] = a1.getContents().getBytes("UTF-8");
                    String sigla = new String(array,"UTF-8");
                    sigla = sigla.replaceAll(";", "");
                    sigla = sigla.trim();
                                        
                    array = a2.getContents().getBytes("UTF-8");
                    String title = new String(array,"UTF-8");
                    title = title.replaceAll(";","");
                    title = title.trim();
                    
                    array = a3.getContents().getBytes("UTF-8");
                    String classification = new String(array,"UTF-8");
                    classification = classification.toUpperCase();
                    classification = classification.replaceAll(";","");
                    classification =  classification.trim();
                    
                    String line = sigla+";"+title+";"+classification;
                    conferencesQualis.add(line);
                }
            }
            workbook.close();
            
        } catch (FileNotFoundException f) {
            log.error("Arq. nao existe.", f.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        } catch (IOException e) {
            log.error("Erro de E/S.", e.fillInStackTrace());
            throw new ErrorException("Ocorreu um Erro Interno");
        } catch (BiffException ex) {
            log.error("Erro de Leitura do arquivo biff.", ex.fillInStackTrace());
            throw new ErrorException("Salve no Formato Microsoft Excel (.xls)");
        }
    }
}
