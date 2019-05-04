
package VistaControlador;

import DAO.CitaDAO;
import DAO.CitaSql;
import DAO.ContactoDAO;
import DAO.ContactoEmpresarialSql;
import DAO.ContactoPersonalSql;
import DTO.ContactoEmpresarialDTO;
import DTO.TrabajadorDTO;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javax.swing.ImageIcon;

public class ContenedorContactoController implements Initializable {
   private ContactoEmpresarialDTO contacto;
   @FXML TextField campoEmpresa;
   @FXML TextField campoTelefono;
   @FXML TextField campoGiro;
   @FXML TextArea campoDireccion;
   @FXML TextField campoEmail;
   @FXML TextField campoResponsable;
   @FXML Button actualizarEmpresa;
   @FXML Button actualizarTelefono;
   @FXML Button actualizarGiro;
   @FXML Button actualizarDireccion;
   @FXML Button actualizarEmail;
   @FXML Button actualizarResponsable;
   @FXML Label etiquetaError;
   private String nombreAnterior;
   
    final private Image imgGuardar = new Image(getClass().getResource("guardar.png").toString(),20,20,false,true);
    final private Image imgEscribir = new Image(getClass().getResource("escribir.png").toString(),20,20,false,true);
    private ImageView imgGuarda[];
    private ImageView imgEscribi[];
    private ContenedorDirectorioController controllerDirectorio;
    private VentanaPrincipalController controllerPrincipal;
    private String campoAnterior;
    
    public void setControllerPrincipal(VentanaPrincipalController controller){
        controllerPrincipal = controller;
    }
    
    public void setControllerDirectorio(ContenedorDirectorioController controller){
        controllerDirectorio = controller;
    }
    
    public VentanaPrincipalController getControllerPrincipal(){
        return controllerPrincipal;
    }
    
    public ContenedorDirectorioController getControllerAgenda(){
        return controllerDirectorio;
    }
    
    public void setContactoEmpresarial(ContactoEmpresarialDTO contacto){
        this.contacto = contacto;
       
            //Rellenar campos
            
            //Nombre
            campoEmpresa.setText(contacto.getNombre());
            nombreAnterior = contacto.getNombre();
            
            //Telefono
            campoTelefono.setText(contacto.getTelefono());
            
            //Email
            campoEmail.setText(contacto.getEmail());
            
            //Giro
            campoGiro.setText(contacto.getGiro());
            
            //Direccion
            campoDireccion.setText(contacto.getDireccionPostal());
            
            //Nombre responsable
            campoResponsable.setText(contacto.getRepresentante());
            
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /*Inicializa imagenes necesarias para modificar/guardar cada campo*/
        imgGuarda = new ImageView[6];
        imgEscribi = new ImageView[6];
        
        for(int i = 0; i <6;i++){
            imgGuarda[i] = new ImageView(imgGuardar);
            imgEscribi[i] = new ImageView(imgEscribir);
        }
        
       actualizarEmpresa.setGraphic(imgEscribi[0]);
       actualizarTelefono.setGraphic(imgEscribi[1]);
       actualizarGiro.setGraphic(imgEscribi[2]);
       actualizarDireccion.setGraphic(imgEscribi[3]);
       actualizarEmail.setGraphic(imgEscribi[4]);
       actualizarResponsable.setGraphic(imgEscribi[5]);
            
    }    

