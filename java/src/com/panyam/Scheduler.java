
package com.panyam;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Scheduler implements ElevatorSelector {
    private List<Elevator> elevators;
    private int maxTries = 5;

    public Scheduler(int numElevators, int numFloors) {
        elevators = IntStream.range(0, numElevators)
                        .map(new StandardElevator(this, numFloors))
                        .collect(Collector::asList);
    }

    public synchronized void request(int floor) {
        submitFloorRequest(floor, MAX_TRIES);
    }

    private void submitFloorRequest(int floor, int triesLeft) {
        if (triesLeft > 0) {
            Elevator bestElevator = elevatorStrategy.selectElevator(request.targetFloor);
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
        Elevator bestElevator = null;
        int minCost = Integer.MAX_VALUE;
        for (Elevator el: elevators) {
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
