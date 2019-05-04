
package VistaControlador;

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
import java.awt.AWTException;
import java.awt.Dimension;
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
import javafx.geometry.Bounds;
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
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

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
        /*Evento que se genera cuando se da un enter sobre el campo de busqueda de contactos empresarial
            *Determnina en que contenedor esta el contacto y prosigue a hacer un scroll hacia el y poner el puntero del mouse sobre dicho contenedor.
        */
        
        if(e.getCode().equals(KeyCode.ENTER)){
            //Determina si fue un enter
            try{
                System.out.println("Buscando contacto empresarial");
                e.consume();
                
                //Recolecta contenedor de todos los contactos empresarial
                ObservableList<Node> contenedores = contenedorDirectorioEmpresarial.getChildren();
                boolean flagEncontrado = false;
                GridPane contenedorContacto = null;
                FXMLLoader loader = null;
                int posicion = 0;
                Iterator itr = contenedores.iterator();
                
                //Busca el contenedor relacionado con el contacto.
                while(itr.hasNext() && !flagEncontrado){
                    posicion++;
                    contenedorContacto =  (GridPane) itr.next();
                    loader =(FXMLLoader) contenedorContacto.getUserData();
                    ContenedorContactoController controller =(ContenedorContactoController) loader.getController();
                    
                    if(controller.campoEmpresa.getText().equals(campoBusquedaEmpresa.getText().toUpperCase())){
                        //En caso de que lo encuentre hace el llamado a la funcion que hace el scroll y pone el puntero sobre el
                        ensureVisible(scrollDirectorioEmpresarial,contenedorContacto,posicion,0);
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
        
            /*Evento que se genera cuando se da un enter sobre el campo de busqueda de contactos personal
            *Determnina en que contenedor esta el contacto y prosigue a hacer un scroll hacia el y poner el puntero del mouse sobre dicho contenedor.
            */
            
        if(e.getCode().equals(KeyCode.ENTER)){

            try{
                System.out.println("Buscando contacto personal");
                e.consume();
                
                //Recolecta todos los contenedores de contactos personales
                ObservableList<Node> contenedores = contenedorDirectorioPersonal.getChildren();
                GridPane contenedorContacto = null;
                FXMLLoader loader = null;
                Iterator itr = contenedores.iterator();
                boolean flagEncontrado =false;
                int posicion  = 0;
                
                //Busca el contenedor que contiene el contacto personal buscado
                while(itr.hasNext() && !flagEncontrado){
                    posicion++;
                    contenedorContacto =  (GridPane) itr.next();
                    loader =(FXMLLoader) contenedorContacto.getUserData();
                    ContenedorContactoPersonalController controller =(ContenedorContactoPersonalController) loader.getController();
                    if(controller.campoNombre.getText().equals(campoBusquedaPersona.getText().toUpperCase())){  
                        //En caso de ser encontrado llama a la funcion encarga de hacer scroll hacia el y poner el puntero del mouse sobre dicho contenedor.
                        System.out.println("Encontrado");
                        ensureVisible(scrollDirectorioPersonal,contenedorContacto,posicion,1);
                        flagEncontrado = true;
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
                controllerPrincipal.mostrarVentanaError("Error. No fue posible hacer la busqueda del contacto.");
            }
   
        }
        
    }
    
     private  void ensureVisible(ScrollPane pane, Node node,int posicionContenedor,int tipoDirectorio)  {
         
         /*Funcion auxiliar que se encarga de hacer scroll hacia un nodo en especifico y posterior a eso poner el puntero del mouse sobre dicho contenedor*/
         
         //Determina posicion de scroll
        double height = pane.getContent().getBoundsInLocal().getHeight();
        Bounds bounds = pane.getViewportBounds();
        if(tipoDirectorio == 0)
            pane.setVvalue(contenedorDirectorioEmpresarial.getChildren().get((int)posicionContenedor-1).getLayoutY() * (1/(contenedorDirectorioEmpresarial.getHeight()-bounds.getHeight())));
        else
            pane.setVvalue(contenedorDirectorioPersonal.getChildren().get((int)posicionContenedor-1).getLayoutY() * (1/(contenedorDirectorioPersonal.getHeight()-bounds.getHeight())));

        
        
        //Determina movimiento del cursor del mouse hacia el contenedor
        Bounds bound = node.localToScreen(node.getBoundsInLocal());
        Toolkit tool = Toolkit.getDefaultToolkit();
        Dimension dim = tool.getScreenSize();
        
        try{
            MouseCorrectRobot robot = new MouseCorrectRobot();
            int x = 200;
            robot.MoveMouseControlled(((bound.getMinX()+ bound.getMaxX())/2)/dim.getWidth(),((bound.getMinY()+ bound.getMaxY())/2)/dim.getHeight());
        }catch(AWTException e){
            e.printStackTrace();
        }
        

    }
    
    @FXML 
    public void bloquearEnter(KeyEvent e){
        /*Consume enter*/
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
        /*Listar todos los contactos Personales del trabajador en el momento actual */
   
    try{
        System.out.println("Inicializacion de directorio personal");
        
        //Recolecta contactos personales de la base de datos.
        DirectorioPersonalSql baseD = new DirectorioPersonalSql();  
        Directorio directorio =  baseD.getDirectorio(usuario);
        contenedorDirectorioPersonal.getChildren().clear();
        
        //Rellena contenedores con todos los contactos actuales
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
          controllerPrincipal.mostrarVentanaError("Error. No fue posible conectarse a la base de datos");
        e.printStackTrace();
    }catch(Exception e){
        controllerPrincipal.mostrarVentanaError("Error. No fue posible listar los contactos");
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
        // Generar una ventana que maneje la creacion de un contacto empresarial
        
        try{
            
            //Generacion de ventana relacionada con nuevo contacto empresarial
            Stage ventana = new Stage();
            ventana.getIcons().add(new Image(getClass().getResource("agenda.png").toString()));
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
         // Generar una ventana que maneje la creacion de un contacto personal
         
         
        try{
            //Generacion de ventana relacionada con nuevo contacto empresarial

            Stage ventana = new Stage();
            ventana.getIcons().add(new Image(getClass().getResource("agenda.png").toString()));
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
        /* Metodo encargado de eliminar un contacto empresarial y todas las citas relacionadas con el*/
        
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
        /*Metodo encargado de borrar un contacto personal y todas las citas relacionadas con el
        
        */
        Directorio dir = usuario.getDirPersonal();
        if(dir.eliminarContacto(contacto))
            controllerPrincipal.mostrarVentanaError("Error. No fue posible eliminar el contacto de la base de datos");
        else{
            //Eliminar todas las citas del usuario eliminado
            actualizarDirectorioPersonal();
            
        }
    }
    
}
