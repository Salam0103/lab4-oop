public interface VehicleActions extends Movable{
    void startEngine();
    void stopEngine();
    void gas(double amount);
    void brake(double amount);
}
