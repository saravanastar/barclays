package com.ask.barclays.exercise1.po;

import java.io.Serializable;

/**
 *
 */
public class Flight implements Serializable {

    private String id;
    private String destination;
    private String time;

    public Flight(String id, String destination, String time) {
        this.id = id;
        this.destination = destination;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
