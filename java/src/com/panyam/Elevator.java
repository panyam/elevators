
package com.panyam;

public interface Elevator {
    class State {
        enum Status {
            UP,
            DOWN,
            STOPPED,
            FUCKED
        };

        public int currentFloor;
        public Status currentStatus;
    }

    public int getCapacity();
    public int getNumFloors();
    public State getCurrentState();
    // public FloorSelector getFloorSelector();
}


