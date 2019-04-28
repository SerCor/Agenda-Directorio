
package Agenda;

import DTO.TrabajadorDTO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


public class BienvenidaController extends windowBorderlessManager implements Initializable {
    
    @FXML Label nombre;
    @FXML Label puesto;
    @FXML Label id_trabajador;
    @FXML
    public void personalizarBienvenida(TrabajadorDTO user){
        id_trabajador.setText(user.getIdTrabajador());
        nombre.setText(user.getNombre());
        puesto.setText(user.getPuesto());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
