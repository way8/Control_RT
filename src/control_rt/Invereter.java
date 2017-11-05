/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control_rt;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.util.Console;

/**
 *
 * @author szieba
 */
public class Invereter {
    
         /**
     * [ARGUMENT/OPTION "--invPin (#)" | "-p (#)" ]
 This example program accepts an optional argument for specifying the GPIO invPin (by number)
 to use with this GPIO listener example. If no argument is provided, then GPIO #1 will be used.
 -- EXAMPLE: "--invPin 4" or "-p 0".
     *
     * @param args
     * @throws InterruptedException
     */
    public static void invMove(String[] args) throws InterruptedException {

    

        // create GPIO controller instance, 
        //GpioController invGpio deklaracja obiektu invGpio, jest typu interfejsu GpioController, 
        //GpioFactory.getInstance(); - wywoĹ‚anie statycznej metody getInstance() z klasy GpioFactory
        GpioController invGpio = GpioFactory.getInstance();
        
        final GpioPinDigitalOutput[] pins = {
              invGpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW), // STA   20
invGpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.HIGH), // STP  21
invGpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW)};// F/R   22
      
        // All Raspberry Pi models support a hardware PWM invPin on GPIO_01.
        // Raspberry Pi models A+, B+, 2B, 3B also support hardware PWM pins: GPIO_23, GPIO_24, GPIO_26
        //
        // by default we will use invGpio invPin #01; however, if an argument
        // has been provided, then lookup the invPin by address
        Pin invPin = CommandArgumentParser.getPin(
                RaspiPin.class,    // invPin provider class to obtain invPin instance from
                RaspiPin.GPIO_01,  // default invPin if no invPin argument found
                args);             // argument array to search in

        GpioPinPwmOutput invPwm = invGpio.provisionPwmOutputPin(invPin);

        // you can optionally use these wiringPi methods to further customize the PWM generator
        // see: http://wiringpi.com/reference/raspberry-pi-specifics/
        // pwmFrequency in Hz = 19.2e6 Hz / pwmClock / pwmRange
        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
        com.pi4j.wiringpi.Gpio.pwmSetRange(1000); //1024 is default
        com.pi4j.wiringpi.Gpio.pwmSetClock(100);  //divisor

              
         // set the PWM rate to 500, 1024==1
         invPwm.setPwm(512);
         pins[0].high();
         Thread.sleep(10);
         
         pins[0].low();
         pins[1].high();
         pins[2].low();        
        
         Thread.sleep(20000);
       
   // set the PWM rate to 500, 1000==1
         invPwm.setPwm(80);
        
         Thread.sleep(15000);
         
         pins[1].low();
         invPwm.setPwm(0);
        
         Thread.sleep(3000);   
     

      
        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        invGpio.shutdown();
    }
    
}
