import java.util.ArrayList;
import java.util.List;

public class Workshop<T extends Vehicle> {
    private final int maxCapacity;
    private final List<T> vehicles;

    public Workshop(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.vehicles = new ArrayList<>();
    }
    public void loadCar(T vehicle) {
        if (vehicles.size() >= maxCapacity) { // Call size() on the list, not on the car
            throw new IllegalStateException("Workshop is full");
        }
        vehicles.add(vehicle);
    }
    public T unloadCar() {
        if (vehicles.isEmpty()) {
            throw new IllegalStateException("No cars in the workshop");
        }
        return vehicles.remove(vehicles.size()-1);

    }

    public int getNumberOfCars() {
        return vehicles.size();
    }
}
