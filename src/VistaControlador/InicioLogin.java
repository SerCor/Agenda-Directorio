
package VistaControlador;

import DB.Db;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import DB.TrabajadorDAO;
import DB.TrabajadorSQL;
import DTO.TrabajadorDTO;

public class InicioLogin extends windowBorderlessManager implements Initializable {
    
    public Db database= new Db();
    @FXML protected Label crearCuenta;
    @FXML private TextField textfieldUsuario; 
    @FXML private TextField passwordField;
    @FXML private JFXButton Buttonlogin;
    @FXML private Label warning;
    
   @FXML private void loginAction(ActionEvent event){
        String usuario,pass;
        usuario=this.textfieldUsuario.getText();
        pass= this.passwordField.getText();
        TrabajadorDAO trabajador= new TrabajadorSQL();
        user=new TrabajadorDTO();
        
        try{
            user=trabajador.select(usuario, pass);
            if(user.getUsuario().equals(usuario))
                super.createBienvenida(event);
                //System.out.println("bienvenido "+ user.getNombre()+" :)");
            else  warning.setText("usuario y/o contraseña incorrectos");
        }catch(SQLException e){
            warning.setText("error en la base de datos");
            e.getErrorCode();
        }
    }
    @FXML private void enableButton(){
        if (!textfieldUsuario.getText().isEmpty() && !passwordField.getText().isEmpty()){
            
                Buttonlogin.setDisable(false);
        }
        else Buttonlogin.setDisable(true);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }  
}
