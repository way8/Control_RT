
/**
 *
 * @author HP_
 */

package control_rt;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;

public class LinearActuator {
    
    
        private int dir;

        /**
         * @param dir the dir to set
         */
        public void setDir(int dir) {
            this.dir = dir;
        }
    
            
        public void la_move(String[] args) throws InterruptedException {

    
        // create GPIO controller instance
        GpioController gpio = GpioFactory.getInstance();

        // All Raspberry Pi models support a hardware PWM pin on GPIO_01.
        // Raspberry Pi models A+, B+, 2B, 3B also support hardware PWM pins: GPIO_23, GPIO_24, GPIO_26
        //
        // by default we will use gpio pin #01; however, if an argument
        // has been provided, then lookup the pin by address
        Pin pin = CommandArgumentParser.getPin(
                RaspiPin.class,    // pin provider class to obtain pin instance from
                RaspiPin.GPIO_01,  // default pin if no pin argument found
                args);             // argument array to search in

        GpioPinPwmOutput pwm = gpio.provisionPwmOutputPin(pin);

        // you can optionally use these wiringPi methods to further customize the PWM generator
        // see: http://wiringpi.com/reference/raspberry-pi-specifics/
        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
        com.pi4j.wiringpi.Gpio.pwmSetRange(1024);
        com.pi4j.wiringpi.Gpio.pwmSetClock(500);

      
         final GpioPinDigitalOutput[] pins = {
              gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.HIGH),
              gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.HIGH)};
        
        
        // set the PWM rate to 500
        pwm.setPwm(800);
         System.out.println("PWM rate is: " + pwm.getPwm());

         
        switch (dir) {
     case 1: 
        System.out.println("Move backward");
         pins[0].low();
         pins[1].high();
         break;
     case 2: 
        System.out.println("Move forward");
         pins[0].high();
         pins[1].low();
         break;
        default:
         System.out.println("Nie rozumiem");
}
                                           
        Thread.sleep(2000);
        
        //turn off PWM
        pwm.setPwm(0);
        System.out.println("Koniec");
        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpio.shutdown();
    }

    
}   
    

