
/**
 *
 * @author HP_
 */

package control_rt;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;

public class LinearActuator {
    
    
      
        // create GPIO controller instance
        GpioController laGpio = GpioFactory.getInstance();
        Pin laPin = CommandArgumentParser.getPin(
                RaspiPin.class,    // laPin provider class to obtain laPin instance from
                RaspiPin.GPIO_23  // default laPin if no laPin argument found
                );             // argument array to search in

        GpioPinPwmOutput laPwm = laGpio.provisionPwmOutputPin(laPin);



      
         final GpioPinDigitalOutput[] pins = {
              laGpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.HIGH),
              laGpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.HIGH)};
        
        
        
        
        /**
         * @param dir the dir to set
         */
        public void setDir(int dir) {
                    // you can optionally use these wiringPi methods to further customize the PWM generator
        // see: http://wiringpi.com/reference/raspberry-pi-specifics/
        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
        com.pi4j.wiringpi.Gpio.pwmSetRange(1024);
        com.pi4j.wiringpi.Gpio.pwmSetClock(500);
         //   this.dir = dir;
                    // set the PWM rate to 500
        laPwm.setPwm(800);
       //  System.out.println("PWM rate is: " + laPwm.getPwm());

         
        switch (dir) {
     case 1: 
      //  System.out.println("Move backward");
         pins[0].low();
         pins[1].high();
         break;
     case 2: 
     //   System.out.println("Move forward");
         pins[0].high();
         pins[1].low();
         break;
     case 3: 
      //  System.out.println("Stop");
         pins[0].high();
         pins[1].high();
         break;
        default:
                  }
    
        }        
        public void laMove() {
       
       

      //   System.out.println("Nie rozumiem");
}
                                           
       // Thread.sleep(2000);
        
        //turn off PWM
      //  laPwm.setPwm(0);
      //  System.out.println("Koniec");
        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
      //  laGpio.shutdown();
    }

        
  
    

