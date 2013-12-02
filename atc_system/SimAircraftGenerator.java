package atc_system;

import java.lang.Math;

class SimAircraftGenerator {
    public SimAircraft makeAircraft() {
        int id = (int)(Math.random() * 1000);
        return new SimAircraft(id);
    }
}