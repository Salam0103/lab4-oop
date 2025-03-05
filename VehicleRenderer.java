import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class VehicleRenderer {
    private final Map<Class<? extends Vehicle>, BufferedImage> vehicleImages = new HashMap<>();

    public VehicleRenderer() {
        loadVehicleImages();
    }

    /**
     * Loads vehicle images from the resources folder.
     */
    private void loadVehicleImages() {
        try {
            vehicleImages.put(Volvo240.class, ImageIO.read(getClass().getResourceAsStream("pics/Volvo240.jpg")));
            vehicleImages.put(Saab95.class, ImageIO.read(getClass().getResourceAsStream("pics/Saab95.jpg")));
            vehicleImages.put(Scania.class, ImageIO.read(getClass().getResourceAsStream("pics/Scania.jpg")));
        } catch (IOException ex) {
            System.out.println("Error loading images: " + ex.getMessage());
        }
    }

    /**
     * Draws a vehicle on the screen.
     *
     * @param vehicle  The vehicle to draw.
     * @param position The position of the vehicle.
     * @param g        The Graphics object used for drawing.
     */
    public void drawVehicle(Vehicle vehicle, Point position, Graphics g) {
        BufferedImage vehicleImage = vehicleImages.get(vehicle.getClass());

        if (vehicleImage != null) {
            if (vehicle.getDirection() == 3) {
                // Flip the image horizontally for vehicles facing west
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(
                        vehicleImage,
                        position.x + vehicleImage.getWidth() / 2,
                        position.y,
                        -vehicleImage.getWidth(),
                        vehicleImage.getHeight(),
                        null
                );
            } else {
                // Draw the image normally for other directions
                g.drawImage(vehicleImage, position.x, position.y, null);
            }
        }
    }
}