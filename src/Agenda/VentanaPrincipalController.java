/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import DAO.AgendaDAO;
import DAO.AgendaSql;
import DAO.CitaDAO;
import DAO.CitaSql;
import DAO.ContactoDAO;
import DAO.ContactoEmpresarialSql;
import DAO.ContactoPersonalSql;
import DAO.DirectorioDAO;
import DAO.DirectorioEmpresarialSql;
import DAO.DirectorioPersonalSql;
import DTO.*;
 import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaPrincipalController  implements Initializable {
    @FXML TabPane contenedorPrincipal;
    @FXML Tab tabAgenda;
    @FXML Tab tabDirectorio;
    @FXML Label etiquetaNombre;
    @FXML Label etiquetaNoTrabajador;
    @FXML Label etiquetaPuesto;
    @FXML Label etiquetaCerrarSesion;
    @FXML SplitPane contenedorDirectorios;
    private TabPane directorio;
    private SplitPane agenda;
    private ContenedorAgendaController controllerAgenda;
    private TrabajadorDTO usuario;

    public void setTrabajadorDTO(TrabajadorDTO usuario){
        this.usuario = usuario;
        
        inicializarBarraInformacion();
    }
    
    public TrabajadorDTO getUsuario(){
        return usuario;
    }
    
  @FXML
  public void cerrarSesion(MouseEvent e){
      System.exit(0);
  }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

    }
    
    public void inicializarBarraInformacion(){
        etiquetaNombre.setText(usuario.getNombre());
        etiquetaNoTrabajador.setText(usuario.getIdTrabajador());
        etiquetaPuesto.setText(usuario.getPuesto());
    }
    
    public void incializaEscenaDirectorio(){
        try{         
            //Cargar Escena Directorio
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("ContenedorDirectorio.fxml"));
            directorio =  (TabPane)loader1.load();
            ContenedorDirectorioController controllerDirectorio =(ContenedorDirectorioController) loader1.getController();
            FXMLLoader loader =(FXMLLoader) (contenedorPrincipal.getScene().getUserData());
            VentanaPrincipalController controllerPrincipal = loader.getController();
            controllerDirectorio.setControllerPrincipal(controllerPrincipal);
            controllerDirectorio.setUsuario(usuario);
            controllerDirectorio.setMyController(controllerDirectorio);
            controllerDirectorio.actualizarDirectorioEmpresarial();
            controllerDirectorio.actualizarDirectorioPersonal();
            Scene scene =  new Scene(directorio);
            scene.setUserData(loader1);
            contenedorDirectorios.getItems().add(scene.getRoot());  
            contenedorDirectorios.setDividerPositions(.18);
            contenedorPrincipal.getSelectionModel().select(contenedorPrincipal.getTabs().get(1)); //Seleccionar el tab de Agenda
            
        }catch(Exception e){
            e.printStackTrace();
            mostrarVentanaError("Error.No fue posible cargar los datos del directorio.");
        }      
    }
    
    public void inicializaEscenaAgenda(){
         //Cargar Escena Agenda
         try{
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("ContenedorAgenda.fxml"));
            agenda = (SplitPane)loader1.load();
            ContenedorAgendaController controllerAgenda = (ContenedorAgendaController)loader1.getController();        
            controllerAgenda.setMyController(controllerAgenda);
            FXMLLoader loader =(FXMLLoader) (contenedorPrincipal.getScene().getUserData());
            VentanaPrincipalController controllerPrincipal = loader.getController();
            this.controllerAgenda = controllerAgenda;
            controllerAgenda.setControllerPrincipal(controllerPrincipal);
            controllerAgenda.setUsuario(usuario);
            controllerAgenda.actualizaTableroMes();
            controllerAgenda.actualizarTableroCitasDia();
            
            System.out.println(agenda);
            tabAgenda.setContent(agenda);
         }catch(Exception e){
             e.printStackTrace();
             mostrarVentanaError("Error.No fue posible cargar los datos de la agenda.");
         }
    }
    
    @FXML
    public void renovarAgenda(Event value){
        if(controllerAgenda != null){
            controllerAgenda.actualizaTableroMes();
           controllerAgenda.actualizarTableroCitasDia();
        }
        
    }

    
    public void mostrarVentanaError(String error){
         try{
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResource("agenda.png").toString()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaError.fxml"));
            AnchorPane contenedor = loader.load();
            VentanaErrorController controller = loader.getController();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(contenedorPrincipal.getScene().getWindow());
            controller.setMensaje(error);
            stage.setScene(new Scene(contenedor));
            stage.getIcons().add(new Image(getClass().getResource("agenda.png").toString()));
            stage.setResizable(false);
            stage.setTitle("Mensaje de error.");
            stage.show();
        }catch(Exception exx){
            exx.printStackTrace();
        }
    }
    
}
