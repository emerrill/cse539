package atc_system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ATCWindow implements Observer {
    private static final int STACK_LIMIT = 20;
    private static final int REGION_LIMIT = 8;
    private JTextField regionLabels[];
    private LayoutRow gatewayRow;
    private LayoutRow stackRows[];
    private LayoutRow glideslopeRow;

    private TMAInterface tma;


    private JFrame frame;

    public ATCWindow(TMAInterface inter) {
        this.tma = inter;
        this.frame = new JFrame();

        Container pane = this.frame.getContentPane();
        pane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("ATC System");
        frame.setSize(800,800);
        frame.setLocationRelativeTo(null);

        JButton button = null;

        
        // **********************************
        // Region Frame
        // **********************************
        JFrame region = new JFrame();
        Container regionPane = region.getContentPane();
        regionPane.setLayout(new GridLayout(0, this.REGION_LIMIT));
        regionPane.setBackground(Color.WHITE);

        this.regionLabels = new JTextField[this.REGION_LIMIT];
        for (int i = 0; i < this.REGION_LIMIT; i++) {
            this.regionLabels[i] = new JTextField();
            this.regionLabels[i].setEditable(false);
            region.add(this.regionLabels[i]);
        }

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridy = 0;
        c.gridx = 0;
        frame.add(regionPane, c);

        


        // **********************************
        // Stack Frame
        // **********************************
        JFrame stack = new JFrame();
        Container stackPane = stack.getContentPane();
        stackPane.setLayout(new GridLayout(0,1));

        JFrame labels = new JFrame();
        Container labelsPane = labels.getContentPane();
        labelsPane.setLayout(new GridLayout(1,0));
        JLabel label = new JLabel("Location");
        labels.add(label);
        label = new JLabel("Id");
        labels.add(label);
        label = new JLabel("");
        labels.add(label);
        label = new JLabel("Available");
        labels.add(label);
        label = new JLabel("");
        labels.add(label);
        label = new JLabel("Clearance");
        labels.add(label);
        label = new JLabel("");
        labels.add(label);
        stack.add(labelsPane);

        this.gatewayRow = new LayoutRow("Gateway:", Aircraft.LOCATION_GATEWAY, this.tma);
        stack.add(this.gatewayRow.getContentPane());

        this.stackRows = new LayoutRow[this.STACK_LIMIT];
        for (int i = this.STACK_LIMIT-1; i >= 0; i--) {
            this.stackRows[i] = new LayoutRow((i + 1) + ":", (i+1), this.tma);
            stack.add(this.stackRows[i].getContentPane());
        }

        this.glideslopeRow = new LayoutRow("Glide:", Aircraft.LOCATION_GLIDESLOPE, this.tma);
        stack.add(this.glideslopeRow.getContentPane());

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        c.gridx = 0;
        frame.add(stackPane, c);



        // **********************************
        // Clearance Frame
        // **********************************
        JFrame clearance = new JFrame();
        Container clearancePane = clearance.getContentPane();
        clearancePane.setLayout(new GridLayout(0,3));
        clearancePane.setBackground(Color.WHITE);

        button = new JButton("Button 2");
        //clearance.add(button);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        c.gridx = 1;
        frame.add(clearancePane, c);

        // **********************************
        // Text Frame
        // **********************************
        /*
        JTextArea report = new JTextArea("A\nB",10, 100);
        JScrollPane scrollPane = new JScrollPane(report);
        report.setEditable(false);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridheight = 3;
        
        frame.add(scrollPane, c);*/

        frame.setVisible(true);
    }

    public void update(Observable o, Object arg) {
        if (o instanceof Airspace) {
            Airspace airspace = (Airspace) o;
            Aircraft[] allAircraft = airspace.getAllAircraft();
            this.setAllAircraft(allAircraft);
        }
    }

    public void setAllAircraft(Aircraft[] allAircraft) {
        //this.clearAll();

        int regionCount = 0;

        for (int i = 0; i < allAircraft.length; i++) {
            int loc = allAircraft[i].getLocation();

            if (loc == Aircraft.LOCATION_REGION) {
                if (regionCount < this.REGION_LIMIT) {
                    regionLabels[regionCount].setText(""+allAircraft[i].getId());
                    regionCount++;
                }
            } else if (loc == Aircraft.LOCATION_GATEWAY) {
                this.gatewayRow.setAircraft(allAircraft[i]);
            } else if (loc == Aircraft.LOCATION_GLIDESLOPE) {
                this.glideslopeRow.setAircraft(allAircraft[i]);
            } else if (loc == Aircraft.LOCATION_LANDED) {

            } else if (loc <= this.STACK_LIMIT) {
                this.stackRows[loc-1].setAircraft(allAircraft[i]);
            }
        }

        for (regionCount = regionCount; regionCount < this.REGION_LIMIT; regionCount++) {
            regionLabels[regionCount].setText("");
        }

    }

    class LayoutRow extends JFrame implements Observer {
        private Aircraft aircraft;
        private JLabel label;
        private JLabel idBox;
        private JButton requestButton;
        private JLabel availClearance;
        private JButton approveButton;
        private JLabel clearance;
        private JButton report;
        private TMAInterface tma;
        private int location;

        public LayoutRow(String l, int loc, TMAInterface inter) {
            this.location = loc;
            this.tma = inter;

            Container pane = this.getContentPane();
            pane.setLayout(new GridLayout(1,0));

            // Label
            this.label = new JLabel(l);
            this.add(this.label);

            // ID Box
            this.idBox = new JLabel();
            this.add(this.idBox);

            // Request Button
            this.requestButton = new JButton("Req");
            this.requestButton.setVisible(false);
            this.requestButton.setActionCommand("");
            this.requestButton.addActionListener(this.tma);
            this.add(this.requestButton);

            // Available clearance
            this.availClearance = new JLabel();
            this.add(this.availClearance);

            // Approve Button
            this.approveButton = new JButton("Approve");
            this.approveButton.setVisible(false);
            this.approveButton.setActionCommand("");
            this.approveButton.addActionListener(this.tma);
            this.add(this.approveButton);

            // Clearance
            this.clearance = new JLabel();
            this.add(this.clearance);

            // Report
            this.report = new JButton("Report");
            this.report.setVisible(false);
            this.report.setActionCommand("");
            this.report.addActionListener(this.tma);
            this.add(this.report);
        }

        public void setAircraft(Aircraft a) {
            if (this.aircraft != null) {
                this.aircraft.deleteObserver(this);
            }

            this.aircraft = a;
            this.updateDrawing();
            if (this.aircraft != null) {
                this.aircraft.addObserver(this);
            }
        }

        public void updateDrawing() {
            if (this.aircraft != null) {
                this.idBox.setText("" + this.aircraft.getId());
                this.requestButton.setActionCommand("request:"+this.aircraft.getId());
                this.requestButton.setVisible(true);
                this.clearance.setText(""+this.aircraft.getClearance());
                this.report.setActionCommand("report:"+this.aircraft.getId());
                this.report.setVisible(true);

                int aClear = this.aircraft.getAvailableClearance();
                if (aClear == Aircraft.CLEARANCE_NONE) {
                    this.availClearance.setText("");
                    this.approveButton.setVisible(false);
                    this.approveButton.setActionCommand("");
                } else {
                    this.availClearance.setText("" + aClear);
                    this.approveButton.setVisible(true);
                    this.approveButton.setActionCommand("approve:" + this.aircraft.getId() + ":" + aClear);
                }
            } else {
                this.idBox.setText("");
                this.requestButton.setActionCommand("");
                this.requestButton.setVisible(false);
                this.availClearance.setText("");
                this.clearance.setText("");
                this.report.setActionCommand("");
                this.report.setVisible(false);
            }
        }

        public void update(Observable o, Object a) {
            if (o instanceof Aircraft) {
                this.updateDrawing();
                String arg = (String)a;
                if ("clearaircraft".equals(arg)) {
                    this.setAircraft(null);
                }
            }
        }
    }

}