/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wm_interface;

import Functions.Insertion;
import Functions.Extraction;
import static Functions.Extraction.binaryToText;
import Functions.Preprocessing;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;
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
    FileChooser fc_openTxt = new FileChooser();

    @FXML
    FileChooser fc_img_save = new FileChooser();
    @FXML
    Label DeWM_label;

    @FXML
    private Label label;
    @FXML
    private ImageView imgView;
    @FXML
    private TextArea secret_key;
    @FXML
    private Label key_label;
    @FXML
    private Label exMsgLabel;
    @FXML
    private Label greyLabel;

    private File selectedFile;
    private File selectedwtFile;
    private File selectedSaveFile;
    private BufferedImage source_img;
    private BufferedImage watermarked_img;
    private BufferedImage watermarked_img2;
    private BufferedImage extracted_img;
    int[] estimator_position = new int[0];
    private String estimator;
    private File estimator_pos_file;
    private int extMsgLength;
    private String extracted_msg;
//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }

    @FXML
    private void exit(ActionEvent event) {

        System.exit(0);
    }

    @FXML
    public void greyConvert(ActionEvent event) {
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
                SequentialTransition transitionForward = createTransition(imgView, image);
                transitionForward.play();
                
                
        int p;
        
        for (int x = 0 ; x < source_img.getHeight() ; x++ ){
            for (int y = 0 ; y < source_img.getWidth(); y++){
                p = source_img.getRGB(y , x);
                int r = (p>>16) & 0xff;
                int g = (p>>8)& 0xff;
                int b = (p) & 0xff;
                int avg = (r+g+b)/3;
                
                p = (avg<<16)|(avg<<8)|avg;
                source_img.setRGB(y, x, p);
                
            }
        }
//                  
                //        imgView.setImage(image);
                greyLabel.setText("Converting completed");
                  //Set extension filter for text files
            fc_img_save.setInitialFileName("Grey version image");
            fc_img_save.setTitle("Choose Image save location");
            fc_img_save.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image files", "*.png"));
            selectedSaveFile = fc_img_save.showSaveDialog(null);
            ImageIO.write(source_img, "png", selectedSaveFile);
            image = SwingFXUtils.toFXImage(source_img, null);
             SequentialTransition transition = createTransition(imgView, image);
                transition.play();
                
            //imgView.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            greyLabel.setText("File not Valid");
            //System.out.println("File not valid");

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
                SequentialTransition transitionForward = createTransition(imgView, image);
                transitionForward.play();
//                  
                //        imgView.setImage(image);
                key_label.setText("inserting watermark Will take sometime , please wait !");
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            key_label.setText("File not Valid");
            //System.out.println("File not valid");

        }

    }