    @FXML
    private void habilitarCampo(ActionEvent e) {
        
        /*Evento generado cuando se selecciona el boton de modificar/guarar un campo.
            *Determina en que estado se encuentra el campo y deshabilita/habilita el campo/
            *Hace validaciones de campos obligatorios y validaciones de formato en campo email.
            *Hace validacion de que el campo nombre no sea repetido.
        */
        
        //Determina fuente del evento
        Button btn = ((Button)e.getSource());
        ImageView img = (ImageView)btn.getGraphic();
        boolean actualizarBase = false;
        
        //Determina que campo es el implicado en el evento
    try{
        if(btn  == actualizarEmpresa ){     
            //Nombre Empresa
            if(img.equals(imgGuarda[0])){
                campoEmpresa.setText(campoEmpresa.getText().toUpperCase());
                
                if(campoEmpresa.getText().isEmpty())
                    throw new Exception("Error. Rellena el campo empresa.");
                
                 //Verificacion de si existe un contacto con este nombre
                ContactoDAO baseD = new ContactoPersonalSql();
                ContactoDAO baseD2 = new ContactoEmpresarialSql();
            
            //Verifica si realmente se hicieron cambios
            if(!nombreAnterior.equals(campoEmpresa.getText())){
                if(baseD.select(controllerPrincipal.getUsuario().getIdTrabajador(), campoEmpresa.getText()) || baseD2.select(controllerPrincipal.getUsuario().getIdTrabajador(),campoEmpresa.getText())){
                    //Se encontro contacto con el mismo nombre
                    throw new Exception("Error. Nombre de contacto ya existente.");
                 }
            }
 
            //Actualiza las citas con el mismo nombre
                CitaDAO baseD3 = new CitaSql();
                baseD3.update(campoEmpresa.getText(), controllerPrincipal.getUsuario().getIdTrabajador(),nombreAnterior);
                nombreAnterior = campoEmpresa.getText();
                
                btn.setGraphic(imgEscribi[0]);
                campoEmpresa.setDisable(true);
                etiquetaError.setText("");
                actualizarBase = true;
                contacto.setNombre(campoEmpresa.getText());
            }
            else{
                btn.setGraphic(imgGuarda[0]);
                campoEmpresa.setDisable(false);
            }
            
        }else if(btn  == actualizarTelefono){
            //Telefono
            if(img.equals(imgGuarda[1])){
                if(campoTelefono.getText().isEmpty())
                    throw new Exception("Error. Rellena el campo teléfono.");
                
                btn.setGraphic(imgEscribi[1]);           
                campoTelefono.setDisable(true);
                etiquetaError.setText("");
                actualizarBase = true;
                contacto.setTelefono(campoTelefono.getText());
            }
            else{
                btn.setGraphic(imgGuarda[1]);
                campoTelefono.setDisable(false);
            }
        }else if(btn  == actualizarGiro){
            //Giro
            if(img.equals(imgGuarda[2])){
                if(campoGiro.getText().isEmpty())
                    throw new Exception("Error. Rellena el campo giro.");
                
                btn.setGraphic(imgEscribi[2]);
                campoGiro.setDisable(true);
                etiquetaError.setText("");
                actualizarBase = true;
                contacto.setGiro(campoGiro.getText());
            }
            else{
                btn.setGraphic(imgGuarda[2]);
                campoGiro.setDisable(false);
            }
        }else if(btn  == actualizarDireccion){
            //Direccion
            if(img.equals(imgGuarda[3])){
                if(campoDireccion.getText().isEmpty())
                    throw new Exception("Error. Rellena el campo dirección.");
                
                btn.setGraphic(imgEscribi[3]);                
                campoDireccion.setDisable(true);
                etiquetaError.setText("");
                actualizarBase = true;
                contacto.setDireccionPostal(campoDireccion.getText());
            }
            else{
                btn.setGraphic(imgGuarda[3]);
                campoDireccion.setDisable(false);
            }
        }else if(btn  == actualizarEmail){
            //Email
           if(img.equals(imgGuarda[4])){
            if(campoEmail.getText().isEmpty())
                    throw new Exception("Error. Rellena el campo email.");
            
            //Validacion de correo
               String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";  
               Pattern pattern = Pattern.compile(emailPattern);
               Matcher matcher = pattern.matcher(campoEmail.getText().toLowerCase());
               if (!matcher.matches()) 
                 throw new Exception("Error. Formato de email invalido.");
            
                btn.setGraphic(imgEscribi[4]);      
                campoEmail.setDisable(true);
                etiquetaError.setText("");
                actualizarBase = true;
                contacto.setEmail(campoEmail.getText());
            }
            else{
                btn.setGraphic(imgGuarda[4]);
                campoEmail.setDisable(false);
            }
        }else if(btn  == actualizarResponsable){
            //Nombre responsable
            if(img.equals(imgGuarda[5])){
                if(campoResponsable.getText().isEmpty())
                    throw new Exception("Error. Rellena el campo responsable.");
                
                btn.setGraphic(imgEscribi[5]);    
                campoResponsable.setDisable(true);
                etiquetaError.setText("");
                actualizarBase = true;
                contacto.setRepresentante(campoResponsable.getText());
                
            }
            else{
                btn.setGraphic(imgGuarda[5]);
                campoResponsable.setDisable(false);
            }
        }
        
        if(actualizarBase){
            ContactoEmpresarialSql baseD = new ContactoEmpresarialSql();

                if(baseD.update(contacto)==0)
                    throw new SQLException();
        }
    }catch(SQLException ex){
      FXMLLoader loader = (FXMLLoader) ((Button)e.getSource()).getScene().getWindow().getScene().getUserData();
        VentanaPrincipalController controller = loader.getController();
        controller.mostrarVentanaError("Error. No fue posible actualizar los datos.");
    }catch(Exception exx){
            etiquetaError.setText(exx.getMessage());
        }
    }
    
    @FXML
    public void eliminarContactoEmpresarial(ActionEvent e){
        
        /*Evento que se genera cuando se da click sobre el boton de eliminar en un contactoEmpresarial
         *Contempla la eliminacion de las citas relacionadas con el contacto eliminado
        */
        
        ContactoDAO contact = new ContactoEmpresarialSql();
        Button btn  = (Button ) e.getSource();
        try {
            //Elimialo de la base de datos.
            System.out.println("Eliminado contacto empresarial");
            FXMLLoader loader = (FXMLLoader) btn.getScene().getWindow().getScene().getUserData();
            VentanaPrincipalController controller = (VentanaPrincipalController)loader.getController();
            
            //Eliminando citas relacionadas con trabajador
            CitaDAO baseD2 = new CitaSql();
            baseD2.delete(contacto.getNombre(),contacto.getId_Trabajador());
            
            //Eliminando al trabajador
            ContactoEmpresarialSql baseD = new ContactoEmpresarialSql();
            baseD.delete(contacto);
            
            //actualizar la agenda
            
            controllerDirectorio.actualizarDirectorioEmpresarial();
        } catch (SQLException ex) {
            FXMLLoader loader = (FXMLLoader) ((Button)e.getSource()).getScene().getWindow().getScene().getUserData();
                VentanaPrincipalController controller = loader.getController();
                controller.mostrarVentanaError("Error en la actualización de los datos en la base datos.");
        } 
        
    }
    
    
    

}
