
interface ElevatorSelector {
    public Elevator selectElevator(int floor);
}

interface FloorSelector {
    public void put(int floor);
    public int nextFloor();
}

