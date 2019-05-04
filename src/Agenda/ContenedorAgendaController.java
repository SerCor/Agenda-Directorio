/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import DAO.AgendaDAO;
import DAO.AgendaSql;
import DAO.CitaDAO;
import DAO.CitaSql;
import DTO.AgendaDTO;
import DTO.CitaDTO;
import DTO.TrabajadorDTO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SerCo
 */
public class ContenedorAgendaController implements Initializable {
    @FXML VBox contenedorCitasDia;
    @FXML VBox contenedorCalendario;
    private LocalDate fechaMostrada ;
    private TrabajadorDTO usuario;
    private  VentanaPrincipalController controllerPrincipal ;
    private ContenedorAgendaController myController;
    @FXML
    private SplitPane contenedorAgenda;
    @FXML
    private Label etiqDia;
    @FXML
    private Label etiquetaAsunto111211;
    @FXML
    private Label etiquetaNombre111211;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnSiguiente;
    @FXML
    private GridPane tablaDias;
    
    
    public void setControllerPrincipal(VentanaPrincipalController controller){
        controllerPrincipal = controller;
    }
    
    public void setMyController(ContenedorAgendaController controller){
        this.myController = controller;
    }
    
    public void setUsuario(TrabajadorDTO usuario){
        this.usuario = usuario;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Button btnAtras  =(Button) contenedorCalendario.lookup("#btnAtras");
        btnAtras.setDisable(true);
        fechaMostrada = LocalDate.now();
        
    }    
    
    @FXML
    private void mostrarMesAnterior(ActionEvent event) {
    /* Boton para ir a la citas del mes anterior
            * Habilita boton hacia adelante(En caso de que este deshabilitado)
            * Actualiza la informacion sobre cada dia con respecto al mes anterior
            * Deshabilita boton anterior en caso de que se llegue al mes actual
        */
    
        System.out.println("Retrasando al mes anterior");
    
        //Atrasa un mes 
        fechaMostrada = fechaMostrada.minusMonths(1);
        
        /*Limpiar interfaz de citas por dia*/
        limpiarTableroCitasDia();
        
        //Actualizar interfaz de citas para el dia 1 del actual mes
        actualizarTableroCitasDia();
        
        //Actualiza interfaz de mes
        actualizaTableroMes();
        
        //Habilita boton hacia adelante
        Button boton =(Button) contenedorCalendario.lookup("#btnSiguiente");
        boton.setDisable(false);
        
        //Cuestiona deshabilitacion de boton hacia atras
        if(fechaMostrada.getMonthValue() == LocalDate.now().getMonthValue() )
         ((Button) event.getSource()).setDisable(true);
    }
    
    @FXML
    private void mostrarMesPosterior(ActionEvent event){
        /* Boton para ir a la citas del dia siguiente
            * Habilita boton hacia atras(En caso de que este deshabilitado)
            * Actualiza la informacion sobre cada dia con respecto al mes proximo
            * Deshabilita boton siguiente en caso de que se supere la fecha maxima de cita
        */
        System.out.println("Avanzando al mes siguiente");
        
        
        //Adelanta un mes 
        fechaMostrada = fechaMostrada.plusMonths(1);
        /*Limpiar interfaz de citas por dia*/
        limpiarTableroCitasDia();
        
        //Actualizar interfaz de citas para el dia 1 del actual mes
        actualizarTableroCitasDia();
        
        //Actualiza interfaz
        actualizaTableroMes();
        
        //Habilita boton hacia atras
        Button btnAtras =(Button) contenedorCalendario.lookup("#btnAtras");
        btnAtras.setDisable(false);
        
        //Cuestiona deshabilitacion de boton hacia adelante         
        if(fechaMostrada.getMonthValue()+1 == LocalDate.now().getMonthValue()){
            ((Button) event.getSource()).setDisable(true);
        }

                 
        
    }
    
