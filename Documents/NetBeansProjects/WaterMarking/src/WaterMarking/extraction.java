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
public class extraction {

    static void extraction(BufferedImage waterMarkedImg, int height, int width, BufferedImage extractedImg, int[] estimator_position, String extractedMsg) {
        //main loop to work on image
        int estimator = 0;
        int block_num = 0;
        // int estimator = 0;
       // width=width-1;
        //height=height-1;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                int b1 = waterMarkedImg.getRGB(x, y);
                int p1 = (b1>>16) & 0xff;
                int b2 = waterMarkedImg.getRGB(x + 1, y);
                int p2 = (b2>>16) & 0xff;
                int b3 = waterMarkedImg.getRGB(x, y + 1);
                int p3 = (b3>>16) & 0xff;
                int b4 = waterMarkedImg.getRGB(x + 1, y + 1);
                int p4 = (b4>>16) & 0xff;
                //geting estimator from Array

                estimator = estimator_position[block_num];

                //-----------comparing estimator with pixels ------------------
                //check is msg didn't end
                switch (estimator) {

                    case 1: //------------------------------------------------------------------------------------- case 1
                        //compare with pixel a
                        estimator = p1;

                        if (p2 > estimator) {
                            p2 = p2 - 2;
                            if ((Math.abs(estimator - p2) < 2)) {
                                extractedMsg = extractedMsg + '1';
                            }
                        } else if (p2 < estimator) {
                            p2 = p2 + 2;
                            if ((Math.abs(estimator - p2) < 2)) {
                                extractedMsg = extractedMsg + '0';
                            }
                        }
                        p2 = (p2 << 16) | (p2 << 8) | p2;
                        extractedImg.setRGB(x + 1, y, p2);
                        //compare with pixel b

                        estimator = p1;

                        if (p3 > estimator) {
                            p3 = p3 - 2;
                            if ((Math.abs(estimator - p3) < 2)) {
                                extractedMsg = extractedMsg + '1';
                            }
                        } else if (p3 < estimator) {
                            p3 = p3 + 2;
                            if ((Math.abs(estimator - p3) < 2)) {
                                extractedMsg = extractedMsg + '0';
                            }
                        }
                        p3 = (p3 << 16) | (p3 << 8) | p3;
                        extractedImg.setRGB(x, y + 1, p3);

                        //compare with pixel c
                        estimator = p1;

                        if (p4 > estimator) {
                            p4 = p4 - 2;
                            if ((Math.abs(estimator - p4) < 2)) {
                                extractedMsg = extractedMsg + '1';
                            }
                        } else if (p4 < estimator) {
                            p4 = p4 + 2;
                            if ((Math.abs(estimator - p4) < 2)) {
                                extractedMsg = extractedMsg + '0';
                            }
                        }
                        p4 = (p4 << 16) | (p4 << 8) | p4;
                        extractedImg.setRGB(x + 1, y + 1, p4);
                        break;
                    case 2: //------------------------------------------------------------------------------------- case 1
                        //compare with pixel a
                        estimator = p2;

                        if (p1 > estimator) {
                            p1 = p1 - 2;
                            if ((Math.abs(estimator - p1) < 2)) {
                                extractedMsg = extractedMsg + '1';
                            }
                        } else if (p1 < estimator) {
                            p1 = p1 + 2;
                            if ((Math.abs(estimator - p1) < 2)) {
                                extractedMsg = extractedMsg + '0';
                            }
                        }
                        p1 = (p1 << 16) | (p1 << 8) | p1;
                        extractedImg.setRGB(x, y, p1);
                        //compare with pixel b

                        estimator = p2;

                        if (p3 > estimator) {
                            p3 = p3 - 2;
                            if ((Math.abs(estimator - p3) < 2)) {
                                extractedMsg = extractedMsg + '1';
                            }
                        } else if (p3 < estimator) {
                            p3 = p3 + 2;
                            if ((Math.abs(estimator - p3) < 2)) {
                                extractedMsg = extractedMsg + '0';
                            }
                        }
                        p3 = (p3 << 16) | (p3 << 8) | p3;
                        extractedImg.setRGB(x, y + 1, p3);

                        //compare with pixel c
                        estimator = p2;

                        if (p4 > estimator) {
                            p4 = p4 - 2;
                            if ((Math.abs(estimator - p4) < 2)) {
                                extractedMsg = extractedMsg + '1';
                            }
                        } else if (p4 < estimator) {
                            p4 = p4 + 2;
                            if ((Math.abs(estimator - p4) < 2)) {
                                extractedMsg = extractedMsg + '0';
                            }
                        }
                        p4 = (p4 << 16) | (p4 << 8) | p4;
                        extractedImg.setRGB(x + 1, y + 1, p4);
                        break;
                    case 3: //------------------------------------------------------------------------------------- case 1
                        //compare with pixel a
                        estimator = p3;

                        if (p1 > estimator) {
                            p1 = p1 - 2;
                            if ((Math.abs(estimator - p1) < 2)) {
                                extractedMsg = extractedMsg + '1';
                            }
                        } else if (p1 < estimator) {
                            p1 = p1 + 2;
                            if ((Math.abs(estimator - p1) < 2)) {
                                extractedMsg = extractedMsg + '0';
                            }
                        }
                        p1 = (p1 << 16) | (p1 << 8) | p1;
                        extractedImg.setRGB(x, y, p1);

                        //compare with pixel b
                        estimator = p3;
                        if (p2 > estimator) {
                            p2 = p2 - 2;
                            if ((Math.abs(estimator - p2) < 2)) {
                                extractedMsg = extractedMsg + '1';
                            }
                        } else if (p2 < estimator) {
                            p2 = p2 + 2;
                            if ((Math.abs(estimator - p2) < 2)) {
                                extractedMsg = extractedMsg + '0';
                            }
                        }
                        p2 = (p2 << 16) | (p2 << 8) | p2;
                        extractedImg.setRGB(x + 1, y, p2);

                        //compare with pixel c
                        estimator = p3;

                        if (p4 > estimator) {
                            p4 = p4 - 2;
                            if ((Math.abs(estimator - p4) < 2)) {
                                extractedMsg = extractedMsg + '1';
                            }
                        } else if (p4 < estimator) {
                            p4 = p4 + 2;
                            if ((Math.abs(estimator - p4) < 2)) {
                                extractedMsg = extractedMsg + '0';
                            }
                        }
                        p4 = (p4 << 16) | (p4 << 8) | p4;
                        extractedImg.setRGB(x + 1, y + 1, p4);
                        break;
                    case 4: //------------------------------------------------------------------------------------- case 1
                        //compare with pixel a
                        estimator = p4;

                        if (p1 > estimator) {
                            p1 = p1 - 2;
                            if ((Math.abs(estimator - p1) < 2)) {
                                extractedMsg = extractedMsg + '1';
                            }
                        } else if (p1 < estimator) {
                            p1 = p1 + 2;
                            if ((Math.abs(estimator - p1) < 2)) {
                                extractedMsg = extractedMsg + '0';
                            }
                        }
                        p1 = (p1 << 16) | (p1 << 8) | p1;
                        extractedImg.setRGB(x, y, p1);

                        //compare with pixel b
                        estimator = p4;
                        if (p2 > estimator) {
                            p2 = p2 - 2;
                            if ((Math.abs(estimator - p2) < 2)) {
                                extractedMsg = extractedMsg + '1';
                            }
                        } else if (p2 < estimator) {
                            p2 = p2 + 2;
                            if ((Math.abs(estimator - p2) < 2)) {
                                extractedMsg = extractedMsg + '0';
                            }
                        }
                        p2 = (p2 << 16) | (p2 << 8) | p2;
                        extractedImg.setRGB(x + 1, y, p2);

                        //compare with pixel c
                        estimator = p4;

                        if (p3 > estimator) {
                            p3 = p3 - 2;
                            if ((Math.abs(estimator - p3) < 2)) {
                                extractedMsg = extractedMsg + '1';
                            }
                        } else if (p3 < estimator) {
                            p3 = p3 + 2;
                            if ((Math.abs(estimator - p3) < 2)) {
                                extractedMsg = extractedMsg + '0';
                            }
                        }
                        p3 = (p3 << 16) | (p3 << 8) | p3;
                        extractedImg.setRGB(x, y + 1, p3);

                }
               
                block_num++;
                y++;
            }
            x++;
            
        }
         System.out.println(extractedMsg);
    }
}
