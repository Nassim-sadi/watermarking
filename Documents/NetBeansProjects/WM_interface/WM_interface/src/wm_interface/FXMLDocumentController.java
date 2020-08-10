/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wm_interface;

import Functions.Insertion;
import Functions.Extraction;
import Functions.Preprocessing;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author Breeeeze
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    FileChooser fc = new FileChooser();

    @FXML
    private BorderPane imgViewPane;

    @FXML
    FileChooser fc_save = new FileChooser();

    @FXML
    FileChooser fc_img_save = new FileChooser();

    @FXML
    private Label label;
    @FXML
    private ImageView imgView;
    @FXML
    private TextArea secret_key;
    @FXML
    private Label key_label;

    private File selectedFile;
    private BufferedImage source_img;
    private BufferedImage watermarked_img;
    private BufferedImage extracted_img;
    private int[] estimator_position= new int[0];
//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }

    @FXML
    private void exit(ActionEvent event) {

        System.exit(0);
    }

    public void centerImage() {
        Image img = imgView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imgView.getFitWidth() / img.getWidth();
            double ratioY = imgView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imgView.setX((imgView.getFitWidth() - w) / 2);
            imgView.setY((imgView.getFitHeight() - h) / 2);

        }
    }

    @FXML
    public void File_btn_action(ActionEvent event) {
        fc.setTitle("Choose Image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png", "*.bmp"));
        selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                source_img = ImageIO.read(selectedFile);
                Image image = SwingFXUtils.toFXImage(source_img, null);
                imgView.fitWidthProperty().bind(imgViewPane.widthProperty());
                imgView.fitHeightProperty().bind(imgViewPane.heightProperty());
                imgView.setManaged(false);
                //centerImage();
                imgView.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("File not valid");

        }

    }
    @FXML
    public void saveImg_btn_action(ActionEvent event) {
        fc.setTitle("Choose Image save location");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png", "*.bmp"));
        selectedFile = fc_img_save.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                ImageIO.write(watermarked_img,"png",selectedFile);
                Image image = SwingFXUtils.toFXImage(watermarked_img, null);
                imgView.fitWidthProperty().bind(imgViewPane.widthProperty());
                imgView.fitHeightProperty().bind(imgViewPane.heightProperty());
                imgView.setManaged(false);
                //centerImage();
                imgView.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("File not valid");

        }

    }

    /*@FXML
   public void saveFile_btn_action(ActionEvent event){
        fc_save.setTitle("Choose save location");
        
        
    }
     */
   
    @FXML
    public void saveTxtAction(ActionEvent event) {
        //sample.setFont(new javafx.scene.text.Font(14));
        String key = secret_key.getText();
        if (!key.isEmpty() && selectedFile != null) {
            //Set extension filter for text files
            fc_save.setTitle("Choose text location");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fc_save.getExtensionFilters().add(extFilter);
            //Show save file dialog
            File file = fc_save.showSaveDialog(null);

            if (file != null) {
                saveTextToFile(key, file);
                key_label.setText("File Saved Successfully !...");
                key_label.setTextFill(Color.web("#0be30f"));
                secret_key.setText("");
            }
        } else {
            key_label.setText("Enter key/Image above First");
            key_label.setTextFill(Color.web("#eb7915"));
        }
    }

    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void insertWM(ActionEvent Event){
        Preprocessing pre =new Preprocessing();
        pre.preprocessing(source_img, source_img.getWidth(),source_img.getHeight());
        Insertion ins = new Insertion();
        String key = secret_key.getText();
         //converting clé to binary
        StringBuilder binary_key = ins.convert_to_binary(key);
        //create estimator pos array
         int e_length = (source_img.getWidth()*source_img.getHeight())/4;
       // System.out.println("number of blockes : "+e_length);
        int[] estimator_position= new int[e_length];
        
        watermarked_img =new BufferedImage(source_img.getWidth(),source_img.getHeight(),BufferedImage.TYPE_INT_RGB);
        ins.insertion(source_img, watermarked_img, source_img.getWidth(), source_img.getHeight(), binary_key, estimator_position);
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
