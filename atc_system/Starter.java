package atc_system;

import java.io.*;

/**
 * The Starter class is used to setup the ATCSystem and the Simulator classes
 */
class Starter {

    /**
     * Main system function
     *
     * @param   args    Command line arguments (not used)
     */
    public static void main(String [] args)
	{
        Simulator sim = new Simulator();        
        ATCSystem atc = new ATCSystem(sim);
        
        sim.setATCSystem(atc);
        
        // Some commands that can be used from the command line.
        InputStreamReader isr = new InputStreamReader ( System.in );
        BufferedReader br = new BufferedReader ( isr );
        String s = null;
        int t;
        try {
            while ( (s = br.readLine ()) != null ) {
                //System.out.println(s);
                if (s.equals("s")) {
                    sim.step();
                } else if (s.equals("a")) {
                    sim.addAircraft();
                } else if (s.equals("l")) {
                    sim.listAircraft();
                } else if (s.equals("L")) {
                    atc.listAircraft();
                } else if (s.equals("r")) {
                    sim.sendRadarUpdate();
                }
            }
        }
        catch ( IOException ioe ) {
            // won't happen too often from the keyboard
        }
    }
}
