/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import DAO.ContactoDAO;
import DAO.ContactoEmpresarialSql;
import DAO.ContactoPersonalSql;
import DTO.ContactoEmpresarialDTO;
import DTO.ContactoPersonalDTO;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SerCo
 */
public class VentanaContactoEmpresarialController implements Initializable {
    @FXML TextField campoEmpresa;
    @FXML TextField campoTelefono;
    @FXML TextField campoGiro;
    @FXML TextArea campoDireccionPostal;
    @FXML TextField campoEmail;
    @FXML TextField campoResponsable;
    @FXML Button btnGuardar;
    @FXML Label etiquetaError;
    private ContactoEmpresarialDTO contacto;
    private VentanaPrincipalController controllerPrincipal;
    private ContenedorDirectorioController controllerDirectorio;
   
    public void setControllerDirectorio(ContenedorDirectorioController controller){
        controllerDirectorio = controller;
    }
    
    public void setControllerPrincipal(VentanaPrincipalController controller){
        controllerPrincipal = controller;
    }
    
    public void seContacto(ContactoEmpresarialDTO contacto){
        this.contacto = contacto;
        campoEmpresa.setText(contacto.getNombre());
        campoTelefono.setText(contacto.getTelefono());
        campoDireccionPostal.setText(contacto.getDireccionPostal());
        campoEmail.setText(contacto.getEmail());
        campoGiro.setText(contacto.getGiro());   
        campoResponsable.setText(contacto.getRepresentante());
    }
    
    public Boolean validarIntegridadDatos(){
        Boolean banderaRetorno = false;
        try{
            String nombre = campoEmpresa.getText();
            String telefono = campoEmpresa.getText();
            String direccionPostal = campoDireccionPostal.getText();
            String email = campoEmail.getText();
            String giro = campoGiro.getText();
            String nombreResponsable = campoResponsable.getText();
            
            if(nombre.equals("") || telefono.equals("")  || direccionPostal.equals("") || email.equals("") || giro.equals(""))
                throw new Exception("Error. No se puede dejar campos obligatorios vacíos.");
            
            if(nombre.length() >= 46)
                campoEmpresa.setText(nombre.substring(0, 45));
            if(telefono.length() >= 16)
                campoTelefono.setText(telefono.substring(0,16));
            if(direccionPostal.length() >= 16)
                campoDireccionPostal.setText(direccionPostal.substring(0,15));
            if(email.length() >= 101)
                campoEmail.setText(email.substring(0,100));
            if(giro.length() >= 26)
                campoGiro.setText(giro.substring(0, 25));
            if(nombreResponsable.length() >= 15)
                campoResponsable.setText(nombreResponsable.substring(0,15));
            
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
        //Recoleccion de informacion de los campos
            String nombre = campoEmpresa.getText();
            String telefono = campoEmpresa.getText();
            String direccionPostal = campoDireccionPostal.getText();
            String email = campoEmail.getText();
            String giro = campoGiro.getText();
            String nombreResponsable = campoResponsable.getText();
 
        try{
            
            //Verificacion de si existe un contacto con este nombre
            ContactoDAO baseD = new ContactoPersonalSql();
            ContactoDAO baseD2 = new ContactoEmpresarialSql();
            if(baseD.select(controllerPrincipal.getUsuario().getIdTrabajador(), nombre) || baseD2.select(controllerPrincipal.getUsuario().getIdTrabajador(),nombre)){
                //Se encontro contacto con el mismo nombre
                throw new Exception("Error. Nombre de contacto ya existente.");
            }
            
            ContactoEmpresarialSql dirB = new ContactoEmpresarialSql();
//public ContactoEmpresarialDTO(String id_trabajador,String nombre,String telefono,String direccionPostal,String email,String giro,String representante){
            ContactoEmpresarialDTO contacto = new ContactoEmpresarialDTO(controllerPrincipal.getUsuario().getIdTrabajador(),nombre.toUpperCase(),telefono,direccionPostal.toUpperCase(),email.toUpperCase(),giro.toUpperCase(),nombreResponsable.toUpperCase());
            if(dirB.insert(contacto) == 0)
                throw new Exception("Error. No fue posible guardar el nuevo contacto.");
            
            controllerDirectorio.actualizarDirectorioEmpresarial();
            Stage ventana =(Stage) campoEmpresa.getScene().getWindow();
            ventana.close();
        }catch(SQLException e){
            e.printStackTrace();
            etiquetaError.setText("Error. No fue posible la conexión con la base de datos.");
        }catch(Exception ex){
           etiquetaError.setText(ex.getMessage());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    
   
    
}