    @FXML 
    private void verDia(ActionEvent event) throws IOException{
        /* Actualiza las citas mostradas por dia*/
        System.out.println("Mostrando citas del dia");
        
        /*Limpiar interfaz de citas por dia*/
        limpiarTableroCitasDia();
        
        Button boton  = (Button) event.getSource();
        String id = boton.getId();
        int day = 0;
        String number = id.substring(id.length()-2);
        if(Character.isAlphabetic(number.charAt(0)))
            day = Integer.parseInt(number.substring(number.length()-1));
        else
            day = Integer.parseInt(number.substring(number.length()-2));
        
        fechaMostrada = fechaMostrada.withDayOfMonth(day);
        actualizarTableroCitasDia();
        
    }
    
    
     public void actualizaTableroMes(){
     
        /* Obtiene el anio y mes de la fecha*/
        LocalDate fecha = fechaMostrada;
        Label mes =(Label) contenedorCalendario.lookup("#etiquetaMes");
        int Fmes = fecha.getMonth().getValue();
        int Fanio = fecha.getYear();
        String cadena = null;
        
        switch(Fmes){
            case 1:
                cadena = "Enero";
            break;
            
            case 2:
                cadena = "Febrero";
            break;
            
            case 3:
                cadena = "Marzo";
            break;
            
            case 4:
                cadena = "Abril";
            break;
            
            case 5:
                cadena = "Mayo";
            break;
            
            case 6:
                cadena = "Junio";
            break;
            
            case 7:
                cadena = "Julio";
            break;
            
            case 8:
                cadena = "Agosto";
            break;
            
            case 9:
                cadena = "Septiembre";
            break;
            
            case 10:
                cadena = "Octubre"; 
            break;
            
            case 11:
                cadena = "Noviembre";
            break;
            
            case 12:
                cadena = "Diciembre";
            break;
        }
        
        cadena += " " + String.valueOf(Fanio);
        mes.setText(cadena);
        
        /*Obten de dia que empieza el mes*/
        LocalDate aux = LocalDate.parse(fechaMostrada.toString());
        aux = aux.minusDays(fechaMostrada.getDayOfMonth()-1);
        DayOfWeek primerDiaMess = aux.getDayOfWeek();
        int primerDiaMes = primerDiaMess.getValue();
        
        //Determina si se encuentra en el mes actual para bloquear los dias que ya pasaron
        int diasPasados = 0;
        LocalDate actual = LocalDate.now();
        if(fechaMostrada.getMonthValue() == actual.getMonthValue()){
            diasPasados = actual.getDayOfMonth();
            System.out.println("Dia de mes: " + diasPasados);
        }
        
        /*Obtiene el panel donde se muestra los dias*/
        GridPane tablaDias = (GridPane) contenedorCalendario.lookup("#tablaDias");
        int contador = 1;
        int numeroDias = aux.plusMonths(1).minusDays(1).getDayOfMonth();
        
        Label etiqueta = null;
        Button boton  = null;
        Set<Node> etiquetaDia = tablaDias.lookupAll(".etiquetaDia");
        Set<Node> etiquetaBoton = tablaDias.lookupAll(".botonDia");
        Iterator itrEtiqueta = etiquetaDia.iterator();
        Iterator itrBoton  =  etiquetaBoton.iterator();
        
        //Salta los dias primeros que no son del mes
            primerDiaMes -= 1;
            if(primerDiaMes < 0)
                primerDiaMes = 6;
            for(int i = 0 ; i < primerDiaMes;i++){
                boton = (Button) itrBoton.next();
                boton.setDisable(true);
                etiqueta = (Label) itrEtiqueta.next();
                etiqueta.setText("");
            }
        
        //Muestra los dias que son del mes
        while(itrEtiqueta.hasNext() && itrBoton.hasNext() && numeroDias >= contador){
            boton = (Button) itrBoton.next();
            etiqueta = (Label) itrEtiqueta.next();
            etiqueta.setText(String.valueOf(contador));
            if(contador >= diasPasados){
                boton.setDisable(false);
                boton.setId("day" + String.valueOf(contador));
            }else{
                boton.setDisable(true);
            }
            contador++;
        }
        
        //Limpia los dias finales que no son del mes
        
        for(int i = contador; i < 36 && itrBoton.hasNext() && itrEtiqueta.hasNext();i++){
                boton = (Button) itrBoton.next();
                boton.setDisable(true);
                etiqueta = (Label) itrEtiqueta.next();
                etiqueta.setText("");
            }
        
        
    }
    
