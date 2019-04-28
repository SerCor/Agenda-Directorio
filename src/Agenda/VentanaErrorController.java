/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SerCo
 */
public class VentanaErrorController implements Initializable {
    private  String msgError = "";
    @FXML Button btnSalir;
    @FXML Label labelError;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    public void setMensaje(String mensaje){
        labelError.setText(mensaje);
    }
    
    @FXML
    public void salir(ActionEvent e){
        Stage ventana =(Stage) ((Button)e.getSource()).getScene().getWindow();
        ventana.close();
    }
    
    public void salirEnter(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER){
            Stage ventana =(Stage) btnSalir.getScene().getWindow();
            ventana.close();
        }
    }
    
}
