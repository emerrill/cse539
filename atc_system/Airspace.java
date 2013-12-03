package atc_system;

import java.util.*;

/**
 * Object to hold and handle all the aircraft in the system
 */
class Airspace extends Observable {
    // The data structure for storing the aircraft
    private Hashtable<Integer, Aircraft> airspace;

    private DeviationObserver devObs;

    /**
     * Airspace constructor
     */
    public Airspace() {
        this.airspace = new Hashtable<Integer, Aircraft>();

        this.devObs = new DeviationObserver();
    }

    /**
     * A method to receive radar updates
     *
     * @param   id          Aircraft id
     * @param   location    Aircraft location
     */
    public void updateAircraft(Integer id, int location) {
        // Check if we already know about this aircraft
        if (!airspace.containsKey(id)) {
            Aircraft aircraft = new Aircraft(this, id, location);
            aircraft.addObserver(this.devObs);
            this.addAircraft(aircraft);
            aircraft.notifyObservers();
        } else {
            Aircraft aircraft = this.getAircraft(id);
            aircraft.setLocation(location);
        }

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Find the next open slot/location after the provided location
     *
     * @param   slot    The slot that we want to search after
     */
    public int getNextSlot(int slot) {
        int nextPlane = Aircraft.LOCATION_LANDED;

        Enumeration<Aircraft> aircraftEnum = this.airspace.elements();

        // Go through all the aircraft and look for the next slot
        while (aircraftEnum.hasMoreElements()) {
            Aircraft aircraft = aircraftEnum.nextElement();
            int loc = aircraft.getLocation();
            if ((loc < slot) && (loc > nextPlane)) {
                nextPlane = loc;
            }
        }

        // If a slot isn't available, send the CLEARANCE_NONE constant
        if ((nextPlane + 1) == slot) {
            return Aircraft.CLEARANCE_NONE;
        } else {
            return (nextPlane + 1);
        }

    }

    /**
     * Add a new aircraft to the dataset
     *
     * @param aircraft      An aircraft object
     */
    private boolean addAircraft(Aircraft aircraft) {
        this.airspace.put(aircraft.getId(), aircraft);

        return true;
    }

    /**
     * Return the aircraft with the supplied ID
     *
     * @param   id      The aircraft id that we want
     */
    public Aircraft getAircraft(int id) {
        return (Aircraft)this.airspace.get(id);
    }

    /**
     * Return all the aircraft in the airspace as an array
     */
    public Aircraft[] getAllAircraft() {
        Enumeration<Aircraft> aircraftEnum = this.airspace.elements();
        ArrayList<Aircraft> aircraftList = Collections.list(aircraftEnum);
        Aircraft[] aircraftArray = new Aircraft[0];
        aircraftArray = aircraftList.toArray(aircraftArray);

        return aircraftArray;
    }

    /**
     * Return all the aircraft at the specified location/slot
     *
     * @param   loc     The location we want to search
     */
    public Aircraft[] getAircraftAtLocation(int loc) {
        ArrayList<Aircraft> list = new ArrayList<Aircraft>();

        Enumeration<Aircraft> aircraftEnum = this.airspace.elements();

        while (aircraftEnum.hasMoreElements()) {
            Aircraft aircraft = aircraftEnum.nextElement();
            if (aircraft.getLocation() == loc) {
                list.add(aircraft);
            }
        }

        Aircraft output[] = new Aircraft[list.size()];
        output = list.toArray(output);

        return output;
    }

    /**
     * Check all open clearances to see if they are still valid.
     * Clear it if not.
     */
    public void checkAvailableClearances() {
        Enumeration<Aircraft> aircraftEnum = this.airspace.elements();

        while (aircraftEnum.hasMoreElements()) {
            Aircraft aircraft = aircraftEnum.nextElement();

            int aClear = aircraft.getAvailableClearance();
            int newAClear = this.getNextSlot(aircraft.getLocation());

            // If the new available clearance is higher that the
            // current one, the current is invalid
            if (newAClear > aClear) {
                aircraft.setAvailableClearance(Aircraft.CLEARANCE_NONE);
            }
        }
    }
}
