import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;



public class CarController {
    private final int delay = 50;
    private Timer timer = new Timer(delay, new TimerListener());

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
        cars.add(vehicle);
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (Vehicle car : cars) {
                car.move();
                checkBounds(car);
                int x = (int) Math.round(car. getX());
                int y = (int) Math.round(car. getY());
                frame.drawPanel.moveit(car, x, y);
                frame.drawPanel.repaint();
            }
        }
    }

    private void checkBounds(Vehicle car) {
        final int maxWidth = 800;

        if (car.getX() > maxWidth) {
            car.setPosition(maxWidth, car.getY());
            reverseDirection(car);
        } else if (car.getX() < 0) {
            car.setPosition(0, car.getY());
            reverseDirection(car);
        }
    }

    private void reverseDirection(Vehicle car) {
        for (int i = 0; i < 2; i++) {
            car.turnLeft();
        }
    }

    public void brake(int amount) {
        double brake = ((double) amount)/100;
        for (Vehicle car : cars) {
            car.brake(brake);
        }
    }
}
