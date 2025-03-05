import java.util.ArrayList;
import java.util.List;

public class VehicleManager {
    private final List<Vehicle> vehicles = new ArrayList<>();

    public void addCar(Vehicle vehicle) {
        int y = vehicles.size() * 100;
        vehicle.setPosition(0, y);

        while (vehicle.getDirection() != 1) {
            vehicle.turnLeft();
        }

        vehicles.add(vehicle);
    }

    public List<Vehicle> getCars() {
        return vehicles;
    }

    public void startAllVehicles() {
        for (Vehicle vehicle : vehicles) {
            vehicle.startEngine();
        }
    }

    public void stopAllVehicles() {
        for (Vehicle vehicle : vehicles) {
            vehicle.stopEngine();
        }
    }

    public void gas(int amount) {
        double gas = ((double) amount) / 200;
        for (Vehicle vehicle : vehicles) {
            vehicle.gas(gas);
        }
    }

    public void brake(int amount) {
        double brake = ((double) amount) / 100;
        for (Vehicle vehicle : vehicles) {
            vehicle.brake(brake);
        }
    }

    public void removeCar(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public boolean isAnyCarInState(Class<? extends VehicleState> stateClass) {
        for (Vehicle vehicle : vehicles) {
            if (stateClass.isInstance(vehicle.getState())) {
                return true;
            }
        }
        return false;
    }

}