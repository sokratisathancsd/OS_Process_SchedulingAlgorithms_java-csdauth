/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_version2;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Athan
 */
public class SJFScheduler {
    private int nextSwitch;
    private NewProcessTemporaryList newProcessTempList;
    private ReadyProcessList processList;
    private CPU cpu;
    private Clock clock;
    private ArrayList<Process> terminatedProcesses;
    private boolean isPreemptive;
    
    public SJFScheduler(boolean isPreemptive) throws FileNotFoundException, IOException{
        this.isPreemptive=isPreemptive;
        newProcessTempList=new NewProcessTemporaryList();
        processList=new ReadyProcessList();
        cpu=new CPU();
        clock=new Clock();
        terminatedProcesses=new ArrayList<>();
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
            while(!temp.isEmpty()){//taksinomisi tis newProcess temp list ws pros to arrival time
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
        
        newProcessTempList.printList();
        if(this.isPreemptive){
            boolean flag=false;
            int showTime=1;
            while(!newProcessTempList.isEmpty() || !processList.isEmpty()){
                while(!newProcessTempList.isEmpty() && newProcessTempList.getFirst().getArrivalTime()>clock.Show_Time() && processList.isEmpty()){//perimenw na erthei i 1i diergasia
                    clock.Time_Run();
                }
                flag=addProcessesToReadyList();//is is true if a process is added to readylist(so we might have an interrupt)
                
                if(processList.size()==1){
                    cpu.addProcess(processList.get(0), clock.Show_Time());//we add the process to run in cpu if its only one
                    cpu.getRunningProcess().setProcessState(2);//process RUnning
                    if(cpu.getRunningProcess().getPid()!=cpu.getPreviousRunningProcess().getPid()){//means we have a switch
                             showTime=1;
                        }
                }
                else if(flag){//we check for interrupt
                    int position=addTheRightProcess();
                    if(position!=-1){
                        cpu.addProcess(processList.getProcessToRunInCpu(position), clock.Show_Time());//vazoume tin process na treksei stin cpu
                        cpu.getRunningProcess().setProcessState(2);//process RUnning
                        if(cpu.getRunningProcess().getPid()!=cpu.getPreviousRunningProcess().getPid()){//means we have a switch
                             showTime=1;
                        }
                    }
                }
                else{//apla pare ap tis enapominantes diergasies auti me to mikrotero burst  kai trekse tin
                    int position=addTheRightProcess();
                    if(position!=-1){
                        cpu.addProcess(processList.getProcessToRunInCpu(position), clock.Show_Time());
                        cpu.getRunningProcess().setProcessState(2);//process RUnning
                        if(cpu.getRunningProcess().getPid()!=cpu.getPreviousRunningProcess().getPid()){//means we have a switch
                             showTime=1;
                        }
                    }
                }
                processList.printList();
                flag=false;
                if(showTime==1){
                    System.out.println("_____"+clock.Show_Time());
                    showTime=0;
                            
                }
                
                clock.Time_Run();
                cpu.execute();
                for(int i=0;i<processList.size();i++){
                    if(processList.get(i).getState()==1){//means process in readyState, so it is waiting, we have waiting time , for statistics below
                        processList.get(i).waits();
                        
                    }
                }
                for(int i=0;i<processList.size();i++){
                    processList.get(i).setProcessState(1);
                }
                
                for(int i=0;i<processList.size();i++){
                    if(processList.get(i).getRemainingTime()==0){
                        terminatedProcesses.add(processList.get(i));
                        processList.get(i).setProcessState(3);//process terminated
                        processList.remove(i);
                        terminatedProcesses.get(terminatedProcesses.size()-1).setProcessTerminatedTime(clock.Show_Time());//we set a Process Terminated Time for statistics below
                    }
                }
            }
            System.out.println("_____"+clock.Show_Time());
            System.out.println("DO YOU WANT TO CALCULATE STATISTICS? (1 for y, 0 for no)");
            select=sc.nextInt();
            if(select==1){
                calculateStatisticsP();
            }
            else if(select==0){
                return;
            }
        }
            
        
            
        
        else if(!this.isPreemptive){//an den einai proekxwrisimos o SJF
            while(!newProcessTempList.isEmpty() || !processList.isEmpty()){//prepei na adeiasoun kai oi duo stacks gia na teleiwsei o algorithmiki diadikasia
           
            while(!newProcessTempList.isEmpty() && newProcessTempList.getFirst().getArrivalTime()>clock.Show_Time() && processList.isEmpty()){//perimenw na erthei i 1i diergasia
                clock.Time_Run();
            }
            /**
             * This part adds processes to ready list
             */
            while(!this.newProcessTempList.isEmpty() && this.newProcessTempList.getFirst().getArrivalTime()<=clock.Show_Time()){
            this.processList.addProcess(newProcessTempList.getFirst());
            this.newProcessTempList.remove(0);
             
            }
            int min=processList.get(0).getRemainingTime();//psaxnoume tin process me to mirkotero cpubursttime (to cbu burst time kai remaining time einai to idio (edw))
            int position=0;
            for(int i=0;i<processList.size();i++){
                if(processList.get(i).getRemainingTime()<min){
                    position=i;
                }
            }
            processList.get(position).setServiceTime(clock.Show_Time());//used for statistics
            cpu.addProcess(processList.get(position), clock.Show_Time());
            while(cpu.getRunningProcess().getRemainingTime()>0){
                cpu.execute();
                clock.Time_Run();
            }
            cpu.removeProcessFromCpu();
            terminatedProcesses.add(processList.get(position));
            terminatedProcesses.get(terminatedProcesses.size()-1).setProcessTerminatedTime(clock.Show_Time());
            processList.remove(position);
            System.out.println("_______"+clock.Show_Time());
            }   
            System.out.println("DO YOU WANT TO CALCULATE STATISTICS? (1 for y, 0 for no)");
        select=sc.nextInt();
        if(select==1){
        calculateStatisticsNONP();
        }
        else if(select==0){
            return;
        }
        }
        
        
    }
    
    private void calculateStatisticsP() throws IOException{
        try(FileWriter fw = new FileWriter("outputSJFPreemptiveFile.txt", true);
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter out = new PrintWriter(bw)){
             out.print("TOTAL NUMBER OF PROCESSES:"+terminatedProcesses.size());
             int totalWaitingTime=0;
            for(int i=0;i<terminatedProcesses.size();i++){
                totalWaitingTime+=terminatedProcesses.get(i).getWaitingTime();
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
            
            
            
            
    private void calculateStatisticsNONP() throws IOException{//statistics for non-preemptive sjf
        try(FileWriter fw = new FileWriter("outputSJFnonPreemptiveFile.txt", true);
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

    private boolean addProcessesToReadyList() {
       
        
        boolean flag=false;
        
        while(!newProcessTempList.isEmpty() && newProcessTempList.getFirst().getArrivalTime()<=clock.Show_Time()){
            processList.addProcess(newProcessTempList.getFirst());
            processList.get(processList.size()-1).setProcessState(1);//set process to ready
            newProcessTempList.remove(0);
             flag=true;
        }
        
        
        return flag;//an epistrepsei true simainei oti mpike kapoia, kai einai pithanon na exoume kapoia diakopi!
       
        
    }

    private int addTheRightProcess() {
     
        if(!processList.isEmpty()){
        int min=this.processList.get(0).getRemainingTime();
        int position=0;
        for(int i=0;i<processList.size();i++){
            if(processList.get(i).getRemainingTime()<min){
                min=processList.get(i).getRemainingTime();
                position=i;
            }
        }
        return position;
        
        
    }
        return -1;//means ready list is empty
    
}
}
