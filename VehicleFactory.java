public class VehicleFactory {
    public static Vehicle createVehicle(String type) {
        switch (type.toLowerCase()) {
            case "volvo240":
                return new Volvo240();
            case "saab95":
                return new Saab95();
            case "scania":
                return new Scania();
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
}