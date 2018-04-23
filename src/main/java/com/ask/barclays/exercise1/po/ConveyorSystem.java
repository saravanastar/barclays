package com.ask.barclays.exercise1.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConveyorSystem implements Serializable {

    private Map<String,Gate> availableGates;

    public ConveyorSystem() {
        availableGates = new HashMap<String,Gate>();
    }

    public Map<String, Gate> getAvailableGates() {
        return availableGates;
    }

    public void setAvailableGates(Map<String, Gate> availableGates) {
        this.availableGates = availableGates;
    }
}
