public class StoppedState implements VehicleState{
    @Override
    public void startEngine(Vehicle vehicle) {
        vehicle.setState(new StartedState());
        System.out.println("Engine started.");
    }

    @Override
    public void stopEngine(Vehicle vehicle) {
        System.out.println("Engine is already stopped.");
    }
}
