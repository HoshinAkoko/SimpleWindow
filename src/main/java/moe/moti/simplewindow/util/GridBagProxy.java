package moe.moti.simplewindow.util;

import java.awt.*;

public class GridBagProxy {
    private final GridBagConstraints constraints;
    public GridBagProxy() {
        constraints = new GridBagConstraints();
    }
    public GridBagProxy(GridBagConstraints constraints) {
        this.constraints = constraints;
    }
    public GridBagConstraints getConstraints() {
        return constraints;
    }
    public GridBagProxy setGridx(int gridx) {
        constraints.gridx = gridx;
        return this;
    }
    public GridBagProxy setGridy(int gridy) {
        constraints.gridy = gridy;
        return this;
    }
    public GridBagProxy setGridwidth(int gridwidth) {
        constraints.gridwidth = gridwidth;
        return this;
    }
    public GridBagProxy setGridheight(int gridheight) {
        constraints.gridheight = gridheight;
        return this;
    }
    public GridBagProxy setWeightx(double weightx) {
        constraints.weightx = weightx;
        return this;
    }
    public GridBagProxy setWeighty(double weighty) {
        constraints.weighty = weighty;
        return this;
    }
    public GridBagProxy setAnchor(int anchor) {
        constraints.anchor = anchor;
        return this;
    }
    public GridBagProxy setFill(int fill) {
        constraints.fill = fill;
        return this;
    }
    public GridBagProxy setInsets(Insets insets) {
        constraints.insets = insets;
        return this;
    }
    public GridBagProxy setIpadx(int ipadx) {
        constraints.ipadx = ipadx;
        return this;
    }
    public GridBagProxy setIpady(int ipady) {
        constraints.ipady = ipady;
        return this;
    }
}
