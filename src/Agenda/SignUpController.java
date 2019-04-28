
package Agenda;

import DB.TrabajadorDAO;
import DB.TrabajadorSQL;
import DTO.TrabajadorDTO;
import com.jfoenix.controls.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class SignUpController extends windowBorderlessManager implements Initializable {
    @FXML Label warning;
    @FXML JFXButton crearSesion;
    @FXML JFXTextField nombre,apellidos,idTrabajador,usuario;
    @FXML JFXComboBox puesto;
    @FXML JFXTimePicker hrEntrada,hrComida,hrSalida;
    @FXML JFXPasswordField password,confirmPassword;
    ObservableList<String> PUESTOS=FXCollections.observableArrayList("Consultor","Vendedor");
    
    
    @FXML private void enableButton(){
        if(!nombre.getText().isEmpty() && !apellidos.getText().isEmpty() && 
           !idTrabajador.getText().isEmpty() && !(puesto.getValue()==null) &&
           !usuario.getText().isEmpty() && !(hrEntrada.getValue()==null) &&
           !(hrComida.getValue()==null) && !(hrSalida.getValue()==null) &&
           !password.getText().isEmpty() && !confirmPassword.getText().isEmpty()){
            crearSesion.setDisable(false);
        }
        else crearSesion.setDisable(true);
    }
    @FXML private void validateForm(ActionEvent e){
        /***Aqui se hace la consulta a la base de datos y se verifica que el codigo del trabajdor
         y el nombre de usuario no esten repetidos en la base de datos***/
        System.out.print("akiiii");
        TrabajadorDAO trabajador=new TrabajadorSQL();
        String nom,id,pass,usu,puestoaux;
        String entrada,salida,comida;
        
        nom=nombre.getText()+" "+apellidos.getText();
        usu=usuario.getText();
        id=idTrabajador.getText();
        entrada=hrEntrada.getValue().toString();
        comida=hrComida.getValue().toString();
        salida=hrSalida.getValue().toString();
        puestoaux=puesto.getValue().toString();
        pass=password.getText();
                
        user=new TrabajadorDTO(nom,id,usu,puestoaux,entrada,comida,salida,pass);
        
        try {
            if(!trabajador.insert(user))
                warning.setText("Revisa tu ID o cambia tu nombre de usuario");
            else{
                super.createAlerta(e);
                System.out.println("cuenta creada");
            }
        } catch (SQLException ex) {
            warning.setText("Revisa tu ID o cambia tu nombre de usuario");
        }        
    }
    
    @FXML private void validatePassword(){
        if(!password.getText().equals(confirmPassword.getText()))
            warning.setText("las contraseñas no coinciden");
        else
            warning.setText("");
            
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        puesto.setItems(PUESTOS);
        
    }    
    
}
