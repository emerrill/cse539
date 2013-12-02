package atc_system;

import java.util.*;

class Aircraft extends Observable {
    public static final int LOCATION_REGION = 2000;
    public static final int LOCATION_GATEWAY = 1999;
    public static final int LOCATION_GLIDESLOPE = 0;
    public static final int LOCATION_LANDED = -1;

    public static final int CLEARANCE_NONE = -2;

    private int id = 0;
    private int location = this.LOCATION_REGION;
    private int clearance = this.LOCATION_GATEWAY;
    private int lastClearance = this.LOCATION_REGION;
    private int availableClearance = this.CLEARANCE_NONE;

    private boolean deviated = false;

    private ArrayList<Integer> locationHistory;
    private ArrayList<Integer> clearanceHistory;

    private int moveDelay = 0;

    public Aircraft(int i, int l) {
        this.locationHistory = new ArrayList<Integer>();
        this.locationHistory.add(l);
        this.clearanceHistory = new ArrayList<Integer>();

        this.id = i;
        this.location = l;
        this.setChanged();
    }

    public int getId() {
        return this.id;
    }

    public int getLocation() {
        return this.location;
    }

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

    public int getClearance() {
        return this.clearance;
    }

    public int getLastClearance() {
        return this.lastClearance;
    }

    public void setIsDeviated(boolean d) {
        boolean prev = this.deviated;
        this.deviated = d;
        if (prev != this.deviated) {
            this.setChanged();
            this.notifyObservers();
        }
    }

    public boolean getIsDeviated() {
        return this.deviated;
    }

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

    public void setAvailableClearance(int c) {
        this.availableClearance = c;
        this.setChanged();
        this.notifyObservers();
    }

    public int getAvailableClearance() {
        return this.availableClearance;
    }

    public void getHistory() {
        System.out.println("Aircraft History");
        // TODO
    }

}