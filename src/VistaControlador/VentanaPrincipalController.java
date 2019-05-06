
package VistaControlador;

import DTO.*;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaPrincipalController extends windowBorderlessManager  implements Initializable {
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
  public void cerrarSesion(Event e){
      createLogin(e);
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
        
        /*Carga el contenedor del directorio y lo inicializa con la informacion del usuario*/
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
            contenedorDirectorios.setDividerPositions(.198);
            contenedorPrincipal.getSelectionModel().select(contenedorPrincipal.getTabs().get(1)); //Seleccionar el tab de Agenda

            
        }catch(Exception e){
            e.printStackTrace();
            mostrarVentanaError("Error.No fue posible cargar los datos del directorio.");
        }      
    }
    
    public void inicializaEscenaAgenda(){
         //Carga contenedor de la agenda e inicializa con la informacion del usuario/
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
            tabAgenda.setContent(agenda);
         }catch(Exception e){
             e.printStackTrace();
             mostrarVentanaError("Error.No fue posible cargar los datos de la agenda.");
         }
    }
    
    @FXML
    public void renovarAgenda(Event value){
        //Actualiza contenido en la agenda en caso de que sea necesario.
        
        if(controllerAgenda != null){
            controllerAgenda.actualizaTableroMes();
           controllerAgenda.actualizarTableroCitasDia();
        }
        
    }

    
    public void mostrarVentanaError(String error){
        
        //Genera un ventana de error la cual puede contener un mensaje personalizado.
        
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
