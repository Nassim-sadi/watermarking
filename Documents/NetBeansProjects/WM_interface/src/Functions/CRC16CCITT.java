/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Breeeeze
 */
public class CRC16CCITT {

    public static String calculateCRC(String source) throws UnsupportedEncodingException {
        String finalCrc = "";
        int crc = 0xFFFF;          // initial value
        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12) 

        byte[] bytes = source.getBytes("ASCII");

        //byte[] bytes = args[0].getBytes();
        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) {
                    crc ^= polynomial;
                }
            }
        }

        crc &= 0xffff;
        finalCrc = Integer.toHexString(crc);
        switch (finalCrc.length()) {
            case 1:
                finalCrc += "000";
                break;
            case 2:
                finalCrc += "00";
                break;
            case 3:
                finalCrc += "0";
                break;
            default:
                break;
        }
        return finalCrc;

    }

    public static String getImgCrc(BufferedImage img, int width, int height) throws UnsupportedEncodingException {
        String blockMsg = "";
        String TotalCrc = "";
        int p = 0;
        int pixel = 0;
        int endWidth = width *80/100;
        int endHeight = height *80/100;
        int startWidth = width*20/100;
        int startHeight = height*20/100 ;
        
        
        for (int i = startWidth; i < endWidth; i++) {
            for (int j = startHeight; j < endHeight; j++) {

                if (p < 256) {
                    pixel = (img.getRGB(i, j) >> 16) & 0xff;
                    blockMsg += pixel;
                    p++;
                } else if (p == 256) {
                   // System.out.println(blockMsg);
                    TotalCrc += calculateCRC(blockMsg);
                   // System.out.println(calculateCRC(blockMsg));
                    p = 0;
                    blockMsg = "";

                }
                if ((i == width - 1) && (j == height - 1)) {
                  // System.out.println(blockMsg);
                    //System.out.println(calculateCRC(blockMsg));
                    TotalCrc += calculateCRC(blockMsg);

                }

            }

        }
        return TotalCrc;

    }

}
