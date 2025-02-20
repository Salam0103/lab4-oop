import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;



public class CarController {
    private final int delay = 50;
    private Timer timer = new Timer(delay, new TimerListener());
    private boolean started = false;
    private CarView frame;
    private final List<Vehicle> cars = new ArrayList<>();

    public static void main(String[] args) {
        CarController cc = new CarController();

        cc.addCar(new Volvo240());
        cc.addCar(new Saab95());
        cc.addCar(new Scania());

        cc.frame = new CarView("CarSim 1.0", cc);
        cc.timer.start();
    }

    private void addCar(Vehicle vehicle) {
        int y = cars.size() * 100;
        vehicle.setPosition(0, y);

        while (vehicle.getDirection() != 1) {
            vehicle.turnLeft();
        }

        cars.add(vehicle);
    }

    public List<Vehicle> getCars() {
        return cars;
    }

    public void startAllVehicles() {
        started = true;
        for (Vehicle car: cars) {
            car.startEngine();
        }
    }

    public boolean CarStart() {
        return started;
    }

    public void stopAllVehicles() {
        started = false;
        for (Vehicle car: cars) {
            car.stopEngine();
        }
    }

    public void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Vehicle car : cars) {
            car.gas(gas);
        }
    }

    public void brake(int amount) {
        double brake = ((double) amount)/100;
        for (Vehicle car : cars) {
            car.brake(brake);
        }
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (Vehicle car : cars) {
                car.move();
                checkBounds(car);
                int x = (int) Math.round(car. getX());
                int y = (int) Math.round(car. getY());
                frame.getDrawPanel().moveit(car, x, y);
                frame.getDrawPanel().repaint();
            }
        }
    }

    private void checkBounds(Vehicle car) {
        final int maxWidth = 800;
        final int maxHeight = 800;
        int carWidth = 100;
        int carHeight = 60;

       switch (car.getDirection()) {
           case 1:
               if (car.getX() + carWidth > maxWidth) {
                   car.setPosition(maxWidth - carWidth, car.getY());
                   reverseDirection(car);
               }
               break;
           case 3:
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
}


