/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_version2;

import java.util.ArrayList;

/**
 * Lista poy periexei oles tis nees diergasies
 * @author Athan
 */
public class NewProcessTemporaryList {
    
    private ArrayList<Process> newProcessTempList;
    
    /**
     * Constructor
     */
    public NewProcessTemporaryList(){
        newProcessTempList=new ArrayList<>();
    }
    
    /**
     * Eisagwgia mias neas diergasias sti lista
     */
    public void addNewProcess(Process process){
        newProcessTempList.add(process);
    }
    
    /**
     * Epistrofi tis prwtis diergasias tis listas
     */
    public Process getFirst(){
        return newProcessTempList.get(0);
    }
    
    /**
     * Ektupwsi tis listas
     */
    public void printList(){
        System.out.println("DIERGASIES STIN NEWPROCESSTEMPORARYLIST");
        for(int i=0;i<newProcessTempList.size();i++){
            
            System.out.println("PID: "+newProcessTempList.get(i).getPid()+
                               " ARRIVAL TIME: "+newProcessTempList.get(i).getArrivalTime()+
                               " CPUBURSTTIME: "+newProcessTempList.get(i).getRemainingTime());
        }
    }
    
    public int size(){
        return newProcessTempList.size();
    }
    
    public boolean isEmpty(){
        return newProcessTempList.isEmpty();
    }
    
    public Process get(int i){
        return newProcessTempList.get(i);
    }
    
    public void remove(int i){
        newProcessTempList.remove(i);
    }
    
    public ArrayList<Process> getList(){
        return newProcessTempList;
    }
    
    
}
