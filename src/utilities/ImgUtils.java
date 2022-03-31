package utilities;

import content.ContentManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class ImgUtils {

    public static final int ALPHA_OPAQUE = 1;
    public static final int ALPHA_BIT_MASKED = 2;
    public static final int ALPHA_BLEND = 3;

    private ImgUtils(){}

    public static Image loadImage(String filePath){
        try{
            Image imageFromDisk =  ImageIO.read(Objects.requireNonNull(ContentManager.class.getResource(filePath)));
            BufferedImage compatibleImage = (BufferedImage) createCompatibleImage(imageFromDisk.getWidth(null),
                    imageFromDisk.getHeight(null), ALPHA_BIT_MASKED);

            Graphics2D g = compatibleImage.createGraphics();
            g.drawImage(imageFromDisk, 0, 0, null);
            return compatibleImage;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Image createCompatibleImage(int width, int height, int transparency){
        GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        return graphicsConfiguration.createCompatibleImage(width, height, transparency);
    }
}
