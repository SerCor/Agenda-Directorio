package Agenda;

import DTO.TrabajadorDTO;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class windowBorderlessManager{
    protected TrabajadorDTO user;
    private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML private MaterialDesignIconView closeWinBtn;
    @FXML private MaterialDesignIconView minWinBtn;
    
    @FXML private void manageWindow(MouseEvent e){
        Stage stage;
        if(e.getSource()==minWinBtn){
            stage= ((Stage)((MaterialDesignIconView)e.getSource()).getScene().getWindow());
            stage.setIconified(true);
        }
        if(e.getSource()==closeWinBtn){
            System.exit(0);            
        }
    }
    
    public void createSignUp(MouseEvent e){
        try {
            FXMLLoader loader = new FXMLLoader();
            Stage stage=new Stage();
            Pane root = loader.load(getClass().getResource("SignUp.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            
            ((Node)e.getSource()).getScene().getWindow().hide(); //equivalent to close the stage
        } catch (IOException ex) {
            System.out.print(ex);
        }
    }
    
    public void createLogin(Event e){
        try {
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            Pane root = loader.load(getClass().getResource("InicioLogin.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            
            ((Node)e.getSource()).getScene().getWindow().hide(); 
        } catch (IOException ex) {
            System.out.print(ex);
        }
    }
    public void createAlerta(ActionEvent e){
        try {
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            Pane root = loader.load(getClass().getResource("Alerta.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            ((Node)e.getSource()).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.print(ex);
        }
        
    }
    public void createBienvenida(Event e){
        try {
            Stage stage=new Stage();
           FXMLLoader loader = new FXMLLoader(getClass().getResource(
               "VentanaPrincipal.fxml"));
            TabPane root = (TabPane) loader.load();
            VentanaPrincipalController controller =loader.getController();
            controller.setTrabajadorDTO(user);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            //stage.initStyle(StageStyle.TRANSPARENT);
            scene.setUserData(loader);
            stage.setScene(scene);
            Toolkit t = Toolkit.getDefaultToolkit();
            Dimension d = t.getScreenSize();
            stage.setResizable(false);
            stage.setWidth(d.width);
            stage.setHeight(d.height-40);
            stage.setTitle("Agenda Personal");
            stage.getIcons().add(new Image(getClass().getResource("agenda.png").toString()));
            stage.show();
            controller.inicializaEscenaAgenda();
            controller.incializaEscenaDirectorio();
            ((Node)e.getSource()).getScene().getWindow().hide(); 
        } catch (IOException ex) {
            System.out.print(ex);
        }
    }
    
    //Funciones para hacer desplazable la ventana
    //getWindow obtiene la pocision de la ventana 
    //y moveWindow deplaza la ventana de acuerdo a las coordenadas del mouse
    @FXML private void getWindow(MouseEvent e){
        xOffset = e.getSceneX();
        yOffset = e.getSceneY();
    }
    @FXML private void moveWindow(MouseEvent e){
        Stage stage=((Stage)((Pane)e.getSource()).getScene().getWindow());
        stage.setX(e.getScreenX() - xOffset);
        stage.setY(e.getScreenY() - yOffset);
    }
}
