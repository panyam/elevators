
package com.panyam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Scheduler implements Runnable {
  private int _numFloors;
  private List<Elevator> elevators;
  // Number of times to retry before a scheduling of a request is given up.
  private int maxTries = 5;
  // Max time to wait for a schedule to go through or fail.
  private int maxTimeout = 5;
  // The Amount of time to sleep in the scheduler loop
  private long sleepDelay = 50;
  private RequestMatcher _requestMatcher;
  private Set<Request> requests;
  ExecutorService _executor;

  public Scheduler(int numElevators, int numFloors, RequestMatcher requestMatcher) {
    _numFloors = numFloors;
    requests = new HashSet<>();
    _executor = Executors.newFixedThreadPool(elevators.size());
    elevators = IntStream.range(0, numElevators)
        .mapToObj(val -> new StandardElevator())
        .collect(Collectors.toList());
    _requestMatcher = requestMatcher;
  }

  /**
   * Called by the user from a particular floor.  The intent is for the next/best available
   * elevator to arrive to the floor from which the request was initiated.
   *
   * @param request The request that is to be queued and eventually satisfied.
   * @return True if the request was successfully queued, otherwise false.
   */
  public synchronized void request(Request request) {
    requests.add(request);
  }

  public void run() {
    // This is the event loop the scheduler runs on
    // At each instance this does an intersection of the elevator state and the list of requests to pick
    // which elevator should be given a particular request (or even differ the decision to a later point in
    // time).
    // The idea is that elevators do not move "that" fast.  or rather they dont move *that* fast within
    // the sleepDelay.  If they do, then the sleepDelay parameter can be tuned for this scheduler for a
    // particular price.
    while (true) {
      try {
        Thread.sleep(sleepDelay);
        synchronized (this) {
          _requestMatcher.matchElevator(elevators, requests);
        }
      } catch (InterruptedException exc) {
      }
    }
  }

  public synchronized void start() {
    elevators.stream().forEach(elevator -> _executor.submit(elevator));
    _executor.submit(this);
  }
}

