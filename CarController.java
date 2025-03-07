import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarController {
    private final int delay = 50; // Timer delay
    private Timer timer; // Timer for animation
    private CarView frame; // The main UI frame
    private final VehicleManager vehicleManager = new VehicleManager(); // Manages vehicles
    private final VolvoWorkshop volvoWorkshop = new VolvoWorkshop(5); // Workshop for Volvo cars
    private static CarController instance; // Singleton instance

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
        vehicleManager.startAllVehicles();
    }

    public void stopAllVehicles() {
        vehicleManager.stopAllVehicles();
    }

    public void gas(int amount) {
        vehicleManager.gas(amount);
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
            // Pass DrawPanel and VolvoWorkshop to moveAllVehicles
            vehicleManager.moveAllVehicles(frame.getDrawPanel(), volvoWorkshop);

            // Update the vehicle's position in the UI
            for (Vehicle car : vehicleManager.getCars()) {
                int x = (int) Math.round(car.getX());
                int y = (int) Math.round(car.getY());
                frame.getDrawPanel().moveit(car, x, y);
            }
            frame.getDrawPanel().repaint(); // Repaint the panel after updating all cars
        }
    }

    public VehicleManager getVehicleManager() {
        return vehicleManager;
    }

    public CarView getDrawPanel() {
        return frame;
    }
}