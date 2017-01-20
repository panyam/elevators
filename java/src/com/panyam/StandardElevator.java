
package com.panyam;

public class StandardElevator implements Elevator {
    private int capacity;
    private int numFloors;
    private Elevator.State currentState;
    private FloorSelector floorSelector;

    StandardElevator(FloorSelector floorSelector, int numFloors, int capacity) {
        this.numFloors = numFloors;
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public FloorSelector getFloorSelector() {
        return floorSelector;
    }

    public Elevator.State getCurrentState() {
        return currentState;
    }

    public synchronized boolean schedule(int floor) {
        if (getCurrentStatus().currentStatus != Elevator.State.FUCKED) {
            floorSelector.put(floor);
            return true;
        }
        return false;
    }

    public int getNumFloors() {
        return numFloors;
    }

    public int currentDirection() {
        return 0;
    }

    public void request(int floor) {
        floorSelector.put(new Request(floor, this));
    }

    public int lastFloor() {
        return -1;
    }
}

