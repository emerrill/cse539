package atc_system;

import java.awt.event.*;

/**
 * The TMAInterface provides connection between command buttons and the ATCSystem/Simulator
 */
public class TMAInterface implements ActionListener {
    private Airspace airspace;
    private Simulator simulator;
    private ATCSystem atc;

    /**
     * TMAInterface constructor.
     *
     * @param   a       The ATCSystem object
     * @param   air     The Airspace object
     * @param   sim     The Simulator object
     */
    public TMAInterface(ATCSystem a, Airspace air, Simulator sim) {
        this.airspace = air;
        this.simulator = sim;
        this.atc = a;
    }

    /**
     * The actionPerformed method needed for ActionListener.
     *
     * @param   e   ActionEvent object
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().startsWith("request")) {
            // Request new clearance in request:airid format
            String[] parts = e.getActionCommand().split(":");
            this.atc.requestClearanceForAircraft(Integer.parseInt(parts[1]));
        } else if (e.getActionCommand().startsWith("approve")) {
            // Approve a clearance in approve:aircraft:level format
            String[] parts = e.getActionCommand().split(":");
            this.atc.setClearanceForAircraft(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            this.simulator.setClearanceForAircraft(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        } else if (e.getActionCommand().startsWith("report")) {
            // Provide a report based on report:aircraft format
            String[] parts = e.getActionCommand().split(":");
            this.atc.getAircraftReport(Integer.parseInt(parts[1]));
        }
    }
}
