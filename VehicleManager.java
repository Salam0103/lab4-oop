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
        for (Vehicle car : vehicles) {
            car.startEngine();
        }
    }

    public void stopAllVehicles() {
        for (Vehicle car : vehicles) {
            car.stopEngine();
        }
    }

    public void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Vehicle car : vehicles) {
            car.gas(gas);
        }
    }

    public void brake(int amount) {
        double brake = ((double) amount) / 100;
        for (Vehicle car : vehicles) {
            car.brake(brake);
        }
    }

    public void removeCar(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }
}