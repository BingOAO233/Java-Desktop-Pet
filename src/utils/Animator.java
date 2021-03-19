package utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author BingoIO_OI233
 */
public class Animator
{
    public static BufferedImage rotateImage(final BufferedImage bufferedimage, final double degree)
    {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        BufferedImage img;
        Graphics2D graphics2d;
        graphics2d = (img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)).createGraphics();
        img = graphics2d.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        graphics2d.dispose();
        graphics2d = img.createGraphics();
        graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2.0, h / 2.0);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
}
