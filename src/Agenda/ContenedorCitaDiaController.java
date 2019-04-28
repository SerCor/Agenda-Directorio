/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import DTO.CitaDTO;
import DTO.TrabajadorDTO;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ContenedorCitaDiaController implements Initializable {
    @FXML Label etiquetaNombre;
    @FXML Label etiquetaAsunto;
    @FXML Label etiquetaHora;
    @FXML CheckBox check;
    private CitaDTO cita;
    private VentanaPrincipalController controllerPrincipal;
    private ContenedorCitaDiaController myController;
    private ContenedorAgendaController controllerAgenda;
    private TrabajadorDTO usuario;
    
    public void setControllerAgenda(ContenedorAgendaController controller){
        controllerAgenda = controller;
    }
    
    public void setUsuario(TrabajadorDTO usuario){
        this.usuario = usuario;
    }
    
    public void setMyController(ContenedorCitaDiaController controller){
        myController = controller;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setControllerPrincipal(VentanaPrincipalController controller){
        controllerPrincipal = controller;
    }
    
    public void setHora(int hr){
        String contenido = hr + ":00 - "  + hr+1 + ":00";
        etiquetaHora.setText(contenido);
    }
    
    public void setCita(CitaDTO cita){
        this.cita = cita;
        etiquetaNombre.setText(cita.getContacto());
        etiquetaAsunto.setText(cita.getAsunto());
        etiquetaHora.setText(cita.getHrInicio()+":00" + " - " + cita.getHrFin()+":00");
    }
    
    public CitaDTO getCita(){
        return cita;
    }
    
    public boolean isSelect(){
        return check.isSelected();
    }
    
    @FXML
    public void crearModificar(MouseEvent e){
        try{
            System.out.println("Creando/Modificando una sola cita");
            Stage ventana = new Stage();
            ventana.setTitle("Detalles de cita.");
            ventana.setResizable(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaCita.fxml"));
            VBox panel =(VBox) loader.load();
            VentanaCitaController controller = loader.getController();
            controller.setTrabajadorDTO(usuario);
            controller.setControllerAgenda(controllerAgenda);
            List<CitaDTO> citas = new ArrayList<>();

            citas.add(cita);
            if(cita.getId() == -1)
                controller.setCitasNuevas(citas);
            else
                controller.setCitasModificas(citas);
            
            ventana.setScene(new Scene(panel));
            ventana.initModality(Modality.WINDOW_MODAL);
            ventana.initOwner(((Node)e.getSource()).getScene().getWindow());
            ventana.show();
        }catch(Exception ex){
            ex.printStackTrace();
            controllerPrincipal.mostrarVentanaError("Error. No fue posible cargar la pantalla para la modificación de la cita.");
        }
        
        
    }
    
}
