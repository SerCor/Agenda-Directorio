/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import DAO.CitaDAO;
import DAO.CitaSql;
import DAO.ContactoDAO;
import DAO.ContactoEmpresarialSql;
import DAO.ContactoPersonalSql;
import DTO.ContactoPersonalDTO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author SerCo
 */
public class ContenedorContactoPersonalController implements Initializable {
    private ContactoPersonalDTO contacto ;
   @FXML TextField campoNombre;
   @FXML TextField campoTelefonoFijo;
   @FXML TextField campoTelefonoCelular;
   @FXML TextArea campoDireccion;
   @FXML TextField campoEmail;
   @FXML TextField campoParentesco;
   @FXML Button actualizaNombre;
   @FXML Button actualizaTelefonoFijo;
   @FXML Button actualizaTelefonoCelular;
   @FXML Button actualizaDireccion;
   @FXML Button actualizaEmail;
   @FXML Button actualizaParentesco;
   @FXML Button btnCerrarSesion;
   @FXML Label etiquetaError;
    final private Image imgGuardar = new Image(getClass().getResource("guardar.png").toString(),20,20,false,true);
    final private Image imgEscribir = new Image(getClass().getResource("escribir.png").toString(),20,20,false,true);
    private ImageView imgGuarda[];
    private ImageView imgEscribi[];
    private VentanaPrincipalController controllerPrincipal;
    private ContenedorDirectorioController controllerDirectorio;
    private String nombreAnterior;

    
    public void setControllerDirectorio(ContenedorDirectorioController controller){
        controllerDirectorio = controller;
    }
    
    public void setControllerPrincipal(VentanaPrincipalController controller){
        controllerPrincipal = controller;
    }
    
    public VentanaPrincipalController getControllerPrincipal(){
        return controllerPrincipal;
    }
    
    public void setContactoPersonal(ContactoPersonalDTO contacto){
        this.contacto = contacto;
        
            //Rellenar campos
            campoDireccion.setText(contacto.getDireccionPostal());
            
            //Nombre
            campoNombre.setText(contacto.getNombre());
            nombreAnterior = contacto.getNombre();
            
            //Telefono Fijo
            campoTelefonoFijo.setText(contacto.getTelefono());
            
            //Telefono Celular
            campoTelefonoCelular.setText(contacto.getTelefonoCelular());
            
            //Email
            campoEmail.setText(contacto.getEmail());
            
            //Parentesco
            campoParentesco.setText(contacto.getParentesco());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgGuarda = new ImageView[6];
        imgEscribi = new ImageView[6];
        
        for(int i = 0; i <6;i++){
            imgGuarda[i] = new ImageView(imgGuardar);
            imgEscribi[i] = new ImageView(imgEscribir);
        }
        
       actualizaNombre.setGraphic(imgEscribi[0]);
       actualizaTelefonoFijo.setGraphic(imgEscribi[1]);
       actualizaTelefonoCelular.setGraphic(imgEscribi[2]);
       actualizaDireccion.setGraphic(imgEscribi[3]);
       actualizaEmail.setGraphic(imgEscribi[4]);
       actualizaParentesco.setGraphic(imgEscribi[5]);
            
    }    

