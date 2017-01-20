
package com.panyam;

import java.util.Date;


public class Request {
    // Which floor the request is for
    private int _floor;

    // Which elevator the request has been made from (if request was made from inside an elevator)
    // This cannot be changed once set
    private Elevator _elevator;

    // The elevator assigned for this request
    private Elevator _assignedElevator;

    // What time the elevator was assigned to this request
    // This is used to track things like timeouts to see if a request
    // needs to be reassigned - this could result in an elevator stopping at
    // multiple floors but that is not a big deal
    private Date _assignedAt;

    public Request(int floor, Elevator from) {
        _floor = floor;
        _elevator = from;
    }

    public int floor() { return _floor; }
    public Elevator elevator() { return _elevator; }
    public Elevator assignedElevator() { return _assignedElevator; }
    public Date elevatorAssignedAt() { return _assignedAt; }

    @Override
    public boolean equals(Object another) {
        if (another == this) return true;
        if (another == null || !(another instanceof  Request)) return false;
        Request r = (Request) another;
        return (r.floor() == floor() && r.elevator() != elevator());
    }

    @Override
    public int hashCode() {
        return floor();
    }
}

