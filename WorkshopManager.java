public class WorkshopManager {
    private final Workshop<Vehicle> workshop;

    public WorkshopManager(int maxCapacity) {
        this.workshop = new Workshop<>(maxCapacity);
    }

    public void loadCar(Vehicle vehicle) {
        workshop.loadCar(vehicle);
    }

    public Vehicle unloadCar() {
        return workshop.unloadCar();
    }

    public int getNumberOfCars() {
        return workshop.getNumberOfCars();
    }
}