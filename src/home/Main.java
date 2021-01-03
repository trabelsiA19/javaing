
package home;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


import javafx.stage.Stage;


public class Main extends Application {

 

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Formateur.fxml"));
      Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.setTitle("DEVTECH ");
       stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/dribbble.png")));
        stage.show();

    }
    
    

    
    public static void main(String[] args) {
        launch(args);
    }

}