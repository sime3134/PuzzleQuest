package utilities;

import content.ContentManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author Simon Jern
 * Implements tools for handling images.
 */
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

    public static BufferedImage getFasterScaledInstance(BufferedImage img, int targetWidth, int targetHeight, boolean progressiveBilinear)
    {
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
                BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage) img;
        BufferedImage scratchImage = null;
        Graphics2D g2 = null;
        int w, h;
        int prevW = ret.getWidth();
        int prevH = ret.getHeight();
        if(progressiveBilinear) {
            w = img.getWidth();
            h = img.getHeight();
        }else{
            w = targetWidth;
            h = targetHeight;
        }
        do {
            if (progressiveBilinear && w > targetWidth) {
                w /= 2;
                if(w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (progressiveBilinear && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            if(scratchImage == null) {
                scratchImage = new BufferedImage(w, h, type);
                g2 = scratchImage.createGraphics();
            }
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);
            prevW = w;
            prevH = h;
            ret = scratchImage;
        } while (w != targetWidth || h != targetHeight);

        if (g2 != null) {
            g2.dispose();
        }

        if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
            scratchImage = new BufferedImage(targetWidth, targetHeight, type);
            g2 = scratchImage.createGraphics();
            g2.drawImage(ret, 0, 0, null);
            g2.dispose();
            ret = scratchImage;
        }
        return ret;
    }
}
