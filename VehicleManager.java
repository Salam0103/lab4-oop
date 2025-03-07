import java.util.ArrayList;
import java.util.List;

public class VehicleManager {
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final int maxWidth = 800;
    private final int maxHeight = 800;
    private final int carWidth = 100;
    private final int carHeight = 60;

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
            if (vehicle.getState() instanceof StoppedState) {
                vehicle.startEngine();
            }
        }
    }

    public void stopAllVehicles() {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getState() instanceof StartedState) {
                vehicle.stopEngine();
            }
        }
    }

    public void gas(int amount) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getState() instanceof StartedState) {
                vehicle.gas(amount);
            }
        }
    }

    public void brake(int amount) {
        for (Vehicle vehicle : vehicles) {
            vehicle.brake(amount);
        }
    }

    public void removeCar(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public void moveAllVehicles(DrawPanel drawPanel, VolvoWorkshop workshop) {
        List<Vehicle> toRemove = new ArrayList<>();

        for (Vehicle car : new ArrayList<>(vehicles)) {
            car.move();
            checkBounds(car);

            // Check for collisions with the workshop
            if (drawPanel.checkWorkshopCollision(car)) {
                if (car instanceof Volvo240) {
                    try {
                        workshop.loadCar((Volvo240) car);
                        toRemove.add(car);
                        drawPanel.removeVehicle(car); // Remove the car from the DrawPanel
                        System.out.println("Volvo loaded into Workshop!");
                    } catch (IllegalStateException ex) {
                        System.out.println("Workshop is full. Cannot load more cars.");
                    }
                }
            }
        }

        // Remove vehicles after iteration
        vehicles.removeAll(toRemove);
    }

    private void checkBounds(Vehicle car) {
        switch (car.getDirection()) {
            case 1: // East
                if (car.getX() + carWidth > maxWidth) {
                    car.setPosition(maxWidth - carWidth, car.getY());
                    reverseDirection(car);
                }
                break;
            case 3: // West
                if (car.getX() < 0) {
                    car.setPosition(0, car.getY());
                    reverseDirection(car);
                }
                break;
        }
    }

    private void reverseDirection(Vehicle car) {
        for (int i = 0; i < 2; i++) {
            car.turnLeft();
        }
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