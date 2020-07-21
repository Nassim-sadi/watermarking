/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WaterMarking;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Breeeeze
 */
public class insertion {

    static StringBuilder convert_to_binary(String msg) {
        // Secret msg
        //String msg = "this is a secret message inserted in image";
        // converting secret msg to binary 
        byte[] bytes = msg.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        return binary;
    }

    static void insertion(BufferedImage img, BufferedImage waterImg, int width, int height, StringBuilder binary, int[] Estimator_pos) {
//main loop to work on image
        int block_num = 0;
        int msg_counter = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                int b1 = img.getRGB(x, y);

                int p1 = (b1 >> 16) & 0xff;
                int b2 = img.getRGB(x + 1, y);
                int p2 = (b2 >> 16) & 0xff;
                int b3 = img.getRGB(x, y + 1);
                int p3 = (b3 >> 16) & 0xff;
                int b4 = img.getRGB(x + 1, y + 1);
                int p4 = (b4 >> 16) & 0xff;
                //geting a random 1-4 for estimator
                Random rn = new Random();
                int estimator = rn.nextInt(4) + 1;
                //System.out.println(estimator);
                Estimator_pos[block_num] = estimator;
                block_num = block_num + 1;
                //System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                //-----------comparing estimator with pixels ------------------
                switch (estimator) {

                    case 1: //------------------------------------------------------------------------------------- case 1
                        //System.out.println("case 1");
                        ////writing back estimator 
                        p1 = (p1 << 16) | (p1 << 8) | p1;
                        waterImg.setRGB(x, y + 1, p1);

                        //compare with pixel a
                        estimator = p1;
                        if (Math.abs(estimator - p2) < 2) {
                            if (msg_counter < binary.length()) {
                                if (Character.getNumericValue(binary.charAt(msg_counter)) == 1) {
                                    p2 = p2 + 2;
                                } else {
                                    p2 = p2 - 2;
                                }
                                msg_counter++;
                            }
                        } else if (estimator - p2 > 0) {

                            p2 = p2 - 2;
                        } else if (estimator - p2 < 0) {
                            p2 = p2 + 2;
                        }
                        //System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                        p2 = (p2 << 16) | (p2 << 8) | p2;

                        waterImg.setRGB(x + 1, y, p2);

                        //compare with pixel b
                        if (Math.abs(estimator - p3) < 2) {
                            if (msg_counter < binary.length()) {
                                if (Character.getNumericValue(binary.charAt(msg_counter)) == 1) {
                                    p3 = p3 + 2;
                                } else {
                                    p3 = p3 - 2;
                                }
                                msg_counter++;
                            }
                        } else if (estimator - p3 > 0) {

                            p3 = p3 - 2;
                        } else if (estimator - p3 < 0) {
                            p3 = p3 + 2;
                        }
                        //System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                        p3 = (p3 << 16) | (p3 << 8) | p3;
                        waterImg.setRGB(x, y + 1, p3);

                        //compare with pixel c
                        if (Math.abs(estimator - p4) < 2) {
                            if (msg_counter < binary.length()) {
                                if (Character.getNumericValue(binary.charAt(msg_counter)) == 1) {
                                    p4 = p4 + 2;
                                } else {
                                    p4 = p4 - 2;
                                }
                                msg_counter++;
                            }
                        } else if (estimator - p4 > 0) {

                            p4 = p4 - 2;
                        } else if (estimator - p4 < 0) {
                            p4 = p4 + 2;
                        }
                        //System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                        p4 = (p4 << 16) | (p4 << 8) | p4;
                        waterImg.setRGB(x + 1, y + 1, p4);

                        
                        break;
                    case 2:  //------------------------------------------------------------------------------------- case 2
                        //System.out.println("case 2");
                        ////writing back estimator 
                        p2 = (p2 << 16) | (p2 << 8) | p2;
                        waterImg.setRGB(x, y + 1, p2);
                        //compare with pixel a
                        estimator = p2;
                        if (Math.abs(estimator - p1) < 2) {
                            if (msg_counter < binary.length()) {
                                if (Character.getNumericValue(binary.charAt(msg_counter)) == 1) {
                                    p1 = p1 + 2;
                                } else {
                                    p1 = p1 - 2;
                                }
                                msg_counter++;
                            }
                        } else if (estimator - p1 > 0) {

                            p1 = p1 - 2;
                        } else if (estimator - p1 < 0) {
                            p1 = p1 + 2;
                        }
                        //System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                        p1 = (p1 << 16) | (p1 << 8) | p1;
                        waterImg.setRGB(x, y, p1);

                        //compare with pixel b
                        if (Math.abs(estimator - p3) < 2) {
                            if (msg_counter < binary.length()) {
                                if (Character.getNumericValue(binary.charAt(msg_counter)) == 1) {
                                    p3 = p3 + 2;
                                } else {
                                    p3 = p3 - 2;
                                }
                                msg_counter++;
                            }
                        } else if (estimator - p3 > 0) {

                            p3 = p3 - 2;
                        } else if (estimator - p3 < 0) {
                            p3 = p3 + 2;
                        }
                        //System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                        p3 = (p3 << 16) | (p3 << 8) | p3;
                        waterImg.setRGB(x, y + 1, p3);

                        //compare with pixel c
                        if (Math.abs(estimator - p4) < 2) {
                            if (msg_counter < binary.length()) {
                                if (Character.getNumericValue(binary.charAt(msg_counter)) == 1){
                                    p4 = p4 + 2;
                                } else {
                                    p4 = p4 - 2;
                                }
                                msg_counter++;
                            }
                        } else if (estimator - p4 > 0) {

                            p4 = p4 - 2;
                        } else if (estimator - p4 < 0) {
                            p4 = p4 + 2;
                        }
                        //System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                        p4 = (p4 << 16) | (p4 << 8) | p4;
                        waterImg.setRGB(x + 1, y + 1, p4);

                       
                        break;
                    case 3: //------------------------------------------------------------------------------------- case 3
                        //System.out.println("case 3");
                        ////writing back estimator
                        estimator = p3;
                        p3 = (p3 << 16) | (p3 << 8) | p3;
                        waterImg.setRGB(x, y + 1, p3);
                        //compare with pixel a

                        if (Math.abs(estimator - p1) < 2) {
                            if (msg_counter < binary.length()) {
                                if (Character.getNumericValue(binary.charAt(msg_counter)) == 1) {
                                    p1 = p1 + 2;
                                } else {
                                    p1 = p1 - 2;
                                }
                                msg_counter++;
                            }
                        } else if (estimator - p1 > 0) {

                            p1 = p1 - 2;
                        } else if (estimator - p1 < 0) {
                            p1 = p1 + 2;
                        }
                        //System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                        p1 = (p1 << 16) | (p1 << 8) | p1;
                        waterImg.setRGB(x, y, p1);

                        //compare with pixel b
                        if (Math.abs(estimator - p2) < 2) {
                            if (msg_counter < binary.length()) {
                                if (Character.getNumericValue(binary.charAt(msg_counter)) == 1) {
                                    p2 = p2 + 2;
                                } else {
                                    p2 = p2 - 2;
                                }
                                msg_counter++;
                            }
                        } else if (estimator - p2 > 0) {

                            p2 = p2 - 2;
                        } else if (estimator - p2 < 0) {
                            p2 = p2 + 2;
                        }
                        //System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                        p2 = (p2 << 16) | (p2 << 8) | p2;
                        waterImg.setRGB(x + 1, y, p2);

                        //compare with pixel c
                        if (Math.abs(estimator - p4) < 2) {
                            if (msg_counter < binary.length()) {
                                if (Character.getNumericValue(binary.charAt(msg_counter)) == 1) {
                                    p4 = p4 + 2;
                                } else {
                                    p4 = p4 - 2;
                                }
                                msg_counter++;
                            }
                        } else if (estimator - p4 > 0) {

                            p4 = p4 - 2;
                        } else if (estimator - p4 < 0) {
                            p4 = p4 + 2;
                        }
                        //System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                        p4 = (p4 << 16) | (p4 << 8) | p4;
                        waterImg.setRGB(x + 1, y + 1, p4);
                         

                        
                        break;
                    case 4: //------------------------------------------------------------------------------------- case 4
                        //System.out.println("case 4");
                        estimator = p4;
                        //compare with pixel a

                        if (Math.abs(estimator - p1) < 2) {
                            if (msg_counter < binary.length()) {
                                if (Character.getNumericValue(binary.charAt(msg_counter)) == 1) {
                                    p1 = p1 + 2;
                                } else {
                                    p1 = p1 - 2;
                                }
                                msg_counter++;
                            }
                        } else if (estimator - p1 > 0) {

                            p1 = p1 - 2;
                        } else if (estimator - p1 < 0) {
                            p1 = p1 + 2;
                        }
                        //System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                        p1 = (p1 << 16) | (p1 << 8) | p1;
                        waterImg.setRGB(x, y, p1);

                        //compare with pixel b
                        if (Math.abs(estimator - p2) < 2) {
                            if (msg_counter < binary.length()) {
                                if (Character.getNumericValue(binary.charAt(msg_counter)) == 1) {
                                    p2 = p2 + 2;
                                } else {
                                    p2 = p2 - 2;
                                }
                                msg_counter++;
                            }
                        } else if (estimator - p2 > 0) {

                            p2 = p2 - 2;
                        } else if (estimator - p2 < 0) {
                            p2 = p2 + 2;
                        }
                        //System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                        p2 = (p2 << 16) | (p2 << 8) | p2;
                        waterImg.setRGB(x + 1, y, p2);

                        //compare with pixel c
                        if (Math.abs(estimator - p3) < 2) {
                            if (msg_counter < binary.length()) {
                                if (Character.getNumericValue(binary.charAt(msg_counter)) == 1) {
                                    p3 = p3 + 2;
                                } else {
                                    p3 = p3 - 2;
                                }
                                msg_counter++;

                            }
                        } else if (estimator - p3 > 0) {

                            p3 = p3 - 2;
                        } else if (estimator - p3 < 0) {
                            p3 = p3 + 2;
                        }
                        //System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
                        p3 = (p3 << 16) | (p3 << 8) | p3;
                        waterImg.setRGB(x, y + 1, p3);
                        ////writing back estimator 

                        p4 = (p4 << 16) | (p4 << 8) | p4;
                        waterImg.setRGB(x, y + 1, p4);
                        break;
                }

                y++;
            }
            x++;
        }

    }
}
