/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import DAO.CitaDAO;
import DAO.CitaSql;
import DAO.ContactoEmpresarialSql;
import DAO.ContactoPersonalSql;
import DAO.DirectorioEmpresarialSql;
import DAO.DirectorioPersonalSql;
import DTO.ContactoDTO;
import DTO.ContactoEmpresarialDTO;
import DTO.ContactoPersonalDTO;
import DTO.Directorio;
import DTO.DirectorioPersonalDTO;
import DTO.TrabajadorDTO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SerCo
 */
public class ContenedorDirectorioController implements Initializable {
    @FXML ScrollPane scrollDirectorioEmpresarial;
    @FXML ScrollPane scrollDirectorioPersonal;
    @FXML VBox contenedorDirectorioPersonal;
    @FXML VBox contenedorDirectorioEmpresarial;
    @FXML TextField campoBusquedaEmpresa;
    @FXML TextField campoBusquedaPersona;
    private TrabajadorDTO usuario;
    private VentanaPrincipalController controllerPrincipal;
    private ContenedorDirectorioController myController;
    
    public void setMyController(ContenedorDirectorioController controller){
        myController = controller;
    }
    
    @FXML
    public void buscarContactoEmpresarial(KeyEvent e){
        /*Detecta Enter sobre el Input Text para proseguir a buscar el contacto dentro de los contenedores */
        if(e.getCode().equals(KeyCode.ENTER)){
            try{
                System.out.println("Buscando contacto empresarial");
                e.consume();
                ObservableList<Node> contenedores = contenedorDirectorioEmpresarial.getChildren();
                boolean flagEncontrado = false;
                GridPane contenedorContacto = null;
                FXMLLoader loader = null;
                Iterator itr = contenedores.iterator();
                while(itr.hasNext() && !flagEncontrado){
                    contenedorContacto =  (GridPane) itr.next();
                    loader =(FXMLLoader) contenedorContacto.getUserData();
                    ContenedorContactoController controller =(ContenedorContactoController) loader.getController();
                    if(controller.campoEmpresa.getText().equals(campoBusquedaEmpresa.getText().toUpperCase())){
                        ensureVisible(scrollDirectorioEmpresarial,contenedorContacto);
                        System.out.println("Encontrado");
                        flagEncontrado = true;
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
                controllerPrincipal.mostrarVentanaError("Error. No fue posible hacer la busqueda del contacto.");
            }
        }
        
    }
    
    @FXML
    public void buscarContactoPersonal(KeyEvent e){
        
        if(e.getCode().equals(KeyCode.ENTER)){

            try{
                System.out.println("Buscando contacto personal");
                e.consume();
                ObservableList<Node> contenedores = contenedorDirectorioPersonal.getChildren();
                GridPane contenedorContacto = null;
                FXMLLoader loader = null;
                Iterator itr = contenedores.iterator();
                boolean flagEncontrado =false;
                while(itr.hasNext() && !flagEncontrado){
                    contenedorContacto =  (GridPane) itr.next();
                    loader =(FXMLLoader) contenedorContacto.getUserData();
                    ContenedorContactoPersonalController controller =(ContenedorContactoPersonalController) loader.getController();
                    if(controller.campoNombre.getText().equals(campoBusquedaPersona.getText().toUpperCase())){                       
                        System.out.println("Encontrado");
                        ensureVisible(scrollDirectorioPersonal,contenedorContacto);
                        flagEncontrado = true;
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
                controllerPrincipal.mostrarVentanaError("Error. No fue posible hacer la busqueda del contacto.");
            }
   
        }
        
    }
    
     private static void ensureVisible(ScrollPane pane, Node node) {
        double width = pane.getContent().getBoundsInLocal().getWidth();
        double height = pane.getContent().getBoundsInLocal().getHeight();
        
        //TODO: Falta determinar cual es la coordenada del contenedor
        double x = node.getParent().getBoundsInParent().getMinX();
        double y = node.getParent().getBoundsInParent().getMinY();

        // scrolling values range from 0 to 1
        pane.setVvalue(y/height);
        pane.setHvalue(x/width);

        // just for usability
        node.requestFocus();
    }
    
    @FXML 
    public void bloquearEnter(KeyEvent e){
        System.out.println("ENTER DETECTADO");
        e.consume();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setUsuario(TrabajadorDTO usuario){
        this.usuario = usuario;
    }
    
    public TrabajadorDTO getUsuario(){
        return usuario;
    }
    
    public void setControllerPrincipal(VentanaPrincipalController controller){
        controllerPrincipal = controller;
    }
    
    public VentanaPrincipalController getControllerPrincipal(){
        return controllerPrincipal;
    }
    
     public void actualizarDirectorioPersonal(){
        /*Listar todos los contactos Personales del trabajador */
        
        
    try{
        System.out.println("Inicializacion de directorio personal");
        DirectorioPersonalSql baseD = new DirectorioPersonalSql();  
        Directorio directorio =  baseD.getDirectorio(usuario);
        contenedorDirectorioPersonal.getChildren().clear();
        
        List<ContactoDTO> contactos = directorio.getContactos();
        Iterator itr = contactos.iterator();
        TextField campo = null;
        ContactoPersonalDTO contacto = null;
        while(itr.hasNext()){
            contacto =(ContactoPersonalDTO) itr.next();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ContenedorContactoPersonal.fxml"));
            GridPane contenedorContacto = loader.load();
            contenedorContacto.setUserData(loader);
            ContenedorContactoPersonalController controller = loader.getController();
            controller.setContactoPersonal(contacto);
            controller.setControllerPrincipal(controllerPrincipal);
            System.out.println("Mi controller: " + myController);
            controller.setControllerDirectorio(myController);

            //Agregar contacto
            contenedorDirectorioPersonal.getChildren().add(contenedorContacto);
        }
         }catch(SQLException e){
          //No fue posible recolectar el directorio
          System.out.println("Error. No fue posible recolectar el directorio");
        e.printStackTrace();
    }catch(Exception e){
        System.out.println("Error en el procesamiento del directorio");
        e.printStackTrace();
    }
    }
    
    public void actualizarDirectorioEmpresarial(){
        /*Listar todos los contactos empresariales del trabajador*/
        
    try{
        System.out.println("Inicializacion de directorio empresarial");
        DirectorioEmpresarialSql baseD = new DirectorioEmpresarialSql();
        Directorio directorio = baseD.getDirectorio(usuario);
        contenedorDirectorioEmpresarial.getChildren().clear();
        
    
        if(directorio == null){
            //Es la primera vez que se carga el directorio empresarial
            //Recoleccion de los datos desde la base de datos
            DirectorioEmpresarialSql dir = new DirectorioEmpresarialSql();
            directorio = dir.getDirectorio(usuario);
            
        }
        List<ContactoDTO> contactos = directorio.getContactos();
        System.out.println("Numero de contactos: " + contactos.size());
        Iterator itr = contactos.iterator();
        TextField campo = null;
        ContactoEmpresarialDTO contacto = null;
        while(itr.hasNext()){
            contacto =(ContactoEmpresarialDTO) itr.next();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ContenedorContactoEmpresarial.fxml"));
            GridPane contenedorContacto = loader.load();
            contenedorContacto.setUserData(loader);
            ContenedorContactoController controller = loader.getController();
            controller.setContactoEmpresarial(contacto);
            controller.setControllerPrincipal(controllerPrincipal);
            System.out.println("Mi controller: " + myController);
            controller.setControllerDirectorio(myController);
            //Agregar contacto
            contenedorDirectorioEmpresarial.getChildren().add(contenedorContacto);
        }
      }catch(SQLException e){
          //No fue posible recolectar el directorio
          System.out.println("Error. No fue posible recolectar el directorio");
        e.printStackTrace();
    }catch(Exception ex){
        System.out.println("Error en el procesamiento del directorio");
        ex.printStackTrace();
    }
    }
    
    
    @FXML public void agregarContactoEmpresarial(ActionEvent e) {
        // Mostrar una ventana para crear un contacto empresarial
        try{
            Stage ventana = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaContactoEmpresarial.fxml"));
            GridPane root = (GridPane) loader.load();
            VentanaContactoEmpresarialController controller = loader.getController();
            controller.setControllerPrincipal(controllerPrincipal);
            controller.setControllerDirectorio(myController);
            ventana.setScene(new Scene(root));
            ventana.setResizable(false);
            ventana.setTitle("Nuevo Contacto Empresarial");
            ventana.initModality(Modality.WINDOW_MODAL);
            ventana.initOwner(((Node)e.getSource()).getScene().getWindow());
            ventana.show();
        }catch(Exception ex){
            ex.printStackTrace();
            controllerPrincipal.mostrarVentanaError("Error. No fue posible generar el entorno para la creación de un contacto.");
        }
        
        
        
        
    }
    
    @FXML public void agregarContactoPersonal(ActionEvent e){
        // Mostrar una ventana para crear un contacto personal
        try{
            Stage ventana = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaContactoPersonal.fxml"));
            GridPane root = (GridPane) loader.load();
            VentanaContactoPersonalController controller = loader.getController();
            controller.setControllerPrincipal(controllerPrincipal);
            controller.setControllerDirectorio(myController);
            ventana.setScene(new Scene(root));
            ventana.setResizable(false);
            ventana.setTitle("Nuevo Contacto Personal");
            ventana.initModality(Modality.WINDOW_MODAL);
            ventana.initOwner(((Node)e.getSource()).getScene().getWindow());
            ventana.show();
        }catch(Exception exx){
            exx.printStackTrace();
            controllerPrincipal.mostrarVentanaError("Error. No fue posible generar el entorno para la creación de un contacto.");
        }
        
    }
    
    
    public void eliminarContactoEmpresarial(ContactoEmpresarialDTO contacto){      
            Directorio dir = usuario.getDirEmpresarial();
            if(!dir.eliminarContacto(contacto))
                controllerPrincipal.mostrarVentanaError("Error. No fue posible eliminar el contacto de la base de datos");
            else{
                actualizarDirectorioEmpresarial();
                //Eliminar todas las citas del usuario eliminado
                try{
                       CitaDAO baseD = new CitaSql();
                    System.out.println("Citas borradas: " + baseD.delete(contacto.getNombre(),contacto.getId_Trabajador()));
                }catch(SQLException e){
                    controllerPrincipal.mostrarVentanaError("Error. No fue posible borrar datos relacionados con el contacto eliminado");
                } 
      
            }

    }

    public void eliminarContactoPersonal(ContactoPersonalDTO contacto){
        Directorio dir = usuario.getDirPersonal();
        if(dir.eliminarContacto(contacto))
            controllerPrincipal.mostrarVentanaError("Error. No fue posible eliminar el contacto de la base de datos");
        else{
            //Eliminar todas las citas del usuario eliminado
            actualizarDirectorioPersonal();
            
        }
    }
    
}
