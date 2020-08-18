package Functions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.image.BufferedImage;

/**
 *
 * @author Breeeeze
 */
public class Extraction {
    public static String binaryToText(String binaryString) {
    StringBuilder stringBuilder = new StringBuilder();
    int charCode;
    for (int i = 0; i < binaryString.length(); i += 8) {
        charCode = Integer.parseInt(binaryString.substring(i, i + 8), 2);
        String returnChar = Character.toString((char) charCode);
        stringBuilder.append(returnChar);
    }
    return stringBuilder.toString();
}

    String extractedMsg;

    public void extraction(BufferedImage waterMarkedImg, int height, int width, BufferedImage extractedImg, int[] estimator_position, int[] extractedMsgAr, int exMsgLength) {
        //main loop to work on image
        int estimator = 0;
        int block_num = 0;
        String extractedMsg ="";
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                int b1 = waterMarkedImg.getRGB(x, y);
                int p1 = (b1 >> 16) & 0xff;
                int b2 = waterMarkedImg.getRGB(x, y + 1);
                int p2 = (b2 >> 16) & 0xff;
                int b3 = waterMarkedImg.getRGB(x + 1, y);
                int p3 = (b3 >> 16) & 0xff;
                int b4 = waterMarkedImg.getRGB(x + 1, y + 1);
                int p4 = (b4 >> 16) & 0xff;
                //geting estimator from Array
                estimator = estimator_position[block_num];
                // System.out.println(" p1 : "+p1 + "| p2 : " + p2 + "| p3 : " + p3 + "| p4 : " + p4);

                //-----------comparing estimator with pixels ------------------
                if ((p1 < 255) && (p2 < 255) && (p3 < 255) && (p4 < 255)) {

//                    System.out.println(estimator);
                    switch (estimator) {
                        case 1: //------------------------------------------------------------------------------------- case 1
                            //compare with pixel a
                            estimator = p1;

                            p1 = (p1 << 16) | (p1 << 8) | p1;
                            extractedImg.setRGB(x, y, p1);

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
                            extractedImg.setRGB(x, y + 1, p2);
                            //compare with pixel b

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
                            extractedImg.setRGB(x + 1, y, p3);

                            //compare with pixel c
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

                            p2 = (p2 << 16) | (p2 << 8) | p2;
                            extractedImg.setRGB(x, y + 1, p2);

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
                            extractedImg.setRGB(x + 1, y, p3);

                            //compare with pixel c
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

                            p3 = (p3 << 16) | (p3 << 8) | p3;
                            extractedImg.setRGB(x + 1, y, p3);

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
                            extractedImg.setRGB(x, y + 1, p2);

                            //compare with pixel c
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
                            p4 = (p4 << 16) | (p4 << 8) | p4;
                            extractedImg.setRGB(x + 1, y + 1, p4);

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
                            extractedImg.setRGB(x, y + 1, p2);

                            //compare with pixel c
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
                            extractedImg.setRGB(x + 1, y, p3);
                            break;

                    }

                } else {
                    p1 = (p1 << 16) | (p1 << 8) | p1;
                    extractedImg.setRGB(x, y, p1);
                    p2 = (p2 << 16) | (p2 << 8) | p2;
                    extractedImg.setRGB(x + 1, y, p2);
                    p3 = (p3 << 16) | (p3 << 8) | p3;
                    extractedImg.setRGB(x, y + 1, p3);
                    p4 = (p4 << 16) | (p4 << 8) | p4;
                    extractedImg.setRGB(x + 1, y + 1, p4);
                }
                block_num++;

                y++;
            }
            x++;

        }
        System.out.println("the extracted binary is  "+extractedMsg.length());
        extractedMsg = extractedMsg.substring(0, exMsgLength);
        for (int i = 0; i < exMsgLength; i++) {
                    extractedMsgAr[i]=extractedMsg.charAt(i);
        }
    }
}
