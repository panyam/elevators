
package com.panyam;

import java.util.Collection;
import java.util.List;
import java.util.Set;


public interface RequestMatcher {
    /**
     * Called to queue the next best floor from the collection of remaining floors
     * into the most suitable elevator.
     * @param elevators
     * @param requests
     * @return True if a match was successfully performed, otherwise returns false.
     */
    public boolean matchElevator(List<Elevator> elevators, Set<Request> requests);
}

