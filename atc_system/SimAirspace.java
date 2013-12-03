package atc_system;

import java.lang.Math;
import java.util.*;

/**
 * The simulated airspace
 */
class SimAirspace {
    private Hashtable<Integer, SimAircraft> airspace;

    /**
     * Simulated airspace constructor
     */
    public SimAirspace() {
        this.airspace = new Hashtable<Integer, SimAircraft>();
    }

    /**
     * Add an aircraft to the airspace
     *
     * @param   aircraft    The aircraft to add
     */
    public boolean addAircraft(SimAircraft aircraft) {
        this.airspace.put(aircraft.getId(), aircraft);

        return true;
    }

    /**
     * Get an aircraft in the airspace
     *
     * @param   id      Aircraft id
     */
    public SimAircraft getAircraft(int id) {
        return (SimAircraft)this.airspace.get(id);
    }

    /**
     * Get all the aircraft in the airspace
     */
    public SimAircraft[] getAllAircraft() {
        Enumeration<SimAircraft> aircraftEnum = this.airspace.elements();
        ArrayList<SimAircraft> aircraftList = Collections.list(aircraftEnum);
        SimAircraft[] aircraftArray = new SimAircraft[0];
        aircraftArray = aircraftList.toArray(aircraftArray);

        return aircraftArray;
    }

    /**
     * Get all aircraft at a particular location/level
     *
     * @param   loc     The location to search
     */
    public SimAircraft[] getAircraftAtLocation(int loc) {
        ArrayList<SimAircraft> list = new ArrayList<SimAircraft>();

        Enumeration<SimAircraft> aircraftEnum = this.airspace.elements();

        while (aircraftEnum.hasMoreElements()) {
            SimAircraft aircraft = aircraftEnum.nextElement();
            if (aircraft.getLocation() == loc) {
                list.add(aircraft);
            }
        }
        
        SimAircraft output[] = new SimAircraft[list.size()];
        output = list.toArray(output);

        return output;
    }

    /**
     * Get all aircraft with a particular clearance
     *
     * @param   loc     The location of clearance to search
     */
    public SimAircraft[] getAircraftWithClearance(int loc) {
        ArrayList<SimAircraft> list = new ArrayList<SimAircraft>();

        Enumeration<SimAircraft> aircraftEnum = this.airspace.elements();

        while (aircraftEnum.hasMoreElements()) {
            SimAircraft aircraft = aircraftEnum.nextElement();
            if (aircraft.getClearance() == loc) {
                list.add(aircraft);
            }
        }
        
        SimAircraft output[] = new SimAircraft[list.size()];
        output = list.toArray(output);

        return output;
    }
}
