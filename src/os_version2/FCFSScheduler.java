/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_version2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Algorithmos dromologisis FCFS
 * @author Athan
 */
public class FCFSScheduler {
    private CPU cpu;
    private ArrayList<Process> terminatedProcesses;
    private Clock clock;
    private NewProcessTemporaryList newProcessTempList;
    private ReadyProcessList processList;
    
    public FCFSScheduler() throws FileNotFoundException, IOException{
        cpu= new CPU();
        terminatedProcesses=new ArrayList<>();
        clock=new Clock();
        processList=new ReadyProcessList();
        String inputFile="inputFile.txt";
        ProcessGenerator generator= new ProcessGenerator(inputFile,true);
        System.out.println("1 to read Processes from File, 0 to Generate new Processes");
        Scanner sc = new Scanner(System.in);
         int select=sc.nextInt();
        if(select==1){
             generator.readProcessFromFile();
            newProcessTempList=generator.getProcesses();
        }
        else if(select==0){
            
            newProcessTempList=generateProcesses(generator,4);
            
            NewProcessTemporaryList temp= new NewProcessTemporaryList();
            temp=newProcessTempList;
            newProcessTempList=new NewProcessTemporaryList();
            while(!temp.isEmpty()){//taksinomisi twn processes ws pros to arrival time
                int position=0;
                int min=temp.getFirst().getArrivalTime();
                for(int i=0;i<temp.size();i++){
                    if(temp.get(i).getArrivalTime()<min){
                        min=temp.get(i).getArrivalTime();
                        position=i;
                    }
                }
                newProcessTempList.addNewProcess(temp.get(position));
                temp.remove(position);
                
            }
           
        }
        else{
            System.out.println("invalid input given");
            return;
        }
        newProcessTempList.printList();
        
       
        while(!newProcessTempList.isEmpty()){
            
            processList.addProcess(newProcessTempList.get(0));
            newProcessTempList.remove(0);
           
        }
        processList.printList();
     
        int checkTime=0;
        int counter=0;
        while(!processList.isEmpty()){
            while(!processList.isEmpty() && processList.get(0).getArrivalTime()>clock.Show_Time() ){//perimenw na erthei i 1i diergasia
                clock.Time_Run();
            }
            if(counter==0||checkTime!=clock.Show_Time()){
            System.out.println(clock.Show_Time());
            counter++;
            }
            cpu.addProcess(processList.getProcessToRunInCpu(0),clock.Show_Time());
            processList.get(0).setServiceTime(clock.Show_Time());
            while(cpu.getRunningProcess().getRemainingTime()!=0){
                cpu.execute();
                clock.Time_Run();
            }
            processList.get(0).setProcessState(3);//process terminated
            terminatedProcesses.add(processList.get(0));
            processList.remove(0);
            checkTime=clock.Show_Time();
            System.out.println(clock.Show_Time());
            terminatedProcesses.get(terminatedProcesses.size()-1).setProcessTerminatedTime(clock.Show_Time());
            
        }
        System.out.println("CALCULATE STATISTICS? (1 for y, 0 for no)");
        select=sc.nextInt();
        if(select==0){
            return;
        }
        else if(select==1){
            calculateStatistics();
        }
    
    }
    
    
    
    private void calculateStatistics() throws IOException{
         //BufferedWriter out=null;
        //String outputFile="outputFile.txt";
        try(FileWriter fw = new FileWriter("outputFcfsFile.txt", true);
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter out = new PrintWriter(bw)){
 
        out.print("TOTAL NUMBER OF PROCESSES:"+terminatedProcesses.size());
        int totalWaitingTime=0;
        for(int i=0;i<terminatedProcesses.size();i++){//calculate total waiting time
            int WT=terminatedProcesses.get(i).getServiceTime()-terminatedProcesses.get(i).getArrivalTime();
            totalWaitingTime+=WT;
        }
        out.print(" TOTAL WAITING TIME:"+totalWaitingTime);
        out.print(" AVG WAITING TIME:"+(float)totalWaitingTime/terminatedProcesses.size());
        int responseTime=0;
        for(int i=0;i<terminatedProcesses.size();i++){
            int RT=terminatedProcesses.get(i).processTerminatedTime()-terminatedProcesses.get(i).getArrivalTime();
            responseTime+=RT;
        }
        out.println(" AVG RESPONSE TIME:"+(float)responseTime/terminatedProcesses.size());
    }
    }
    
    private NewProcessTemporaryList generateProcesses(ProcessGenerator generator, int count){
        NewProcessTemporaryList newProcessTempList= new NewProcessTemporaryList();
        for(int i=0;i<count;i++){
            newProcessTempList.addNewProcess(generator.createProcess());
        }
        return newProcessTempList;
    }
    
}
