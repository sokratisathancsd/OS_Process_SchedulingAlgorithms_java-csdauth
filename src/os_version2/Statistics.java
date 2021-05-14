/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_version2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Athan
 */
public class Statistics {
    private float avgWaitingTime;
    private int totalWaitingTime;
    private float avgResponseTime;
    private int totalNumberOfProcesses;
    private File outputFile;
    
    public Statistics(File fileName) throws IOException{
        BufferedWriter out=null;
        outputFile=fileName;
        FileWriter fstream = new FileWriter(outputFile, true); //true tells to append data.
        out = new BufferedWriter(fstream); 
   }
}
