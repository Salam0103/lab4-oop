import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Map;

public class DrawPanel extends JPanel {
    private final Map<Vehicle, Point> vehiclePositions = new HashMap<>();
    private final Map<Class<? extends Vehicle>, BufferedImage> vehicleImages = new HashMap<>();

    BufferedImage volvoWorkshopImage;
    Point volvoWorkshopPoint = new Point(300, 300);

    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.gray);

        loadVehicleImages();
        volvoWorkshopPoint.x = x - volvoWorkshopImage.getWidth();  // Set x to right edge minus image width
        volvoWorkshopPoint.y = 40;
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
        vehiclePositions.put(vehicle, new Point(x, y));
        repaint();  // Make sure the screen updates when positions change
    }

    public void removeVehicle(Vehicle vehicle) {
        vehiclePositions.remove(vehicle); // Remove the car from the positions map
        repaint(); // Refresh the panel
    }

    public boolean checkWorkshopCollision(Vehicle vehicle) {
        Point vehiclePos = vehiclePositions.get(vehicle);
        if (vehiclePos != null) {
            Rectangle vehicleBounds = new Rectangle(vehiclePos.x, vehiclePos.y, 100, 60); // Assuming car size is 100x60
            Rectangle workshopBounds = new Rectangle(volvoWorkshopPoint.x, volvoWorkshopPoint.y, volvoWorkshopImage.getWidth(), volvoWorkshopImage.getHeight());
            return vehicleBounds.intersects(workshopBounds);
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Map.Entry<Vehicle, Point> entry : vehiclePositions.entrySet()) {
            Vehicle vehicle = entry.getKey();
            Point position = entry.getValue();

            BufferedImage vehicleImage = vehicleImages.get(vehicle.getClass());
            if (vehicleImage != null) {
                if (vehicle.getDirection() == 3) {
                    // Flip the image horizontally
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.drawImage(vehicleImage,
                            position.x + vehicleImage.getWidth() / 2,
                            position.y,
                            -vehicleImage.getWidth(),
                            vehicleImage.getHeight(),
                            this);
                } else {
                    // Normal drawing if not facing West
                    g.drawImage(vehicleImage, position.x, position.y, null);
                }
            }
        }

        g.drawImage(volvoWorkshopImage, volvoWorkshopPoint.x, volvoWorkshopPoint.y, null);  // Ensure workshop is also visible
    }
}