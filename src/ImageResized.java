import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class Image Resized
 * @author Dmitriy Stepanov
 */
public class ImageResized {

    /**
     * Resize images
     * @param image original image
     * @param width the width image
     * @param height the height image
     * @return modified image of the BufferedImage type
     */
    public  static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        int type;
        type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}