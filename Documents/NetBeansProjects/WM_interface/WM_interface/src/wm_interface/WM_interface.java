/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package wm_interface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Breeeeze
 */
public class WM_interface extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        //testing some stuff ----resize issues
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        stage.setFullScreen(false);
        stage.setMaximized(true);
        stage.setMaxHeight(visualBounds.getHeight());
        stage.setMaxWidth(visualBounds.getWidth());
        stage.setMinHeight(500);
        stage.setMinWidth(1000);
        //end of testing-----------------
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/Css/Styles.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