    public void actualizarTableroCitasDia(){
        /*Actualiza el scrollPane con los datos de todas las citas relacionadas con el dia que genero el evento de ver citas*/
        System.out.println("Actualizando tableor por dia");
        Label etiquetaDia =(Label) contenedorCitasDia.lookup("#etiqDia");
        try{

            etiquetaDia.setText("Dia " + fechaMostrada.getDayOfMonth());
            ObservableList<Node> listaCitas = contenedorCitasDia.getChildren();

            
           if(listaCitas.size() == 2){  //Contiene solo el encabezado del dia y un area de botones.
               System.out.println("Se cargan los datos 1ra forma");
               //Primera vez que se cargan los horarios de citas
               
               //Genera conentenedores default
               for(int i = 0; i < 12;i++){
                   //Rellena contenedores de citas
                   CitaDTO cita = new CitaDTO();
                   cita.setHrInicio(i+7);
                   cita.setHrFin(i+8);
                   System.out.println("Fecha mostrada: "+fechaMostrada);
                   cita.setFecha(fechaMostrada);
                   cita.setIdTrabajador(usuario.getIdTrabajador());
                   FXMLLoader loader = new FXMLLoader(getClass().getResource("ContenedorCitaDia.fxml"));
                   HBox contenedor = loader.load();
                   ContenedorCitaDiaController controller = loader.getController();
                   controller.setUsuario(usuario);
                   controller.setCita(cita);
                   controller.setMyController(controller);
                   controller.setControllerPrincipal(controllerPrincipal);
                   controller.setControllerAgenda(myController);
                   contenedor.setUserData(controller);
                   contenedorCitasDia.getChildren().add(contenedor);
               }
               
               //Rellena citas que estan almacenadas en la base de datos.
               AgendaDAO baseD = new AgendaSql();
               AgendaDTO agenda = baseD.select(usuario, fechaMostrada);
               ObservableList<Node> contenedor = contenedorCitasDia.getChildren();
               HBox contenedorAux = null;
               List<CitaDTO> lista = agenda.getCitas();
               CitaDTO cita = null;
               
               for(int i= 0; i< lista.size() ;i++){
                   cita = (CitaDTO)lista.get(i);
                   contenedorAux = (HBox )contenedor.get(cita.getHrInicio()-5);
                   ContenedorCitaDiaController controller = (ContenedorCitaDiaController)contenedorAux.getUserData();
                   cita.setIdTrabajador(controller.getCita().getIdTrabajador());
                   cita.setFecha(fechaMostrada);
                   controller.setCita(cita);
                }
               
               
               
           }else{
               System.out.println("Se cargan los datos 2da forma");
               //Actualizar  valores con referencia al nuevo dia. Ya se habia cargado anteriormente los contenedores para las citas.
               ObservableList<Node> contenedorCita = contenedorCitasDia.getChildren();
               HBox contenedorAux = null;
               
               //Rellena los contenedores con informacion por default
               for(int i = 2; i< contenedorCita.size() ; i++){
                   contenedorAux = (HBox)contenedorCita.get(i);
                   ContenedorCitaDiaController controller = (ContenedorCitaDiaController)contenedorAux.getUserData();
                   CitaDTO aux = new CitaDTO();
                   aux.setHrInicio(controller.getCita().getHrInicio());
                   aux.setHrFin(controller.getCita().getHrFin());
                   aux.setFecha(fechaMostrada);
                   aux.setIdTrabajador(controller.getCita().getIdTrabajador());
                   controller.setCita(aux);
               }
               
               //Rellena citas que estan almacenadas en la base de datos.
               AgendaDAO baseD = new AgendaSql();
               AgendaDTO agenda = baseD.select(usuario, fechaMostrada);
               ObservableList<Node> contenedor = contenedorCitasDia.getChildren();
               List<CitaDTO> lista = agenda.getCitas();
               CitaDTO cita = null;
               
               for(int i= 0; i< lista.size() ;i++){
                   cita = (CitaDTO)lista.get(i);
                   System.out.println(cita);
                   contenedorAux = (HBox )contenedor.get(cita.getHrInicio()-5);
                   ContenedorCitaDiaController controller = (ContenedorCitaDiaController)contenedorAux.getUserData();
                   controller.setCita(cita);
                }
               
           }
 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void limpiarTableroCitasDia(){
        /*Recolecta todos los CheckBox y los pone como no seleccionados */
        Set<Node> checks = contenedorCitasDia.lookupAll("CheckBox");
        Iterator itr = checks.iterator();
        CheckBox box = null;
        
        while(itr.hasNext()){
            box = (CheckBox) itr.next();
            box.setSelected(false);
        }
    }

    @FXML
    public void crearModificarCita(MouseEvent e){
        //Evento que se genera cuando solo se va a crear/modificar una unica cita.
        
        
    }
    
    @FXML
    public void crearModificarCitas(ActionEvent e){
        //Evento que se genera al intentar crear/modificar varias citas.
        System.out.println("Creando/Modificando varias citas");
        try {
            List<CitaDTO> citasModificadas = new ArrayList<>();
            List<CitaDTO> citasNuevas = new ArrayList<>();
            Iterator itr = contenedorCitasDia.getChildren().iterator();
            itr.next(); // Etiqueta de encabezado
            itr.next(); // Zona de botones
            HBox contenedorCita = null;
            CheckBox check = null;
            
            //Separa los los checks seleccionados y determina si son citas nuevas o citas existentes
            while(itr.hasNext()){
                contenedorCita = (HBox) itr.next();
                ContenedorCitaDiaController controller =(ContenedorCitaDiaController) contenedorCita.getUserData();

                if(controller.isSelect()){ //Si el check de esa cita esta marcada...
                    CitaDTO cita = controller.getCita();
                    if(cita.getId() == -1){ //Significa que la cita aun no existe
                        cita.setFecha(fechaMostrada);
                        citasNuevas.add(cita);
                    }
                    else
                        citasModificadas.add(cita);
                }       
            }
               
               //No se selecciono ningun check
               if(citasNuevas.isEmpty() && citasModificadas.isEmpty())
                   throw new Exception();
               
                Stage ventana = new Stage();
                ventana.getIcons().add(new Image(getClass().getResource("agenda.png").toString()));
                VBox root;  
                FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaCita.fxml"));
                root = loader.load();
                VentanaCitaController controller = loader.getController();
                controller.setTrabajadorDTO(usuario);
                controller.setCitasNuevas(citasNuevas);
                controller.setCitasModificas(citasModificadas);
                controller.setControllerAgenda(myController);
                
                Scene scene = new Scene(root);
                ventana.setScene(scene);
                ventana.setResizable(false);
                ventana.setTitle("Detalles de cita.");
                ventana.initModality(Modality.WINDOW_MODAL);
                ventana.initOwner(((Node)e.getSource()).getScene().getWindow());
                ventana.show();
            }catch (IOException ex) {
                Logger.getLogger(VentanaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }catch(Exception exx){
                controllerPrincipal.mostrarVentanaError("Error. Es necesario seleccionar al menos una cita para realizar esta acción");
            }finally{
            //Limpiar checks
            limpiarTableroCitasDia();
        }   
            
    }
    
    @FXML
    public void limpiarCita(ActionEvent e){
        List<CitaDTO> citasModificadas = new ArrayList<>();
            List<CitaDTO> citasNuevas = new ArrayList<>();
            Iterator itr = contenedorCitasDia.getChildren().iterator();
            itr.next(); // Etiqueta de encabezado
            itr.next(); // Zona de botones
            HBox contenedorCita = null;
            CheckBox check = null;
            
            //Separa los los checks seleccionados y determina si son citas nuevas o citas existentes
            while(itr.hasNext()){
                contenedorCita = (HBox) itr.next();
                ContenedorCitaDiaController controller =(ContenedorCitaDiaController) contenedorCita.getUserData();
                

                if(controller.isSelect()){ //Si el check de esa cita esta marcada...
                    CitaDTO cita = controller.getCita();
                    if(cita.getId() == -1) //Significa que la cita aun no existe
                        citasNuevas.add(cita);
                    else
                        citasModificadas.add(cita);
                }       
            }
               try{
                    //No se selecciono ningun check
                    if( citasModificadas.isEmpty())
                        throw new Exception();

                     Iterator itrCitas = citasModificadas.iterator();
                     CitaDAO baseD = new CitaSql();

                         while(itrCitas.hasNext()){
                             System.out.println("Borrado!!");
                             baseD.delete((CitaDTO) itrCitas.next());
                         }
                    actualizarTableroCitasDia();
                }catch(SQLException ex){
                    controllerPrincipal.mostrarVentanaError("Error. No fue posible eliminar las citas en la base de datos");
                }catch(Exception exx){
                    controllerPrincipal.mostrarVentanaError("Error. Es necesario seleccionar al menos una cita para realizar esta acción");
                }
               
               
               //Limpiar checks
               limpiarTableroCitasDia();
               
               
    }

    
}
