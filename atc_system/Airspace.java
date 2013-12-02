package atc_system;

import java.util.*;

class Airspace extends Observable {
    private Hashtable<Integer, Aircraft> airspace;

    private DeviationObserver devObs;
    
    public Airspace() {
        this.airspace = new Hashtable<Integer, Aircraft>();

        this.devObs = new DeviationObserver();
    }

    public void updateAircraft(Integer id, int location) {
        if (!airspace.containsKey(id)) {
            Aircraft aircraft = new Aircraft(id, location);
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

    public int getNextSlot(int slot) {
        int nextPlane = Aircraft.LOCATION_LANDED;

        Enumeration<Aircraft> aircraftEnum = this.airspace.elements();

        while (aircraftEnum.hasMoreElements()) {
            Aircraft aircraft = aircraftEnum.nextElement();
            int loc = aircraft.getLocation();
            if ((loc < slot) && (loc > nextPlane)) {
                nextPlane = loc;
            }
        }

        if ((nextPlane + 1) == slot) {
            return Aircraft.CLEARANCE_NONE;
        } else {
            return (nextPlane + 1);
        }

    }

    private boolean addAircraft(Aircraft aircraft) {
        this.airspace.put(aircraft.getId(), aircraft);

        return true;
    }

    public Aircraft getAircraft(int id) {
        return (Aircraft)this.airspace.get(id);
    }

    public Aircraft[] getAllAircraft() {
        Enumeration<Aircraft> aircraftEnum = this.airspace.elements();
        ArrayList<Aircraft> aircraftList = Collections.list(aircraftEnum);
        Aircraft[] aircraftArray = new Aircraft[0];
        aircraftArray = aircraftList.toArray(aircraftArray);

        return aircraftArray;
    }

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

}