    @FXML
    private void habilitarCampo(ActionEvent e) {
        Button btn = ((Button)e.getSource());
        ImageView img = (ImageView)btn.getGraphic();
        boolean actualizarBase  = false;
    try{
        if(btn == actualizaNombre){     
            if(img.equals(imgGuarda[0])){
                //Nombre
                campoNombre.setText(campoNombre.getText().toUpperCase());
                 if(campoNombre.getText().isEmpty())
                    throw new Exception("Error. Rellena el campo nombre.");
                 
                     //Verificacion de si existe un contacto con este nombre
            ContactoDAO baseD = new ContactoPersonalSql();
            ContactoDAO baseD2 = new ContactoEmpresarialSql();
            
            //Verifica si realmente se hicieron cambios
            if(!nombreAnterior.equals(campoNombre.getText())){
                if(baseD.select(controllerPrincipal.getUsuario().getIdTrabajador(), campoNombre.getText()) || baseD2.select(controllerPrincipal.getUsuario().getIdTrabajador(),campoNombre.getText())){
                    //Se encontro contacto con el mismo nombre
                    throw new Exception("Error. Nombre de contacto ya existente.");
                }   
            }
            
            //Actualiza las citas con el mismo nombre
                CitaDAO baseD3 = new CitaSql();
                baseD3.update(campoNombre.getText(), controllerPrincipal.getUsuario().getIdTrabajador(),nombreAnterior);
                
                nombreAnterior = campoNombre.getText();
                 
                btn.setGraphic(imgEscribi[0]);
                campoNombre.setDisable(true);
                etiquetaError.setText("");
                actualizarBase = true;
                contacto.setNombre(campoNombre.getText());
            }
            else{
                btn.setGraphic(imgGuarda[0]);
                campoNombre.setDisable(false);
            }
            
        }else if(btn == actualizaTelefonoFijo){
            //TelefonoFijo
            if(img.equals(imgGuarda[1])){
                if(campoTelefonoFijo.getText().isEmpty())
                    throw new Exception("Error. Rellena el campo teléfono fijo.");
                
                btn.setGraphic(imgEscribi[1]);
                campoTelefonoFijo.setDisable(true);
                etiquetaError.setText("");
                actualizarBase = true;
                contacto.setTelefono(campoTelefonoFijo.getText());
            }
            else{
                btn.setGraphic(imgGuarda[1]);
                campoTelefonoFijo.setDisable(false);
                
            }
        }else if(btn == actualizaTelefonoCelular){
            //Telefono Celular
            if(img.equals(imgGuarda[2])){
                btn.setGraphic(imgEscribi[2]);
                
                campoTelefonoCelular.setDisable(true);
                etiquetaError.setText("");
                actualizarBase = true;
                contacto.setTelefonoCelular(campoTelefonoCelular.getText());
            }
            else{
                btn.setGraphic(imgGuarda[2]);
                campoTelefonoCelular.setDisable(false);
            }
        }else if(btn == actualizaDireccion){
            //Direccion Postal
            if(img.equals(imgGuarda[3])){
                if(campoDireccion.getText().isEmpty())
                    throw new Exception("Error. Rellena el campo dirección postal.");
                
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
        }else if(btn == actualizaEmail){
            //Email
           if(img.equals(imgGuarda[4])){
               if(campoEmail.getText().isEmpty())
                    throw new Exception("Eror. Rellena el campo email.");
               
                btn.setGraphic(imgEscribi[4]);
                String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
                campoEmail.setDisable(true);
                etiquetaError.setText("");
                actualizarBase = true;
                contacto.setEmail(campoEmail.getText());
            }
            else{
                btn.setGraphic(imgGuarda[4]);
                campoEmail.setDisable(false);
            }
        }else if(btn == actualizaParentesco){
            //Parentesco
            if(img.equals(imgGuarda[5])){
                if(campoParentesco.getText().isEmpty())
                    throw new Exception("Error. Rellena el campo parentesco.");
                
                btn.setGraphic(imgEscribi[5]);
                campoParentesco.setDisable(true);
                etiquetaError.setText("");
                actualizarBase = true;
                contacto.setParentesco(campoParentesco.getText());
            }
            else{
                btn.setGraphic(imgGuarda[5]);
                campoParentesco.setDisable(false);
            }
        }
        
        if(actualizarBase){
            ContactoPersonalSql baseD  = new ContactoPersonalSql();
            
                if(baseD.update(contacto) == 0)
                    throw new SQLException();
        }
    }catch(SQLException ex){
        controllerPrincipal.mostrarVentanaError("Error. No fue posible actualizar los datos en la base de datos."); 
    }catch(Exception exx){
        etiquetaError.setText(exx.getMessage());
    }
}
    
    
    @FXML
    public void eliminarContactoPersonal(ActionEvent e){
        ContactoPersonalSql contact = new ContactoPersonalSql();
        Button btn = (Button) e.getSource();
        try {
            //Elimialo de la base de datos.
            System.out.println("Eliminando contacto personal");
            FXMLLoader loader = (FXMLLoader) btn.getScene().getWindow().getScene().getUserData();
            VentanaPrincipalController controller = (VentanaPrincipalController)loader.getController();
            
            //Elimiando citas relacionadas con el trabajador
            CitaDAO baseD2 = new CitaSql();
           baseD2.delete(contacto.getNombre(),contacto.getId_Trabajador());
           
           //Eliminando trabajador
            ContactoPersonalSql baseD = new ContactoPersonalSql();
            baseD.delete(contacto);
            
            //Actualizar la agenda
            
            controllerDirectorio.actualizarDirectorioPersonal();
        } catch (SQLException ex) {
                ex.printStackTrace();
                controllerPrincipal.mostrarVentanaError("Error. No fue posible actualizar los datos en la base de datos.");     
        }catch(Exception exx){
            exx.printStackTrace();
            controllerPrincipal.mostrarVentanaError("Error. No fue posible refrescar la pantalla.");
        } 
    }
    

    
    
}
