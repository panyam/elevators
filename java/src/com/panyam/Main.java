package com.panyam;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
  public final static int NUM_ELEVATORS = 5;
  public final static int NUM_FLOORS = 50;

  public static void main(String[] args) {
    Scheduler sc = new Scheduler(NUM_ELEVATORS, NUM_FLOORS, null);
    sc.start();


    // Do something here "for ever" while the scheduler is running
  }
}
