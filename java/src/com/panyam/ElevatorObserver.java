
package com.panyam;

public interface ElevatorObserver {
  /**
   * Called to handle a particular elevator event.
   *
   * @param event     The type of elevator event dispatched.
   * @param elevator  The elevator that caused this event to be dispatched.
   */
  public void handleElevatorEvent(ElevatorEvent event, Elevator elevator);
}


