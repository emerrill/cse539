package atc_system;

import java.lang.Math;
import java.util.*;

class Simulator {
    private SimAircraftGenerator generator;
    private SimRadar radar;
    private SimAirspace airspace;
    private ATCSystem atc;
    private SimWindow window;

    public Simulator()
	{
        generator = new SimAircraftGenerator();
        airspace = new SimAirspace();
        radar = new SimRadar(airspace);
        this.window = new SimWindow(this);
	}

    public void setATCSystem(ATCSystem at) {
        this.atc = at;
        this.radar.setATCSystem(this.atc);
    }

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
        results = this.airspace.getAircraftAtLocation(SimAircraft.LOCATION_GATEWAY);
        if (results.length == 0) {
            results = this.airspace.getAircraftWithClearance(SimAircraft.LOCATION_GATEWAY);
            if (results.length == 0) {
                results = this.airspace.getAircraftWithClearance(SimAircraft.LOCATION_REGION);
                if (results.length > 0) {
                    SimAircraft aircraft = results[0];
                    aircraft.setClearance(SimAircraft.LOCATION_GATEWAY);
                }
            }
        }

        // Move glide aircraft to landed.
        results = this.airspace.getAircraftAtLocation(SimAircraft.LOCATION_GLIDESLOPE);
        if (results.length > 0) {
            results[0].setClearance(SimAircraft.LOCATION_LANDED);
        }
    }

    public void setClearanceForAircraft(int id, int clear) {
        SimAircraft aircraft = this.airspace.getAircraft(id);
        this.setClearanceForAircraft(aircraft, clear);
    }

    public void setClearanceForAircraft(SimAircraft a, int clear) {
        a.setClearance(clear);
    }

    public void addAircraft() {
        SimAircraft newAircraft = generator.makeAircraft();
        airspace.addAircraft(newAircraft);
    }

    public void listAircraft() {
        SimAircraft[] all = this.airspace.getAllAircraft();

        for (int i = 0; i < all.length; i++) {
            System.out.print(all[i].getId());
            System.out.print("   ");
            System.out.print(all[i].getLocation());
            System.out.println(" ");
        }
    }

    public void sendRadarUpdate() {
        radar.sendUpdate();
    }
}