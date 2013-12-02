package atc_system;

import java.lang.Math;


class SimAircraft {
    public static final int LOCATION_REGION = 2000;
    public static final int LOCATION_GATEWAY = 1999;
    public static final int LOCATION_GLIDESLOPE = 0;
    public static final int LOCATION_LANDED = -1;

    private int id = 0;
    private int location = SimAircraft.LOCATION_REGION;
    private int clearance = SimAircraft.LOCATION_REGION;
    private int lastClearance = SimAircraft.LOCATION_REGION;

    private int moveDelay = 0;

    public SimAircraft(int i) {
        this.id = i;
    }

    public int getId() {
        return this.id;
    }

    public int getLocation() {
        return this.location;
    }

    public int getClearance() {
        return this.clearance;
    }

    public int getLastClearance() {
        return this.lastClearance;
    }

    public void setClearance(int c) {
        this.lastClearance = this.clearance;
        this.clearance = c;

        if (this.location != this.clearance) {
            this.moveDelay = (int)(Math.random() * 5);
        }
    }

    public int updateLocation() {
        //double r = Math.random();

        if (this.location != this.clearance) {
            if (this.moveDelay == 0) {
                //System.out.println(""+this.clearance);
                this.location = this.clearance;
            } else {
                this.moveDelay--;
            }
        }

        return this.location;
    }

}