///----------------------inserting Watermarking button

    @FXML
    public void watermark_btn_action(ActionEvent event) {
        String key = secret_key.getText();
        if (!key.isEmpty() && selectedFile != null) {

            //Set extension filter for text files
            fc_img_save.setInitialFileName("Watermarked image");
            fc_img_save.setTitle("Choose Image save location");
            fc_img_save.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image files", "*.png"));
            selectedSaveFile = fc_img_save.showSaveDialog(null);
            if (selectedSaveFile != null) {
                try {

                    Preprocessing pre = new Preprocessing();
                    pre.preprocessing(source_img, source_img.getWidth(), source_img.getHeight());
                    Insertion ins = new Insertion();
                    //converting clé to binary
                    StringBuilder binary_key = ins.convert_to_binary(key);

                    //create estimator pos array
                    int e_length = (source_img.getWidth() * source_img.getHeight()) / 4;
                    // System.out.println("number of blockes : "+e_length);
                    int[] estimator_position = new int[e_length + 1];
                    estimator_position[estimator_position.length - 1] = binary_key.length();
                    //  System.out.println(binary_key.length());
                    watermarked_img = new BufferedImage(source_img.getWidth(), source_img.getHeight(), BufferedImage.TYPE_INT_RGB);
                    ins.insertion(source_img, watermarked_img, source_img.getWidth(), source_img.getHeight(), binary_key, estimator_position);
                    estimator = "";
                    for (int i = 0; i < estimator_position.length; i++) {
                        estimator = estimator + estimator_position[i];
                    }
                    //System.out.println(estimator);
                    Image image = SwingFXUtils.toFXImage(watermarked_img, null);
                    ImageIO.write(watermarked_img, "png", selectedSaveFile);

                    imgView.fitWidthProperty().bind(imgViewPane.widthProperty());
                    imgView.fitHeightProperty().bind(imgViewPane.heightProperty());
                    imgView.setManaged(false);
                    SequentialTransition transitionForward = createTransition(imgView, image);
                    transitionForward.play();
//                   imgView.setImage(image);
                    key_label.setText("inserting watermark completed ..");
                    key_label.setTextFill(Color.web("#0be30f"));

                    //Set extension filter for text files
                    fc_save.setTitle("Choose text location");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                    fc_save.getExtensionFilters().add(extFilter);
                    //Show save file dialog
                    fc_save.setInitialFileName("Est_pos");
                    File file = fc_save.showSaveDialog(null);

                    if (file != null) {
                        saveTextToFile(estimator, file);
                        key_label.setText("File Saved Successfully !...");
                        key_label.setTextFill(Color.web("#0be30f"));
                        secret_key.setText("");
                    }

                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                key_label.setText("File not Valid ");
                //    System.out.println("File not valid");

            }

        } else {
            key_label.setText("Enter key/Image above First");
            key_label.setTextFill(Color.web("#eb7915"));
        }

    }

    ///-----------------------------extract watermark button ----------------------------------------------------
    @FXML
    public void openWT_btn_action(ActionEvent Event) {

        fc.setTitle("Choose Watermarked Image");
        fc.setInitialFileName("watermarked image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.png"));
        selectedwtFile = fc.showOpenDialog(null);
        if (selectedwtFile != null) {
            try {
                watermarked_img2 = ImageIO.read(selectedwtFile);
                Image image = SwingFXUtils.toFXImage(watermarked_img2, null);
                imgView.fitWidthProperty().bind(imgViewPane.widthProperty());
                imgView.fitHeightProperty().bind(imgViewPane.heightProperty());
                imgView.setManaged(false);
                SequentialTransition transitionForward = createTransition(imgView, image);
                transitionForward.play();

                // imgView.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            DeWM_label.setText("File not Valid");
            DeWM_label.setTextFill(Color.web("#eb7915"));

        }

    }

    @FXML
    public void openEst_btn_action(ActionEvent Event) {
        fc_openTxt.setTitle("Choose Est_pos Text file");
        fc_openTxt.setInitialFileName("Est_pos");
        fc_openTxt.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files", "*.txt"));
        if (selectedwtFile != null) {

            int est_length;
            est_length = (watermarked_img2.getWidth() * watermarked_img2.getHeight()) / 4;

            estimator_pos_file = fc_openTxt.showOpenDialog(null);
            estimator = "";
            if (estimator_pos_file != null) {

                try {
                    Scanner scanner = new Scanner(estimator_pos_file);
                    String extMsg = "";
                    while (scanner.hasNext()) {
                        estimator = estimator + scanner.next();
                    }

                    extMsg = estimator.substring(est_length);

                    //System.out.println(estimator);
                    extMsgLength = Integer.parseInt(extMsg);
                    //  System.out.println(extMsgLength);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                DeWM_label.setText("Files Ready . press Dewatermark !");
                DeWM_label.setTextFill(Color.web("#0be30f"));

            } else {
                DeWM_label.setText("File not Valid");
                DeWM_label.setTextFill(Color.web("#eb7915"));

            }

        } else {

            DeWM_label.setText("Open Watermarked image first");
            DeWM_label.setTextFill(Color.web("#eb7915"));

        }
    }

    @FXML
    public void deWatermark_btn_action(ActionEvent Event) throws IOException {
        Extraction ext = new Extraction();
        if (estimator_pos_file != null) {
            //create estimator pos array
            int e_length = (watermarked_img2.getWidth() * watermarked_img2.getHeight()) / 4;
            int[] estimator_position2 = new int[e_length];
            //          System.out.println(extMsgLength);
//
            for (int i = 0; i < e_length; i++) {
                estimator_position2[i] = Character.getNumericValue(estimator.charAt(i));
            }
            extracted_msg = "";
            int[] extactedMsgArray = new int[extMsgLength];
            extracted_img = new BufferedImage(watermarked_img2.getWidth(), watermarked_img2.getHeight(), BufferedImage.TYPE_INT_RGB);
            ext.extraction(watermarked_img2, watermarked_img2.getHeight(), watermarked_img2.getWidth(), extracted_img, estimator_position2, extactedMsgArray, extMsgLength);
            for (int i = 0; i < extMsgLength; i++) {
                extracted_msg = extracted_msg + (char) extactedMsgArray[i];
            }

            extracted_msg = binaryToText(extracted_msg);

            DeWM_label.setText("Extracting Watermark completed .!");
            DeWM_label.setTextFill(Color.web("#0be30f"));
            exMsgLabel.setText(extracted_msg);
            Image image = SwingFXUtils.toFXImage(extracted_img, null);
            imgView.fitWidthProperty().bind(imgViewPane.widthProperty());
            imgView.fitHeightProperty().bind(imgViewPane.heightProperty());
            imgView.setManaged(false);
            SequentialTransition transitionForward = createTransition(imgView, image);
            transitionForward.play();
            //Set extension filter for text files
            fc_img_save.setInitialFileName("Extracted image");
            fc_img_save.setTitle("Choose Image save location");
            fc_img_save.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image files", "*.png"));
            selectedSaveFile = fc_img_save.showSaveDialog(null);
            ImageIO.write(extracted_img, "png", selectedSaveFile);
            //imgView.setImage(image);

        } else {
            DeWM_label.setText("Open Estimator txt file ....");
            DeWM_label.setTextFill(Color.web("#eb7915"));
        }

    }

    @FXML
    /*   public void saveTxtAction(ActionEvent event) {
        String key = "";

        if (!key.isEmpty() && selectedSaveFile != null) {
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
            key_label.setText("You need to watermark an image First !");
            key_label.setTextFill(Color.web("#eb7915"));
        }
    }
     */
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    SequentialTransition createTransition(final ImageView iv, final Image img) {
        FadeTransition fadeOutTransition
                = new FadeTransition(Duration.seconds(1), iv);
        fadeOutTransition.setFromValue(1.0);
        fadeOutTransition.setToValue(0.0);
        fadeOutTransition.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                iv.setImage(img);
            }

        });

        FadeTransition fadeInTransition
                = new FadeTransition(Duration.seconds(1), iv);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        SequentialTransition sequentialTransition
                = SequentialTransitionBuilder
                        .create()
                        .children(fadeOutTransition, fadeInTransition)
                        .build();

        return sequentialTransition;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
