package atc_system;

import java.awt.event.*;

public class TMAInterface implements ActionListener {
    private Airspace airspace;
    private Simulator simulator;
    private ATCSystem atc;

    public TMAInterface(ATCSystem a, Airspace air, Simulator sim) {
        this.airspace = air;
        this.simulator = sim;
        this.atc = a;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().startsWith("request")) {
            String[] parts = e.getActionCommand().split(":");
            this.atc.requestClearanceForAircraft(Integer.parseInt(parts[1]));
        } else if (e.getActionCommand().startsWith("approve")) {
            String[] parts = e.getActionCommand().split(":");
            this.atc.setClearanceForAircraft(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            this.simulator.setClearanceForAircraft(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        } else if (e.getActionCommand().startsWith("report")) {
            String[] parts = e.getActionCommand().split(":");
            this.atc.getAircraftReport(Integer.parseInt(parts[1]));
        }
    }
}