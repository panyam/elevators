
package com.panyam;

public class StandardElevator implements Elevator {
    private  Status _status;
    private int _currFloor = 1;
    private volatile int _requestedFloor = -1;
    private int _ticksPerFloor = 10;
    private int _currTick = 0;

    StandardElevator() {
        _status = Status.IDLE;
    }

    @Override
    public Status getStatus() {
        return _status;
    }

    @Override
    public int getLastFloor() {
      return _currFloor;
    }

    @Override
    public void gotoFloor(int floor) {
        synchronized (this) {
            _requestedFloor = floor;
            this.notify();
        }
    }

    @Override
    public int getRequestedFloor() {
        return _requestedFloor;
    }

    public void run() {
        while (true) {
            synchronized (this) {
                while (_requestedFloor < 0) {
                    try {
                        this.wait(50);
                    } catch (InterruptedException exc) {
                        // ...
                    }
                }
            }
            if (_requestedFloor >= 1) {
                // move one tick closer to the requested floor
                if (_requestedFloor == _currFloor) {
                    // Reached the floor
                    _currTick = 0;
                    _status = Status.IDLE;
                } else if (_requestedFloor > _currFloor) {
                    _status = Status.MOVING_UP;
                    _currTick++;
                    if (_currTick == _ticksPerFloor) {
                        _currFloor++;
                        _currTick = 0;
                    }
                } else {
                    _status = Status.MOVING_DOWN;
                    _currTick--;
                    if (_currTick < 0) {
                        _requestedFloor--;
                        _currTick += _ticksPerFloor;
                    }
                }
            }
        }
    }
}

