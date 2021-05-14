/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_version2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Athan
 */
public class OS_VERSION2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        System.out.println("Select Algorithm (1 for FCFS, 2 for SJF)");
         Scanner sc = new Scanner(System.in);
         int select=sc.nextInt();
         if(select==1){
             FCFSScheduler fcfs= new FCFSScheduler();
         }
         else if(select==2){
            boolean preemptive=false;
            System.out.println("press 1 for non-Preemptive, 2 for preemptive");
            select=sc.nextInt();
            
            if(select==1){
                 preemptive=false;
            }
            else if(select==2){
                 preemptive=true;
            }
            
            SJFScheduler sjf=new SJFScheduler(preemptive);
        }
         else{
             System.out.println("Invalid Input given");
         }
    }
    
}
