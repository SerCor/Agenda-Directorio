
package VistaControlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Agenda extends Application {
         
    @Override    
    public void start(Stage stage) throws Exception {
        /*Inicio del programa. Se incializa la ventana Inicio Login donde se ingresaran los datos de sesion o la creacion de una sesion*/
        
        FXMLLoader loader=new FXMLLoader(getClass().getResource("InicioLogin.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
  
    }

    public static void main(String[] args) {
        launch(args);
    }
}
