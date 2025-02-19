import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Map;

// This panel represents the animated part of the view with the car images.
public class DrawPanel extends JPanel{

    private final Map< Vehicle, Point> vehiclePositions = new HashMap<>();
    private final Map<Class<? extends Vehicle>, BufferedImage> vehicleImages = new HashMap<>();

    BufferedImage volvoWorkshopImage;
    Point volvoWorkshopPoint = new Point(300,300);

    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);

        loadVehicleImages();
    }

    private void loadVehicleImages() {
        try {
            vehicleImages.put(Volvo240.class, ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Volvo240.jpg")));
            vehicleImages.put(Saab95.class, ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Saab95.jpg")));
            vehicleImages.put(Scania.class, ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Scania.jpg")));
            volvoWorkshopImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void moveit(Vehicle vehicle, int x, int y) {
        vehiclePositions.put(vehicle, new Point(x,y));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Map.Entry<Vehicle, Point> entry : vehiclePositions.entrySet()) {
            Vehicle vehicle = entry.getKey();
            Point position = entry.getValue();

            BufferedImage vehicleImage = vehicleImages.get(vehicle.getClass()); // Get correct image
            if (vehicleImage != null) {
                g.drawImage(vehicleImage, position.x, position.y, null);
            }
        }

        g.drawImage(volvoWorkshopImage, volvoWorkshopPoint.x, volvoWorkshopPoint.y, null);
    }
}
