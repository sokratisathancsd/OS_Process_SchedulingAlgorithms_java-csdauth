/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_version2;

/**
 * Paristanei tin monada epeksergasias CPU tou sustimatos
 * @author Athan
 */
public class CPU {
    private Process runningProcess;//diergasia pou xrisimopoiei tin cpu tin trexousa stigmi
    private int timeToNextContextSwitch;//periexei tin xroniki stigmi tis epomenis diakopis
    private int lastProcessStartTime;//periexei tin xroniki stigmi enarksis tis teleutaias diergasias
    private Process previousRunningProcess;
    
    /**
     * Constructor
     */
    public CPU(){
        timeToNextContextSwitch=0;
       lastProcessStartTime=0;
       runningProcess=new Process(-2,-2,-2);
       previousRunningProcess=new Process(-1,-1,-1);
    }
    
    /**
     * Eisagwgi tis neas diergasias pros ektelesi sti CPU
     */
    public void addProcess(Process process, int time){
        previousRunningProcess=runningProcess;
        lastProcessStartTime=time;
        runningProcess=process;
        
    }
    public Process getPreviousRunningProcess(){
        return previousRunningProcess;
    }
    
    /**
     * Eksagwgi tis trexousas diergasias apo ti CPU
     */
    public void removeProcessFromCpu(){
        runningProcess=null;
    }
    
    /**
     * Ektelesi tis diergasias me antistoixi meiwsi tou xronou ektelesis tis
     */
    public void execute(){
        if(runningProcess==null){
            return;
        }
        runningProcess.changeCpuTotalTime();
        runningProcess.changeCpuRemainingTime();
    }
    
    public Process getRunningProcess(){
        return runningProcess;
    }
}
