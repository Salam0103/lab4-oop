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

        // Initialize the UI first
        cc.frame = new CarView("CarSim 1.0", cc);

        // Add cars after the frame is initialized
        cc.addCar(VehicleFactory.createVehicle("volvo240"));
        cc.addCar(VehicleFactory.createVehicle("saab95"));
        cc.addCar(VehicleFactory.createVehicle("scania"));

        // Start the timer
        cc.timer.start();
    }


    public void addCar(Vehicle vehicle) {
        if (vehicleManager.getCars().size() < 10) { // Check if there are fewer than 10 cars
            int y = vehicleManager.getCars().size() * 100; // Position cars vertically
            vehicle.setPosition(0, y); // Set initial position
            while (vehicle.getDirection() != 1) { // Ensure the car is facing east
                vehicle.turnLeft();
            }
            vehicleManager.addCar(vehicle); // Add the car to the list
            frame.getDrawPanel().moveit(vehicle, 0, y); // Update the DrawPanel
        }
    }


    public void removeCar(Vehicle vehicle) {
        if (vehicle != null && vehicleManager.getCars().contains(vehicle)) {
            vehicleManager.removeCar(vehicle); // Remove the car from the list
            frame.getDrawPanel().removeVehicle(vehicle); // Update the DrawPanel
        }
    }


    public void startAllVehicles() {
        for (Vehicle vehicle : vehicleManager.getCars()) {
            if (vehicle.getState() instanceof StoppedState) {
                vehicle.startEngine(); // Start the engine of each car
            }
        }
    }


    public void stopAllVehicles() {
        for (Vehicle vehicle : vehicleManager.getCars()) {
            if (vehicle.getState() instanceof StartedState) {
                vehicle.stopEngine(); // Stop the engine of each car
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


    public List<Vehicle> getCars() {
        return vehicleManager.getCars();
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