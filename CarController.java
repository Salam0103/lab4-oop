import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CarController {
    private final int delay = 50; // Timer delay
    private Timer timer; // Timer for animation
    private CarView frame; // The main UI frame
    private final VehicleManager vehicleManager = new VehicleManager(); // Manages vehicles
    private final VolvoWorkshop volvoWorkshop = new VolvoWorkshop(5); // Workshop for Volvo cars
    private static CarController instance; // Singleton instance
    private final WorkshopManager workshopManager = new WorkshopManager(5);
    public static void main(String[] args) {
        CarController cc = new CarController();

        cc.addCar(VehicleFactory.createVehicle("volvo240"));
        cc.addCar(VehicleFactory.createVehicle("saab95"));
        cc.addCar(VehicleFactory.createVehicle("scania"));

        // Initialize the UI and start the timer
        cc.frame = new CarView("CarSim 1.0", cc);
        cc.timer.start();
    }


    public void addCar(Vehicle vehicle) {
        vehicleManager.addCar(vehicle);
    }


    public List<Vehicle> getCars() {
        return vehicleManager.getCars();
    }


    public void startAllVehicles() {
        // Set each vehicle to the StartedState
        for (Vehicle vehicle : vehicleManager.getCars()) {
            if (vehicle.getState() instanceof StoppedState) {
                vehicle.startEngine();  // This will set the vehicle to StartedState
            }
        }
    }


    public void stopAllVehicles() {
        // Set each vehicle to the StoppedState
        for (Vehicle vehicle : vehicleManager.getCars()) {
            if (vehicle.getState() instanceof StartedState) {
                vehicle.stopEngine();  // This will set the vehicle to StoppedState
            }
        }
    }


    public void gas(int amount) {
        for (Vehicle vehicle : vehicleManager.getCars()) {
            if (vehicle.getState() instanceof StartedState) {
                vehicle.gas(amount);
            }
        }
    }


    public void brake(int amount) {
        vehicleManager.brake(amount);
    }


    public void removeCar(Vehicle vehicle) {
        vehicleManager.removeCar(vehicle);
    }


    public VolvoWorkshop getVolvoWorkshop() {
        return volvoWorkshop;
    }


    public static CarController getInstance() {
        return instance;
    }

    public CarController() {
        instance = this;
        timer = new Timer(delay, new TimerListener()); // Initialize timer with TimerListener
    }


    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Vehicle> toRemove = new ArrayList<>();

            // Create a copy of the vehicles list for iteration
            for (Vehicle car : new ArrayList<>(vehicleManager.getCars())) {
                car.move();
                checkBounds(car);

                // Update the vehicle's position in the UI
                int x = (int) Math.round(car.getX());
                int y = (int) Math.round(car.getY());
                frame.getDrawPanel().moveit(car, x, y);
                frame.getDrawPanel().repaint();

                // Check for collisions with the workshop
                if (frame.getDrawPanel().checkWorkshopCollision(car, volvoWorkshop)) {
                    toRemove.add(car);
                }
            }

            // Remove vehicles after iteration
            vehicleManager.getCars().removeAll(toRemove);
        }
    }


    private void checkBounds(Vehicle car) {
        final int maxWidth = 800;
        final int maxHeight = 800;
        int carWidth = 100;
        int carHeight = 60;

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
    public VehicleManager getVehicleManager() {
        return vehicleManager;
    }
}