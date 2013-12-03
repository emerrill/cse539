package atc_system;

import java.lang.Math;

/**
 * A simulated aircraft
 */
class SimAircraft {
    private int id = 0;
    private int location = Aircraft.LOCATION_REGION;
    private int clearance = Aircraft.LOCATION_REGION;

    private int moveDelay = 0;

    /**
     * Simulated aircraft constructor
     *
     * @param   u       Aircraft id
     */
    public SimAircraft(int i) {
        this.id = i;
    }

    /**
     * Get the aircraft if
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the aircraft location
     */
    public int getLocation() {
        return this.location;
    }

    /**
     * Get the current clearance of the aircraft
     */
    public int getClearance() {
        return this.clearance;
    }

    /**
     * Set the aircraft clearance
     *
     * @param   c       Clearance
     */
    public void setClearance(int c) {
        this.clearance = c;

        if (this.location != this.clearance) {
            this.moveDelay = (int)(Math.random() * 5);
        }
    }

    /**
     * Step the location
     */
    public int updateLocation() {
        if (this.location != this.clearance) {
            if (this.moveDelay == 0) {
                this.location = this.clearance;
            } else {
                this.moveDelay--;
            }
        }
        return this.location;
    }
}
