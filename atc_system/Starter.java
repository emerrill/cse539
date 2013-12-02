package atc_system;

import java.io.*;

class Starter {



    public static void main(String [] args)
	{
        Simulator sim = new Simulator();        
        ATCSystem atc = new ATCSystem(sim);
        
        sim.setATCSystem(atc);
        

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

