
class ElevatorState {
    enum Status {
        UP,
        DOWN,
        STOPPED,
        FUCKED
    };

    public int currentFloor;
    public Status currentStatus;
}

interface ElevatorInterface {
    public int getCapacity();
    public int getNumFloors();
    public ElevatorState getCurrentState();
    public FloorSelector getFloorSelector();
}

class Request {
    public int targetFloor;
    public Optional<ElevatorInterface> elevator;

    public Request(int floor, ElevatorInterface e) {
        targetFloor = floor;
        elevator = e;
    }
}

class Elevator implements ElevatorInterface {
    public int capacity;
    public int numFloors;
    public ElevatorState currentState;
    public FloorSelector floorSelector;

    Elevator(Scheduler scheduler, FloorSelector floorSelector, int numFloors, int capacity) {
        this.scheduler = scheduler;
        this.numFloors = numFloors;
        this.capacity = capacity;
    }

    public void run() {
        while (true) {
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public FloorSelector getFloorSelector() {
        return floorSelector;
    }

    public ElevatorState getCurrentState() {
        return currentState;
    }

    public synchronized boolean schedule(int floor) {
        if (getCurrentStatus().currentStatus != FUCKED) {
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

interface ElevatorSelector {
    public Elevator selectElevator(int floor);
}

interface FloorSelector {
    public void put(int floor);
    public int nextFloor();
}

class Scheduler implements ElevatorSelector {
    List elevators;
    public Scheduler(int numElevators, int numFloors) {
        elevators = IntStream.range(0, numElevators)
                        .map(new Elevator(this, numFloors))
                        .collect(Collectors::asList);
    }

    public synchronized void request(Request request) {
        assert(!request.elevator.isPresent());
        submitFloorRequest(request, MAX_TRIES);
    }

    public void submitFloorRequest(Request request, int triesLeft) {
        if (triesLeft > 0) {
            ElevatorInterface bestElevator = elevatorStrategy.selectElevator(request.targetFloor);
            if (bestElevator != null) {
                Future<boolean> future = executor.submit(() -> {
                    return bestElevator.schedule(request.floor);
                });
                future.get(1, TimeUnit.SECONDS);
                if (!future.isDone()) {
                    submitFloorRequest(request, triesLeft - 1);
                }
            }
        } else {
            // Send notification to admin
        }
    }

    public Elevator selectElevator(int floor) {
        ElevatorInterface bestElevator = null;
        int minCost = Integer.MAX_VALUE;
        for (ElevatorInterface elevator : elevators) {
            if (el.getCurrentStatus().currentStatus != FUCKED) {
                int cost = el.getFloorSelector().costFor(request.targetFloor);
                if (cost < minCost) {
                    minCost = cost;
                    bestElevator = el;
                }
            }
        }
    }
}

public class System {
    public static void main (String args[]) {
        Scheduler sc = new Scheduler(5, 50);
        sc.start();

        while (true) {
            // Something happens here
        }
    }
}
