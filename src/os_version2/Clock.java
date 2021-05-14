/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_version2;

/**
 * Anaparista to roloi tou sustimatos
 * @author Athan
 */
public class Clock {
    protected static int ticks;//apothikeuei ton trexon arithmo xtupwn rologiou pou exoun sumvei
    
    /**
     * Constructor
     */
    public Clock(){
        ticks=0;
    }
    
    /**
     * Auksisi twn xtupwn tou rologiou (+1)
     */
    public void Time_Run(){
        ticks++;
    }
    
    /**
     * Epistrofi tis wras vasei tis metavlitis ticks
     */
    public int Show_Time(){
        return ticks;
    }
    
    public void setTime(int time){
        ticks=time;
    }
}
