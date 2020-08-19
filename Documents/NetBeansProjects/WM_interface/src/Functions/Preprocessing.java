/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;

import java.awt.image.BufferedImage;

/**
 *
 * @author Breeeeze
 */
public class Preprocessing {

    public void preprocessing(BufferedImage img, int width, int height) {

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                int p = img.getRGB(x, y);
                int r = (p >> 16) & 0xff;
                if ((r < 256) && (r > 248)) {
                    r = 255;

                } else if ((r >= 0) && (r <= 248)) {
                    r = r + 4;

                }
                p = (255 << 24)|(r << 16) | (r << 8) | r;
                img.setRGB(x, y, p);

            }
        }
    }
}
