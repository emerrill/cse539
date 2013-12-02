package atc_system;

import java.util.*;

class SimRadar {
    private SimAirspace airspace;
    private ATCSystem atc;

    public SimRadar(SimAirspace a) {
        this.airspace = a;
    }

    public void setATCSystem(ATCSystem at) {
        this.atc = at;
    }

    public void sendUpdate() {
        SimAircraft[] all = this.airspace.getAllAircraft();

        for (int i = 0; i < all.length; i++) {
            this.atc.radarUpdate(all[i].getId(), all[i].getLocation());
        }
    }
}