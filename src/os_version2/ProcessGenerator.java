/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_version2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Athan
 * Simulates a Generator of new Processes
 */
public class ProcessGenerator {
    private File inputFile;//a File that stores data of new Processes
    private BufferedReader in;
    private NewProcessTemporaryList newProcessTempList;
    private int pidCount;
    
   
    
    
    /**
     * If readFile==false creates a File object, else opens opens the inputFile to read
     * @param filename the path of the file
     * @param readFile
     * @throws FileNotFoundException 
     */
    public ProcessGenerator(String filename, boolean readFile) throws FileNotFoundException{
        newProcessTempList=new NewProcessTemporaryList();
        pidCount=0;
        if(readFile==false){
            inputFile=new File(filename);
        }
        else{
            
            in = new BufferedReader(new FileReader(filename));
        }
    }
    
    
    /**
     * Creates new Process with random pid, arrivalTime and cpuBurstTime
     * @return process, the random Process that has been created
     */
    public Process createProcess(){
        Random rand = new Random();
        int pid=pidCount++;
        int arrivalTime=rand.nextInt(20);
        int cpuBurstTime=rand.nextInt(20)+1;
        Process process = new Process(pid,arrivalTime,cpuBurstTime) {};
        return process;
    }
    
    /**
     * Store Process to inputFile
     * @param process the process that will be saved to file.
     * @throws java.io.IOException
     */
    public void storeProcessToFile(Process process) throws IOException{
        
        int pid=process.getPid();
        int arrivalTime=process.getArrivalTime();
        int cpuBurstTime=process.getRemainingTime();
        BufferedWriter out;
	FileWriter fw;
        fw = new FileWriter(inputFile);
	out = new BufferedWriter(fw);
        out.write(pid+" "+arrivalTime+" "+ cpuBurstTime);
	
    }
    
    /**
     * Reads processes from inputFile
     * @throws java.io.IOException
     */
    public void readProcessFromFile() throws IOException{
        String line;
        while((line=in.readLine())!=null){
            String[] parts = line.split(" ");
            String tempPid = parts[0]; 
            String tempArrivalTime = parts[1];
            String tempCpuBurstTime=parts[2];
            int pid=Integer.parseInt(tempPid);
            int arrivalTime=Integer.parseInt(tempArrivalTime);
            int cpuBurstTime=Integer.parseInt(tempCpuBurstTime);
            Process process=new Process(pid, arrivalTime, cpuBurstTime);
            newProcessTempList.addNewProcess(process);
        }
    }
    
    /**
     * Return Processes that have been read from a file
     * @return an ArrayList of processes
     */
    public NewProcessTemporaryList getProcesses(){
        return newProcessTempList;
    }
}
