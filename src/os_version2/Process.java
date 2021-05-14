/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_version2;

/**
 * Anaparista mia diergasia tou sustimatos
 * @author Athan
 */
public class Process {
    private int processTerminatedTime;
    private int waitingTime;
    private int serviceTime;
    private int pid;
    private int arrivalTime;//xroniki stigmi afiksis diergasias
    private int cpuTotalTime;//periexei ton sunoliko xrono apasxolisis CPU tis diergasias
    private int cpuRemainingTime;//periexei ton enapomeinonta xrono apasxolisis tis CPU apo ti diergasia
    /**
     * 0 Created/New, 1 Ready/Waiting, 2 Running, 3 Terminated
     */
    private int currentState;//periexei tin trexoysa katastasi tis diergasias
    
   
    
    /**
     * Constructor
     */
    public Process(int pid, int arrivalTime, int cpuBurstTime){
        waitingTime=0;
        this.pid=pid;
        this.arrivalTime=arrivalTime;
        cpuRemainingTime=cpuBurstTime;
        cpuTotalTime=0;
        currentState=0;
        
    }
    public void waits(){
        waitingTime++;
    }
    public int getState(){
        return currentState;
    }
    public int getWaitingTime(){
        return waitingTime;
    }
    
    /**
     * Thetei tin katastasi tis diergasias isi me tin parametro newState
     */
    public int setProcessState(int newState){
        currentState=newState;
        return currentState;
    }
    
    /**
     * Allazei ton enapomeinonta xrono apasxolisis tis cpu apo tin diergasia
     */
    public void changeCpuRemainingTime(){
        cpuRemainingTime--;
    }
    
    public int getPid(){
        return pid;
    }
    public int getArrivalTime(){
        return arrivalTime;
    }
    public int getRemainingTime(){
        return cpuRemainingTime;
    }
    
    public void changeCpuTotalTime(){
        cpuTotalTime++;
       
    }
    
    public void setServiceTime(int ST){
        serviceTime=ST;
    }
    public int getServiceTime(){
        return serviceTime;
    }
    
    public void setProcessTerminatedTime(int PTT){
        processTerminatedTime=PTT;
    }
    public int processTerminatedTime(){
        return processTerminatedTime;
    }
    
    
    
    
}
