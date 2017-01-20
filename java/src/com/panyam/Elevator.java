
package com.panyam;

public interface Elevator extends Runnable {
    public enum Status {
        MOVING_UP,
        MOVING_DOWN,
        IDLE,
        FUCKED
    };

    /**
     * Gets the current status of the elevator.
     *
     * @return
     */
    public Status getStatus();

    /**
     * Returns the last visited floor of the elevator (perhaps as it passed it).
     * Note that if the elevator is stationary then this could mean the last floor
     * it has passed or the requested floor (with gotoFloor).  A third scenario
     * is that the elevator is stuck between floors (requires maintenance or is
     * facing a problem) which will be indicated by the status.
     *
     * @return
     */
    public int getLastFloor();

    /**
     * Makes the elevator go to the particular floor at any given point in time
     * How an elevator achieves this is completely upto the mechanics decided
     * by the elevator designer.   For instance if an elevator is between
     * two floors and this was called, then the elevator may choose to change
     * directions mid floor to hit a new target floor
     *
     * @param floor
     */
    public void gotoFloor(int floor);

    /**
     * Gets the last floor that was requested (via gotoFloor).
     *
     * @return Returns the last requested floor, otherwise -1 if the request was satisfied.
     */
    public int getRequestedFloor();
}


