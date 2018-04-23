package com.ask.barclays.exercise1.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Gate implements Serializable {

    private String name;
    private Integer costFromOrigin;
    private List<Gate> connectedGates;
    private List<Flight> flights;
    private boolean isVisited = false;

    public Gate(String gateName) {
        this.name = gateName;
    }

    public Gate(String gateName, int costFromOrigin) {
        this.name = gateName;
        this.costFromOrigin = costFromOrigin;
    }

    public Integer getCostFromOrigin() {
        return costFromOrigin;
    }

    public void setCostFromOrigin(Integer costFromOrigin) {
        this.costFromOrigin = costFromOrigin;
    }

    public List<Gate> getConnectedGates() {
        return Optional.ofNullable(connectedGates).orElse(new ArrayList<>());
    }

    public void setConnectedGates(List<Gate> connectedGates) {
        this.connectedGates = connectedGates;
    }

    public List<Flight> getFlights() {
        return Optional.ofNullable(flights).orElse(new ArrayList<>());
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
