package atc_system;

import java.lang.Math;

import java.util.*;

class SimAirspace {
    private Hashtable<Integer, SimAircraft> airspace;

    public SimAirspace() {
        this.airspace = new Hashtable<Integer, SimAircraft>();
    }

    public boolean addAircraft(SimAircraft aircraft) {
        this.airspace.put(aircraft.getId(), aircraft);

        return true;
    }

    public SimAircraft getAircraft(int id) {
        return (SimAircraft)this.airspace.get(id);
    }

    public SimAircraft[] getAllAircraft() {
        Enumeration<SimAircraft> aircraftEnum = this.airspace.elements();
        ArrayList<SimAircraft> aircraftList = Collections.list(aircraftEnum);
        SimAircraft[] aircraftArray = new SimAircraft[0];
        aircraftArray = aircraftList.toArray(aircraftArray);

        return aircraftArray;
    }

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