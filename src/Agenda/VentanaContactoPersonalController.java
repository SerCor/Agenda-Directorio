/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import DAO.ContactoPersonalSql;
import DTO.ContactoPersonalDTO;
import DTO.DirectorioPersonalDTO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SerCo
 */
public class VentanaContactoPersonalController implements Initializable {
    @FXML TextField campoNombre;
    @FXML TextField campoTelefonoFijo;
    @FXML TextField campoTelefonoCelular;
    @FXML TextArea campoDireccionPostal;
    @FXML TextField campoEmail;
    @FXML TextField campoParentesco;
    @FXML Button btnGuardar;
    @FXML Label etiquetaError;
    private ContactoPersonalDTO contacto;
    private VentanaPrincipalController controllerPrincipal;
    private ContenedorDirectorioController controllerDirectorio;
   
    public void setControllerDirectorio(ContenedorDirectorioController controller){
        controllerDirectorio = controller;
    }
    
    public void setControllerPrincipal(VentanaPrincipalController controller){
        controllerPrincipal = controller;
    }
    
    
    public void seContacto(ContactoPersonalDTO contacto){
        this.contacto = contacto;
        campoNombre.setText(contacto.getNombre());
        campoTelefonoFijo.setText(contacto.getTelefono());
        campoTelefonoCelular.setText(contacto.getTelefonoCelular());
        campoDireccionPostal.setText(contacto.getDireccionPostal());
        campoEmail.setText(contacto.getEmail());
        campoParentesco.setText(contacto.getParentesco());   
    }
    
    public Boolean validarIntegridadDatos(){
        Boolean banderaRetorno = false;
        try{
            String nombre = campoNombre.getText();
            String telefonoFijo = campoTelefonoFijo.getText();
            String telefonoCelular = campoTelefonoCelular.getText();
            String direccionPostal = campoDireccionPostal.getText();
            String email = campoEmail.getText();
            String parentesco = campoParentesco.getText();
            
            if(nombre.equals("") || telefonoFijo.equals("")  || direccionPostal.equals("") || email.equals("") || parentesco.equals(""))
                throw new Exception("Error. No se puede dejar campos obligatorios vacíos.");
            
            if(nombre.length() >= 46)
                campoNombre.setText(nombre.substring(0, 45));
            if(telefonoFijo.length() >= 16)
                campoTelefonoFijo.setText(telefonoFijo.substring(0,16));
            if(telefonoCelular.length() >= 16)
                campoTelefonoCelular.setText(telefonoCelular.substring(0,15));
            if(direccionPostal.length() >= 101)
                campoDireccionPostal.setText(direccionPostal.substring(0,100));
            if(email.length() >= 26)
                campoEmail.setText(email.substring(0, 25));
            if(parentesco.length() >= 15)
                campoParentesco.setText(parentesco.substring(0,15));
            
            banderaRetorno  = true;
        }catch(Exception e){
            etiquetaError.setText(e.getMessage());
        }finally{
            return banderaRetorno;
        }
    }
    
    @FXML public void guardarContactoBoton(ActionEvent e){
        if(validarIntegridadDatos())
            guardarContacto();
    }
    
    @FXML public void guardarContactoEnter(KeyEvent e){
        if(e.getCode().equals(KeyCode.ENTER))
            if(validarIntegridadDatos())
                guardarContacto();
    }
    
    public void guardarContacto(){
        //Consulta para crear Contacto;
        try{
            
            //Recoleccion de informacion de los campos
            String nombre = campoNombre.getText();
            String telefonoFijo = campoTelefonoFijo.getText();
            String telefonoCelular = campoTelefonoCelular.getText();
            String direccionPostal = campoDireccionPostal.getText();
            String email = campoEmail.getText();
            String parentesco = campoParentesco.getText();
            
            //Creacion e almacenamiento de nuevo objeto
            ContactoPersonalSql dirB = new ContactoPersonalSql();
//    public ContactoPersonalDTO(String id_trabajador,String nombre,String telefonoFijo,String telefonoCelular,String direccionPostal,String email,String parentesco){W
            ContactoPersonalDTO contacto =  new ContactoPersonalDTO(controllerPrincipal.getUsuario().getIdTrabajador(),nombre.toUpperCase(),telefonoFijo,telefonoCelular,direccionPostal.toUpperCase(),email.toUpperCase(),parentesco.toUpperCase());
            if(dirB.insert(contacto) != 1)
                throw new Exception("Error. No fue posible guardar el nuevo contacto.");
            controllerDirectorio.actualizarDirectorioPersonal();
            Stage ventana =(Stage) campoNombre.getScene().getWindow();
            ventana.close();
        }catch(Exception er){
            controllerPrincipal.mostrarVentanaError(er.getMessage());
        }
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
}
