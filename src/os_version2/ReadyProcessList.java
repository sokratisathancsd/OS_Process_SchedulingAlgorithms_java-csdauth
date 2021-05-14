/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_version2;

import java.util.ArrayList;

/**
 * Lista stin opoia topothetountai oi etoimes Ready diergasies
 * @author Athan
 */
public class ReadyProcessList {
    private ArrayList<Process> processList;
    
    /**
     * Constructor
     */
    public ReadyProcessList(){
        processList=new ArrayList<>();
    }
    
    /**
     * Prosthiki mias neas etoimis diergasias sti lista
     */
    public void addProcess(Process process){
        process.setProcessState(1);
        processList.add(process);
    }
    
    /**
     * Epistrofi tis diergasias tis opoias einai i seira na ektelestei sti CPU sumfwna me ton ekastote algorithmo dromologisis
     * 
     */
    public Process getProcessToRunInCpu(int i){
        processList.get(i).setProcessState(2);
        return processList.get(i);
        
    }
    
    public boolean isEmpty(){
        return processList.isEmpty();
    }
    
    public int size(){
        return processList.size();
    }
    
    public ArrayList<Process> getList(){
        return processList;
    }
    
    /**
     * Ektupwsi tis listas
     */
    public void printList(){
        System.out.println("DIERGASIES STIN READYPROCESSLIST");
        for(int i=0;i<processList.size();i++){
            
            System.out.println("PID: "+processList.get(i).getPid()+
                               " ARRIVAL TIME: "+processList.get(i).getArrivalTime()+
                               " CPUBURSTTIME: "+processList.get(i).getRemainingTime());
        }
    }
    
    public void remove(int i){
        processList.remove(i);
    }
    public Process get(int i){
        return processList.get(i);
    }
}
