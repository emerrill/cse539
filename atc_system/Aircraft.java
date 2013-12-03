package atc_system;

import java.util.*;

/**
 * Object that internally represents the aircraft data we have
 */
class Aircraft extends Observable {
    // Some location constants
    public static final int LOCATION_REGION = 2000;
    public static final int LOCATION_GATEWAY = 1999;
    public static final int LOCATION_GLIDESLOPE = 0;
    public static final int LOCATION_LANDED = -1;

    public static final int CLEARANCE_NONE = -2;

    // Data we store about the aircraft
    private Airspace airspace;
    private int id = 0;
    private int location = this.LOCATION_REGION;
    private int clearance = this.LOCATION_GATEWAY;
    private int lastClearance = this.LOCATION_REGION;
    private int availableClearance = this.CLEARANCE_NONE;

    private boolean deviated = false;

    private ArrayList<Integer> locationHistory;
    private ArrayList<Integer> clearanceHistory;

    private int moveDelay = 0;

    /**
     * Aircraft constructor
     *
     * @param   air     The airspace object
     * @param   i       The aircraft id
     * @param   l       The current location of the aircraft
     */
    public Aircraft(Airspace air, int i, int l) {
        this.airspace = air;
        this.locationHistory = new ArrayList<Integer>();
        this.locationHistory.add(l);
        this.clearanceHistory = new ArrayList<Integer>();

        this.id = i;
        this.location = l;
        this.setChanged();
    }

    /**
     * Return the id of the aircraft
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the current location of the aircraft
     */
    public int getLocation() {
        return this.location;
    }

    /**
     * Set the current location of the aircraft
     *
     * @param   l   The location
     */
    public void setLocation(int l) {
        boolean changed = false;
        if (this.location != l) {
            changed = true;
        }
        this.location = l;

        // We move the last clearance so detect upwards deviations
        this.lastClearance = l;
        this.setChanged();
        if (changed) {
            this.locationHistory.add(l);
            this.notifyObservers("clearaircraft");
        } else {
            this.notifyObservers();
        }
    }

    /**
     * Return the current clearance of the aircraft
     */
    public int getClearance() {
        return this.clearance;
    }

    /**
     * Return the previous clearance of the aircraft
     */
    public int getLastClearance() {
        return this.lastClearance;
    }

    /**
     * Set the deviation status of the aircraft
     *
     * @param   d   The diviation status, true = deviated
     */
    public void setIsDeviated(boolean d) {
        boolean prev = this.deviated;
        this.deviated = d;
        if (prev != this.deviated) {
            this.setChanged();
            this.notifyObservers();
        }
    }

    /**
     * Return the deviation status
     */
    public boolean getIsDeviated() {
        return this.deviated;
    }

    /**
     * Set the clearance for the aircraft
     *
     * @param   c   The new clearance
     */
    public void setClearance(int c) {
        this.lastClearance = this.clearance;
        this.clearanceHistory.add(c);
        this.clearance = c;
        this.availableClearance = this.CLEARANCE_NONE;

        if (this.location != this.clearance) {
            this.moveDelay = (int)(Math.random() * 10);
        }

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Set an available clearnace
     *
     * @param   c   A clearance that is available
     */
    public void setAvailableClearance(int c) {
        this.availableClearance = c;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Return the current available clearance
     */
    public int getAvailableClearance() {
        return this.availableClearance;
    }

    /**
     * Get the history report of the aircraft
     */
    public void getHistory() {
        System.out.println("Aircraft History");
        // TODO
    }
}
