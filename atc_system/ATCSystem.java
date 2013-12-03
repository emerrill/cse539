package atc_system;

import java.util.*;

/**
 * The ATCSystem, which is main system class
 */
class ATCSystem {
    private Airspace airspace;
    private ATCWindow window;
    private TMAInterface tma;
    private Simulator simulator;

    /**
     * ATCSystem constructor
     *
     * @param   sim     The simulator object
     */
    public ATCSystem(Simulator sim) {
        this.simulator = sim;

        this.airspace = new Airspace();
        tma = new TMAInterface(this, this.airspace, this.simulator);

        this.window = new ATCWindow(tma);        
        this.airspace.addObserver(this.window);
        
    }

    /**
     * Look for an available clearance for an aircraft based on id
     *
     * @param   id      The aircraft id
     */
    public int requestClearanceForAircraft(int id) {
        Aircraft aircraft = this.airspace.getAircraft(id);
        return this.requestClearanceForAircraft(aircraft);
    }

    /**
     * Look for an available clearance for an aircraft object
     *
     * @param   a   The aircraft object
     */
    public int requestClearanceForAircraft(Aircraft a) {
        int loc = a.getLocation();
        int aClear = this.airspace.getNextSlot(loc);
        a.setAvailableClearance(aClear);

        return aClear;
    }

    /**
     * Set the clearance for an airspace
     *
     * @param   id      Aircraft id
     * @param   clear   Clearance
     */
    public void setClearanceForAircraft(int id, int clear) {
        Aircraft aircraft = this.airspace.getAircraft(id);
        this.setClearanceForAircraft(aircraft, clear);
    }

    /**
     * Set the clearance for an airspace
     *
     * @param   a       Aircraft object
     * @param   clear   Clearance
     */
    public void setClearanceForAircraft(Aircraft a, int clear) {
        a.setClearance(clear);
    }

    /**
     * Receive a radar update
     *
     * @param   id          Aircraft id
     * @param   location    Location
     */
    public void radarUpdate(int id, int location) {
        this.airspace.updateAircraft(id, location);
    }

    /**
     * Set the simulator object
     *
     * @param   sim     Simulator object
     */
    public void setSimulator(Simulator sim) {
        this.simulator = sim;
    }

    /**
     * List all the aircraft to the console
     */
    public void listAircraft() {
        Aircraft[] all = this.airspace.getAllAircraft();

        for (int i = 0; i < all.length; i++) {
            System.out.print(all[i].getId());
            System.out.print("   ");
            System.out.print(all[i].getLocation());
            System.out.println(" ");
        }
    }

    /**
     * Get the airspace object
     */
    public Airspace getAirspace() {
        return this.airspace;
    }

    /**
     * Get and aicraft report
     *
     * @param   id      Aircraft id
     */
    public void getAircraftReport(int id) {
        Aircraft aircraft = this.airspace.getAircraft(id);
        this.getAircraftReport(aircraft);
    }

    /**
     * Get and aicraft report
     *
     * @param   a       Aircraft object
     */
    public void getAircraftReport(Aircraft a) {
        a.getHistory();
    }
}
