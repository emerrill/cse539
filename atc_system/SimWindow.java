package atc_system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * The simulator window interface
 */
public class SimWindow implements ActionListener {
    private JFrame frame;
    private Simulator simulator;
    private javax.swing.Timer timer;

    /**
     * The constructor that builds the window
     *
     * @param   s   The simulator object
     */
    public SimWindow(Simulator s) {
        this.simulator = s;
        this.timer = new javax.swing.Timer(750, this);
        this.timer.setActionCommand("stepradar");

        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setTitle("Simulator");
        this.frame.setSize(500,40);

        Container pane = this.frame.getContentPane();
        pane.setLayout(new GridLayout(1,0));

        JButton button = new JButton("Step");
        button.setActionCommand("step");
        button.addActionListener(this);
        pane.add(button);
        button = new JButton("Step+Radar");
        button.setActionCommand("stepradar");
        button.addActionListener(this);
        pane.add(button);
        button = new JButton("Send Radar");
        button.setActionCommand("radar");
        button.addActionListener(this);
        pane.add(button);
        button = new JButton("Add Aircraft");
        button.setActionCommand("add");
        button.addActionListener(this);
        pane.add(button);
        button = new JButton("Play");
        button.setActionCommand("play");
        button.addActionListener(this);
        pane.add(button);
        button = new JButton("Pause");
        button.setActionCommand("pause");
        button.addActionListener(this);
        pane.add(button);

        frame.setVisible(true);
    }

    /**
     * The ActionListener actionPerformed receiver
     *
     * @param   e       ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        if ("step".equals(e.getActionCommand())) {
            this.simulator.step();
        } else if ("radar".equals(e.getActionCommand())) {
            this.simulator.sendRadarUpdate();
        } else if ("stepradar".equals(e.getActionCommand())) {
            this.simulator.step();
            this.simulator.sendRadarUpdate();
        } else if ("add".equals(e.getActionCommand())) {
            this.simulator.addAircraft();
        } else if ("play".equals(e.getActionCommand())) {
            this.timer.start();
        } else if ("pause".equals(e.getActionCommand())) {
            this.timer.stop();
        }
    }
}
