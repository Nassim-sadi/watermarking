/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WaterMarking;

import static WaterMarking.insertion.convert_to_binary;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author Breeeeze
 */
public class Main {

    static BufferedImage read(File f) {
        BufferedImage image = null;
        File temp_img = new File("d:\\Java_tests\\temp_img.png");
        try {

            image = ImageIO.read(f);

            ImageIO.write(image,"png",temp_img);

            image = ImageIO.read(temp_img);

            /*if (temp_img.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file");
            }
            System.out.println("Reading image complete*/

        } catch (IOException e) {
            System.out.println("Error : " + e);
        }
        return image;
    }

    static void write_png(BufferedImage img, File file2) {

        try {
            ImageIO.write(img, "png", file2);
            System.out.println("Writing file png completed---------");
        } catch (IOException e) {
            System.out.println("Error :" + e);
        }

    }

    static void write_jpg(BufferedImage img, File file2) {

        try {
            ImageIO.write(img, "jpg", file2);
            System.out.println("Writing file as jpg completed---------");
        } catch (IOException e) {
            System.out.println("Error :" + e);
        }

    }

    public static void main(String[] args) {

        //declaration
        BufferedImage source_img = null;
        File source_file = null;
        BufferedImage watermarked_img = null;
        BufferedImage extracted_img = null;
            //input
            source_file = new File("d:\\Java_tests\\shelbi-grey.jpg");
            //output
            File output_file = new File("d:\\java_tests\\png_output.png");
            File extracted_file = new File("d:\\java_tests\\png_extracted.png");
            File f_jpg = new File("d:\\java_tests\\jpg_output.jpg");
        
        
        
        //read image 
        source_img = read(source_file);
        
        //get image height and width
        int width = source_img.getWidth();
        int height = source_img.getHeight();
        
        //preprocessing (adding 4 (0=>249), (250 => 255 = 255)
        preprocessing p = new preprocessing();
        p.preprocessing(source_img, width, height);
        
        //create new watermarked/extracted image
        watermarked_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        extracted_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
       
        //---------reading secret msg---------to be controlled later????!!!!!!!!!!!!!!!!!!------------------
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter Secret message , keep it short please ;D , until it gets controlled !");
        String msg = myObj.nextLine();  // Read user input
        System.out.println("secret message is : " + msg);  // Output user input
        
        //converting clé to binary
        StringBuilder binary_key = convert_to_binary(msg);

        //-------end of converting
        System.out.println(binary_key);
        System.out.println("binary length is : " + binary_key.length());
        
        //estimator position
        int e_length = (width*height)/4;
        System.out.println("number of blockes : "+e_length);
        int[] estimator_position= new int[e_length];
        
        //insertion 
        insertion insert = new insertion();
        insert.insertion(source_img,watermarked_img, width, height, binary_key, estimator_position);
        
        //write image 
        write_png(watermarked_img, output_file);
        //extracting msg 
        
        String extracted_msg="1";
        extraction extract =new extraction();
        extract.extraction(watermarked_img, height, width, extracted_img, estimator_position, extracted_msg);
        System.out.println("extracting finished .... attempting writing to disk");
         write_png(extracted_img, extracted_file);
         System.out.println(extracted_msg);
        //watermarked_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        watermarked_img = read(output_file);
//        write_jpg(watermarked_img, f_jpg);
//        
        
    }
}
