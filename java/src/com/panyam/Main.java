package com.panyam;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Main {
  public final static int NUM_ELEVATORS = 5;
  public final static int NUM_FLOORS = 50;

  public static void main(String[] args) {
    List<Elevator> elevators = IntStream.range(0, NUM_ELEVATORS)
            .mapToObj(val -> new StandardElevator())
            .collect(Collectors.toList());

    // Start the elevators - keep them on standby!
    ExecutorService executor = Executors.newFixedThreadPool(elevators.size());
    elevators.stream().forEach(elevator -> executor.submit(elevator));

    Scheduler sc = new Scheduler(elevators, NUM_FLOORS, null);
    sc.start();

    // Do something here "for ever" while the scheduler is running
  }
}
