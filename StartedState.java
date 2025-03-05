public class StartedState implements VehicleState{
    @Override
    public void startEngine(Vehicle vehicle) {
        System.out.println("Engine has already started");
    }

    @Override
    public void stopEngine(Vehicle vehicle) {
        vehicle.transitionToState(new StoppedState());
        System.out.println("Engine stopped");
    }
}
