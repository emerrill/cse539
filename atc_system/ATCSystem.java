package atc_system;

import java.util.*;

class ATCSystem {
    private Airspace airspace;
    private ATCWindow window;
    private TMAInterface tma;
    private Simulator simulator;

    public ATCSystem(Simulator sim) {
        this.simulator = sim;

        this.airspace = new Airspace();
        tma = new TMAInterface(this, this.airspace, this.simulator);

        this.window = new ATCWindow(tma);        
        this.airspace.addObserver(this.window);
        
    }

    public int requestClearanceForAircraft(int id) {
        Aircraft aircraft = this.airspace.getAircraft(id);
        return this.requestClearanceForAircraft(aircraft);
    }

    public int requestClearanceForAircraft(Aircraft a) {
        int loc = a.getLocation();
        int aClear = this.airspace.getNextSlot(loc);
        a.setAvailableClearance(aClear);

        return aClear;
    }

    public void setClearanceForAircraft(int id, int clear) {
        Aircraft aircraft = this.airspace.getAircraft(id);
        this.setClearanceForAircraft(aircraft, clear);
    }

    public void setClearanceForAircraft(Aircraft a, int clear) {
        a.setClearance(clear);
    }

    public void radarUpdate(int id, int location) {
        this.airspace.updateAircraft(id, location);
    }

    public void setSimulator(Simulator sim) {
        this.simulator = sim;
    }

    public void listAircraft() {
        Aircraft[] all = this.airspace.getAllAircraft();

        for (int i = 0; i < all.length; i++) {
            System.out.print(all[i].getId());
            System.out.print("   ");
            System.out.print(all[i].getLocation());
            System.out.println(" ");
        }
    }

    public Airspace getAirspace() {
        return this.airspace;
    }


    public void getAircraftReport(int id) {
        Aircraft aircraft = this.airspace.getAircraft(id);
        this.getAircraftReport(aircraft);
    }

    public void getAircraftReport(Aircraft a) {
        a.getHistory();
    }

}