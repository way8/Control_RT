/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control_rt;

/**
 *
 * @author HP_
 */
public class Control_RT {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        
        LinearActuator lin = new LinearActuator();
        lin.setDir(1);
        lin.la_move(args);
    }
    
}
