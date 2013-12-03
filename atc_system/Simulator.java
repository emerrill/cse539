package atc_system;

import java.lang.Math;
import java.util.*;

/**
 * The primary simulator object
 */
class Simulator {
    private SimRadar radar;
    private SimAirspace airspace;
    private ATCSystem atc;
    private SimWindow window;

    /**
     * The constructor
     */
    public Simulator() {
        airspace = new SimAirspace();
        radar = new SimRadar(airspace);
        this.window = new SimWindow(this);
	}

    /**
     * Set the ATCSystem object
     *
     * @param   at      The ATCSystem object
     */
    public void setATCSystem(ATCSystem at) {
        this.atc = at;
        this.radar.setATCSystem(this.atc);
    }

    /**
     * Step the simulator through time
     */
    public void step()
    {
        // Randomly add a new aircraft to the region.
        double val = Math.random();
        if (val > .95) {
            //this.addAircraft();
        }

        // Update aircraft locations.
        SimAircraft[] results = this.airspace.getAllAircraft();
        for (int i = 0; i < results.length; i++) {
            results[i].updateLocation();
        }

        // Move a region aircraft into the gateway.
        results = this.airspace.getAircraftAtLocation(Aircraft.LOCATION_GATEWAY);
        if (results.length == 0) {
            results = this.airspace.getAircraftWithClearance(Aircraft.LOCATION_GATEWAY);
            if (results.length == 0) {
                results = this.airspace.getAircraftWithClearance(Aircraft.LOCATION_REGION);
                if (results.length > 0) {
                    SimAircraft aircraft = results[0];
                    aircraft.setClearance(Aircraft.LOCATION_GATEWAY);
                }
            }
        }

        // Move glide aircraft to landed.
        results = this.airspace.getAircraftAtLocation(Aircraft.LOCATION_GLIDESLOPE);
        if (results.length > 0) {
            results[0].setClearance(Aircraft.LOCATION_LANDED);
        }
    }

    /**
     * Set the clearance for an aircraft
     *
     * @param   id      The aircraft id
     * @param   clear   The clearance to issue
     */
    public void setClearanceForAircraft(int id, int clear) {
        SimAircraft aircraft = this.airspace.getAircraft(id);
        this.setClearanceForAircraft(aircraft, clear);
    }

    /**
     * Set the clearance for an aircraft
     *
     * @param   id      The aircraft object
     * @param   clear   The clearance to issue
     */
    public void setClearanceForAircraft(SimAircraft a, int clear) {
        a.setClearance(clear);
    }

    /**
     * Add a new aircraft to the simulation
     */
    public void addAircraft() {
        int id = (int)(Math.random() * 1000);
        SimAircraft newAircraft = new SimAircraft(id);
        airspace.addAircraft(newAircraft);
    }

    /**
     * List all the airspace aircraft to the console
     */
    public void listAircraft() {
        SimAircraft[] all = this.airspace.getAllAircraft();

        for (int i = 0; i < all.length; i++) {
            System.out.print(all[i].getId());
            System.out.print("   ");
            System.out.print(all[i].getLocation());
            System.out.println(" ");
        }
    }

    /**
     * Send a radar update
     */
    public void sendRadarUpdate() {
        radar.sendUpdate();
    }
}
