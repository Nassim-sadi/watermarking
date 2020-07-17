/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WaterMarking;

import java.awt.image.BufferedImage;
/**
 *
 * @author Breeeeze
 */

public class preprocessing{

    static void preprocessing(BufferedImage img, int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int p = img.getRGB(x, y);
                int r = (p >> 16) & 0xff;

                if ((r <= 255) && (r > 249)) {
                    r = 255;
                    p = (r << 16) | (r << 8) | r;
                    img.setRGB(x, y, p);

                } else if ((r <= 0) && (r >= 249)) {
                    r = r + 4;
                    p = (r << 16) | (r << 8) | r;
                    img.setRGB(x, y, p);

                }
            }
        }
    }
}
