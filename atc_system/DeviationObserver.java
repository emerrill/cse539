package atc_system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * Observer that monitors for aircraft deviations
 */
public class DeviationObserver implements Observer {
    /**
     * Observer update routine
     *
     * @param   o       Observable Object
     * @param   arg     Supplied argument
     */
    public void update(Observable o, Object arg) {
        if (o instanceof Aircraft) {
            Aircraft aircraft = (Aircraft) o;
            int loc = aircraft.getLocation();
            int clear = aircraft.getClearance();
            int lastClear = aircraft.getLastClearance();

            if (loc >= 0) {
                if ((loc != clear) && (loc != lastClear)) {
                    System.out.println("DEVIATION!"+loc+":"+clear+":"+lastClear);
                    aircraft.setIsDeviated(true);
                } else {
                    aircraft.setIsDeviated(false);
                }
            }
        }
        
    }
}
