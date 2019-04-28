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
import DAO.ContactoDAO;
import DAO.ContactoEmpresarialSql;
import DAO.ContactoPersonalSql;
import DAO.DirectorioDAO;
import DAO.DirectorioEmpresarialSql;
import DAO.DirectorioPersonalSql;
import DTO.*;
 import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaPrincipalController implements Initializable {
    @FXML TabPane contenedorPrincipal;
    @FXML Tab tabAgenda;
    @FXML Tab tabDirectorio;
    private TabPane directorio;
    private SplitPane agenda;
    /*@FXML VBox contenedorAgenda;
    @FXML VBox contenedorDirectorioPersonal;
    @FXML VBox contenedorDirectorioEmpresarial;
    @FXML VBox contenedorCitasDia;
    @FXML ScrollPane ScrollDirectorioPersonal;
    @FXML ScrollPane ScrollDirectorioEmpresarial;
    LocalDate fechaMostrada ;*/
    private TrabajadorDTO usuario;

    public void setTrabajadorDTO(TrabajadorDTO usuario){
        this.usuario = usuario;
    }
    
    public TrabajadorDTO getUsuario(){
        return usuario;
    }
    
    
    
//    @FXML
//    private void mostrarMesAnterior(ActionEvent event) {
//    /* Boton para ir a la citas del mes anterior
//            * Habilita boton hacia adelante(En caso de que este deshabilitado)
//            * Actualiza la informacion sobre cada dia con respecto al mes anterior
//            * Deshabilita boton anterior en caso de que se llegue al mes actual
//        */
//    
//        System.out.println("Retrasando al mes anterior");
//    
//        //Atrasa un mes 
//        fechaMostrada = fechaMostrada.minusMonths(1);
//        
//        /*Limpiar interfaz de citas por dia*/
//        limpiarTableroCitasDia();
//        
//        //Actualizar interfaz de citas para el dia 1 del actual mes
//        actualizarTableroCitasDia();
//        
//        //Actualiza interfaz de mes
//        actualizaTableroMes(fechaMostrada);
//        
//        //Habilita boton hacia adelante
//        Button boton =(Button) contenedorAgenda.lookup("#btnSiguiente");
//        boton.setDisable(false);
//        
//        //Cuestiona deshabilitacion de boton hacia atras
//        if(fechaMostrada.getMonthValue() == LocalDate.now().getMonthValue() )
//         ((Button) event.getSource()).setDisable(true);
//    }
//    
//    @FXML
//    private void mostrarMesPosterior(ActionEvent event){
//        /* Boton para ir a la citas del dia siguiente
//            * Habilita boton hacia atras(En caso de que este deshabilitado)
//            * Actualiza la informacion sobre cada dia con respecto al mes proximo
//            * Deshabilita boton siguiente en caso de que se supere la fecha maxima de cita
//        */
//        System.out.println("Avanzando al mes siguiente");
//        
//        
//        //Adelanta un mes 
//        fechaMostrada = fechaMostrada.plusMonths(1);
//        /*Limpiar interfaz de citas por dia*/
//        limpiarTableroCitasDia();
//        
//        //Actualizar interfaz de citas para el dia 1 del actual mes
//        actualizarTableroCitasDia();
//        
//        //Actualiza interfaz
//        actualizaTableroMes(fechaMostrada);
//        
//        //Habilita boton hacia atras
//        Button btnAtras =(Button) contenedorAgenda.lookup("#btnAtras");
//        btnAtras.setDisable(false);
//        
//        //Cuestiona deshabilitacion de boton hacia adelante         
//        if(fechaMostrada.getMonthValue()+1 == LocalDate.now().getMonthValue()){
//            ((Button) event.getSource()).setDisable(true);
//        }
//
//                 
//        
//    }
//    
//    @FXML 
//    private void verDia(ActionEvent event) throws IOException{
//        /* Actualiza las citas mostradas por dia*/
//        System.out.println("Mostrando citas del dia");
//        
//        /*Limpiar interfaz de citas por dia*/
//        limpiarTableroCitasDia();
//        
//        Button boton  = (Button) event.getSource();
//        String id = boton.getId();
//        int day = 0;
//        String number = id.substring(id.length()-2);
//        if(Character.isAlphabetic(number.charAt(0)))
//            day = Integer.parseInt(number.substring(number.length()-1));
//        else
//            day = Integer.parseInt(number.substring(number.length()-2));
//        
//        fechaMostrada = fechaMostrada.withDayOfMonth(day);
//        actualizarTableroCitasDia();
//        
//    }
//    
//   
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

    }
    
    public void incializaEscenaDirectorio(){
        try{         
            //Cargar Escena Directorio
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("ContenedorDirectorio.fxml"));
            directorio =  (TabPane)loader1.load();
            ContenedorDirectorioController controllerDirectorio =(ContenedorDirectorioController) loader1.getController();
            FXMLLoader loader =(FXMLLoader) (contenedorPrincipal.getScene().getUserData());
            VentanaPrincipalController controllerPrincipal = loader.getController();
            controllerDirectorio.setControllerPrincipal(controllerPrincipal);
            controllerDirectorio.setUsuario(usuario);
            controllerDirectorio.setMyController(controllerDirectorio);
            controllerDirectorio.actualizarDirectorioEmpresarial();
            controllerDirectorio.actualizarDirectorioPersonal();
            Scene scene =  new Scene(directorio);
            scene.setUserData(loader1);
            tabDirectorio.setContent(scene.getRoot());  
            contenedorPrincipal.getSelectionModel().select(contenedorPrincipal.getTabs().get(1)); //Seleccionar el tab de Agenda
            
        }catch(Exception e){
            e.printStackTrace();
            mostrarVentanaError("Error.No fue posible cargar los datos del directorio.");
        }      
    }
    
    public void inicializaEscenaAgenda(){
         //Cargar Escena Agenda
         try{
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("ContenedorAgenda.fxml"));
            agenda = (SplitPane)loader1.load();
            ContenedorAgendaController controllerAgenda = (ContenedorAgendaController)loader1.getController();        
            controllerAgenda.setMyController(controllerAgenda);
            FXMLLoader loader =(FXMLLoader) (contenedorPrincipal.getScene().getUserData());
            VentanaPrincipalController controllerPrincipal = loader.getController();
            controllerAgenda.setControllerPrincipal(controllerPrincipal);
            controllerAgenda.setUsuario(usuario);
            controllerAgenda.actualizaTableroMes();
            controllerAgenda.actualizarTableroCitasDia();
            
            System.out.println(agenda);
            tabAgenda.setContent(agenda);
         }catch(Exception e){
             e.printStackTrace();
             mostrarVentanaError("Error.No fue posible cargar los datos de la agenda.");
         }
    }
    
//    public void actualizarTodo(){
//        actualizaTableroMes(fechaMostrada);
//        actualizarTableroCitasDia();    //Mostrar citas del dia actual
//        actualizarDirectorioPersonal();
//        actualizarDirectorioEmpresarial();
//    }
    
//    public void actualizaTableroMes(LocalDate fecha){
//     
//        /* Obtiene el anio y mes de la fecha*/
//        Label mes =(Label) contenedorAgenda.lookup("#etiquetaMes");
//        int Fmes = fecha.getMonth().getValue();
//        int Fanio = fecha.getYear();
//        String cadena = null;
//        
//        switch(Fmes){
//            case 1:
//                cadena = "Enero";
//            break;
//            
//            case 2:
//                cadena = "Febrero";
//            break;
//            
//            case 3:
//                cadena = "Marzo";
//            break;
//            
//            case 4:
//                cadena = "Abril";
//            break;
//            
//            case 5:
//                cadena = "Mayo";
//            break;
//            
//            case 6:
//                cadena = "Junio";
//            break;
//            
//            case 7:
//                cadena = "Julio";
//            break;
//            
//            case 8:
//                cadena = "Agosto";
//            break;
//            
//            case 9:
//                cadena = "Septiembre";
//            break;
//            
//            case 10:
//                cadena = "Octubre"; 
//            break;
//            
//            case 11:
//                cadena = "Noviembre";
//            break;
//            
//            case 12:
//                cadena = "Diciembre";
//            break;
//        }
//        
//        cadena += " " + String.valueOf(Fanio);
//        mes.setText(cadena);
//        
//        /*Obten de dia que empieza el mes*/
//        LocalDate aux = LocalDate.parse(fechaMostrada.toString());
//        aux = aux.minusDays(fechaMostrada.getDayOfMonth()-1);
//        DayOfWeek primerDiaMess = aux.getDayOfWeek();
//        int primerDiaMes = primerDiaMess.getValue();
//        
//        /*Obtiene el panel donde se muestra los dias*/
//        GridPane tablaDias = (GridPane) contenedorAgenda.lookup("#tablaDias");
//        int contador = 1;
//        int numeroDias = aux.plusMonths(1).minusDays(1).getDayOfMonth();
//        
//        Label etiqueta = null;
//        Button boton  = null;
//        Set<Node> etiquetaDia = tablaDias.lookupAll(".etiquetaDia");
//        Set<Node> etiquetaBoton = tablaDias.lookupAll(".botonDia");
//        Iterator itrEtiqueta = etiquetaDia.iterator();
//        Iterator itrBoton  =  etiquetaBoton.iterator();
//        
//        //Salta los dias primeros que no son del mes
//            primerDiaMes -= 1;
//            if(primerDiaMes < 0)
//                primerDiaMes = 6;
//            for(int i = 0; i < primerDiaMes;i++){
//                boton = (Button) itrBoton.next();
//                boton.setDisable(true);
//                etiqueta = (Label) itrEtiqueta.next();
//                etiqueta.setText("");
//            }
//        
//        //Muestra los dias que son del mes
//        while(itrEtiqueta.hasNext() && itrBoton.hasNext() && numeroDias >= contador){
//            boton = (Button) itrBoton.next();
//            boton.setDisable(false);
//            boton.setId("day" + String.valueOf(contador));
//            etiqueta = (Label) itrEtiqueta.next();
//            etiqueta.setText(String.valueOf(contador));
//            contador++;
//        }
//        
//        //Limpia los dias finales que no son del mes
//        
//        for(int i = contador; i < 36 && itrBoton.hasNext() && itrEtiqueta.hasNext();i++){
//                boton = (Button) itrBoton.next();
//                boton.setDisable(true);
//                etiqueta = (Label) itrEtiqueta.next();
//                etiqueta.setText("");
//            }
//        
//        
//    }
//    
//    public void actualizarTableroCitasDia(){
//        /*Actualiza el scrollPane con los datos de todas las citas relacionadas con el dia que genero el evento de ver citas*/
//        Label etiquetaDia =(Label) contenedorCitasDia.lookup("#etiqDia");
//        try{
//            AgendaDAO agend = new AgendaSql();
//            AgendaDTO agenda =  agend.select(usuario,fechaMostrada);
//            
//          
//            etiquetaDia.setText("Dia " + fechaMostrada.getDayOfMonth());
//             
//            Set<Node> contenedoresCita = contenedorCitasDia.lookupAll(".contenedorCita");
//            Set<Node> etiquetasAsunto  = contenedorCitasDia.lookupAll(".etiquetaAsunto");
//            Set<Node> etiquetasNombre = contenedorCitasDia.lookupAll(".etiquetaNombre");
//            Object contenedorCita[] = contenedoresCita.toArray();
//            Object etiquetaAsunto[] = etiquetasAsunto.toArray();
//            Object etiquetaNombre[] = etiquetasNombre.toArray();
//            
//            //Valores para citas vacias
//            for(int i =0 ;i < etiquetaAsunto.length;i++){
//                ((HBox)contenedorCita[i]).setId("Cita-1");
//                ((Label)etiquetaAsunto[i]).setText("Sin asignar");
//                ((Label)etiquetaNombre[i]).setText("Sin asignar");
//            }
//            
//            
//            //Rellenar las ocupadas
//            List<CitaDTO> citas = agenda.getCitas();
//            CitaDTO cita = null;
//            int hr_inicio = 0;
//            Iterator itr = citas.iterator();
//            while(itr.hasNext()){
//                cita = (CitaDTO) itr.next();
//                hr_inicio = cita.getHrInicio();
//                ((HBox)contenedorCita[hr_inicio-7]).setId("Cita"+cita.getId()); //El contenedor contiene el id de la cita.
//                ((Label)etiquetaAsunto[hr_inicio-7]).setText(cita.getAsunto());
//                ((Label)etiquetaNombre[hr_inicio-7]).setText(cita.getContacto());
//            }
//            
//            
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    
//    public void limpiarTableroCitasDia(){
//        /*Recolecta todos los CheckBox y los pone como no seleccionados */
//        
//        Set<Node> checks = contenedorCitasDia.lookupAll("CheckBox");
//        Iterator itr = checks.iterator();
//        CheckBox box = null;
//        
//        while(itr.hasNext()){
//            box = (CheckBox) itr.next();
//            box.setSelected(false);
//        }
//    }
//
//    
//    public void actualizarDirectorioPersonal(){
//        /*Listar todos los contactos Personales del trabajador */
//        
//        
//    try{
//        System.out.println("Inicializacion de directorio empresarial");
//        DirectorioPersonalSql baseD = new DirectorioPersonalSql();  
//        Directorio directorio =  baseD.getDirectorio(usuario);
//        GridPane encabezado = (GridPane)contenedorDirectorioPersonal.lookup("#encabezado");
//        contenedorDirectorioPersonal.getChildren().clear();
//        contenedorDirectorioPersonal.getChildren().add(encabezado);
//        
//        List<ContactoDTO> contactos = directorio.getContactos();
//        Iterator itr = contactos.iterator();
//        TextField campo = null;
//        ContactoPersonalDTO contacto = null;
//        while(itr.hasNext()){
//            contacto =(ContactoPersonalDTO) itr.next();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("ContenedorContactoPersonal.fxml"));
//            GridPane contenedorContacto = loader.load();
//            ContenedorContactoPersonalController controller = loader.getController();
//            controller.setContactoPersonal(contacto);
//            Set<Node> componentes = contenedorContacto.lookupAll("TextField");
//            Iterator itr2 = componentes.iterator();
//            //Rellenar campos
//            TextArea direccion =(TextArea) contenedorContacto.lookup("TextArea");
//            direccion.setText(contacto.getDireccionPostal());
//            
//            //Nombre
//            campo = (TextField) itr2.next(); 
//            campo.setText(contacto.getNombre());
//            
//            //Telefono Fijo
//            campo = (TextField) itr2.next(); 
//            campo.setText(contacto.getTelefono());
//            
//            //Telefono Celular
//            campo = (TextField) itr2.next();
//            campo.setText(contacto.getTelefonoCelular());
//            
//            //Email
//            campo = (TextField) itr2.next(); 
//            campo.setText(contacto.getEmail());
//            
//            //Parentesco
//            campo = (TextField) itr2.next(); 
//            campo.setText(contacto.getParentesco());
//           
//            //Agregar contacto
//            contenedorDirectorioPersonal.getChildren().add(contenedorContacto);
//        }
//         }catch(SQLException e){
//          //No fue posible recolectar el directorio
//          System.out.println("Error. No fue posible recolectar el directorio");
//        e.printStackTrace();
//    }catch(Exception e){
//        System.out.println("Error en el procesamiento del directorio");
//        e.printStackTrace();
//    }
//    }
//    
//    public void actualizarDirectorioEmpresarial(){
//        /*Listar todos los contactos empresariales del trabajador*/
//        
//    try{
//        System.out.println("Inicializacion de directorio empresarial");
//        DirectorioEmpresarialSql baseD = new DirectorioEmpresarialSql();
//        Directorio directorio = baseD.getDirectorio(usuario);
//        GridPane encabezado = (GridPane)contenedorDirectorioEmpresarial.lookup("#encabezado");
//        contenedorDirectorioEmpresarial.getChildren().clear();
//        contenedorDirectorioEmpresarial.getChildren().add(encabezado);
//    
//        if(directorio == null){
//            //Es la primera vez que se carga el directorio empresarial
//            //Recoleccion de los datos desde la base de datos
//            DirectorioEmpresarialSql dir = new DirectorioEmpresarialSql();
//            directorio = dir.getDirectorio(usuario);
//            
//        }
//        List<ContactoDTO> contactos = directorio.getContactos();
//        System.out.println("Numero de contactos: " + contactos.size());
//        Iterator itr = contactos.iterator();
//        TextField campo = null;
//        ContactoEmpresarialDTO contacto = null;
//        while(itr.hasNext()){
//            contacto =(ContactoEmpresarialDTO) itr.next();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("ContenedorContactoEmpresarial.fxml"));
//            GridPane contenedorContacto = loader.load();
//            ContenedorContactoController controller = loader.getController();
//            controller.setContactoEmpresarial(contacto);
//            Set<Node> componentes = contenedorContacto.lookupAll("TextField");
//            Iterator itr2 = componentes.iterator();
//            //Rellenar campos
//            TextArea direccion =(TextArea) contenedorContacto.lookup("TextArea");
//            direccion.setText(contacto.getDireccionPostal());
//            
//            //Nombre
//            campo = (TextField) itr2.next(); 
//            campo.setText(contacto.getNombre());
//            
//            //Telefono
//            campo = (TextField) itr2.next(); 
//            campo.setText(contacto.getTelefono());
//            
//            //Email
//            campo = (TextField) itr2.next(); 
//            campo.setText(contacto.getEmail());
//            
//            //Giro
//            campo = (TextField) itr2.next(); 
//            campo.setText(contacto.getGiro());
//           
//            //Agregar contacto
//            contenedorDirectorioEmpresarial.getChildren().add(contenedorContacto);
//        }
//      }catch(SQLException e){
//          //No fue posible recolectar el directorio
//          System.out.println("Error. No fue posible recolectar el directorio");
//        e.printStackTrace();
//    }catch(Exception e){
//        System.out.println("Error en el procesamiento del directorio");
//        e.printStackTrace();
//    }
//    }
//    
//    
//    @FXML
//    public void crerModificarCita(ActionEvent e){
//        //Evento que se genera cuando se va a modifcar uno o mas citas
//    }
//    
//    @FXML
//    public void crearModificarCita(MouseEvent e){
//        //Evento que se genera cuando solo se va a modifcar una cita
//        try {
//            List<Integer> citasModificas = new ArrayList<>();
//            List<Integer> citasNuevas = new ArrayList<>();
//            Set<Node> contenedoresCita = contenedorCitasDia.lookupAll(".contenedorCita");
//              Object []contenedor = contenedoresCita.toArray();
//              CitaDAO baseD  = new CitaSql();
//            if(e.getSource() instanceof Button){
//                //Se selecciono mas de una cita
//                System.out.println("Entro");
//               List<CheckBox> checks = new ArrayList<>();
//               CheckBox aux;
//               Set<Node> lista =  contenedorCitasDia.lookupAll("CheckBox");
//               Iterator itr = lista.iterator();
//               
//               //Separa los los checks seleccionados
//               while(itr.hasNext()){
//                   aux = (CheckBox) itr.next();
//                   if(aux.isSelected())
//                       checks.add(aux);
//                           
//               }
//               
//               //No se selecciono ningun check
//               if(checks.isEmpty())
//                   throw new Exception();
//               System.out.println("Paso");
//               
//               
//               //Determinar cuales citas son modifiaciones y cuales son nuevas
//               
//               Iterator itr2 = checks.iterator();
//               
//               while(itr2.hasNext()){
//                   int id_check = Integer.parseInt(((CheckBox) itr2.next()).getId().substring(5));
//                   if(((HBox)contenedor[id_check]).getId().equals("-1"))
//                       citasNuevas.add(id_check);
//                   else
//                       citasModificas.add(id_check);      
//               }
//               
//            }else{
//                //Cambios en solo una cita
//                int id_check = Integer.parseInt(((Node)e.getSource()).getId().substring(5));
//                int hr_Inicial = id_check+7;
//                if(((HBox)contenedor[id_check]).getId().equals("-1"))
//                  ;//  baseD.insert(cita);
//                else
//                    ;//baseD.update(cita);   
//            }
//            Stage ventana = new Stage();
//                VBox root;  
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaCita.fxml"));
//                root = loader.load();
//                VentanaCitaController controller = loader.getController();
//                controller.setCitasNuevas(citasNuevas);
//                controller.setCitasModificas(citasModificas);
//                Scene scene = new Scene(root);
//                ventana.setScene(scene);
//                ventana.setResizable(false);
//                ventana.setTitle("Información detallada de cita.");
//                ventana.initModality(Modality.WINDOW_MODAL);
//                ventana.initOwner(((Node)e.getSource()).getScene().getWindow());
//                ventana.show();
//            }catch (IOException ex) {
//                Logger.getLogger(VentanaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
//            }catch(Exception exx){
//                mostrarVentanaError("Error. Es necesario seleccionar al menos una cita para realizar esta acción");
//            }finally{
//            //Limpiar checks
//            limpiarTableroCitasDia();
//        }   
//            
//    }
//    
//    @FXML
//    public void limpiarCita(ActionEvent e){
//        System.out.println("Limpiando citas");
//            List<CheckBox> checks = new ArrayList<>();
//               CheckBox aux;
//               Set<Node> lista =  contenedorCitasDia.lookupAll("CheckBox");
//               Iterator itr = lista.iterator();
//               
//               //Separa los los checks seleccionados
//               while(itr.hasNext()){
//                   aux = (CheckBox) itr.next();
//                   if(aux.isSelected())
//                       checks.add(aux);                          
//               }
//               if(checks.isEmpty()){
//                  mostrarVentanaError("Error. Es necesario seleccionar al menos una cita para realizar esta acción");
//               }else{
//                   //Determinar cuales citas ya existne y borrarlas
//                Set<Node> contenedoresCita = contenedorCitasDia.lookupAll(".contenedorCita");
//                Object []contenedor = contenedoresCita.toArray();
//                Iterator itr2 = checks.iterator();
//                List<Integer> citasExistentes = new ArrayList<>();
//             
//                while(itr2.hasNext()){
//                    int id_check = Integer.parseInt(((CheckBox) itr2.next()).getId().substring(5));
//                    System.out.println(id_check);
//                    System.out.println(((HBox)contenedor[id_check-1]).getId());
//                    if(!((HBox)contenedor[id_check-1]).getId().equals("Cita-1"))
//                        citasExistentes.add(id_check);     
//                }
//                
//                Iterator itrCitas = citasExistentes.iterator();
//                CitaSql baseD = new CitaSql();
//                
//                try{
//                    while(itrCitas.hasNext()){
//                        baseD.delete(new CitaDTO((Integer)itr.next()));
//                    }
//                }catch(SQLException ex){
//                    mostrarVentanaError("Error. No fue posible eliminar las citas en la base de datos");
//                }
//               
//               
//               //Limpiar checks
//               limpiarTableroCitasDia();
//               }
//               
//    }
//    @FXML public void agregarContactoEmpresarial(ActionEvent e){
//        // Agregar un contacto Personal predefinido a directorio y hacer la consulta  a la base de datos
//        try{
//            ContactoEmpresarialSql dirB = new ContactoEmpresarialSql();
//            ContactoEmpresarialDTO contacto = new ContactoEmpresarialDTO();
//            contacto.setIdTrabajador(usuario.getIdTrabajador());
//            dirB.insert(contacto);
//            actualizarDirectorioEmpresarial();
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        
//    }
//    
//    @FXML public void agregarContactoPersonal(ActionEvent e){
//        // Agregar un contacto Personal predefinido a directorio y hacer la consulta  a la base de datos
//        try{
//            DirectorioPersonalDTO dir = usuario.getDirPersonal();
//            ContactoPersonalSql dirB = new ContactoPersonalSql();
//            ContactoPersonalDTO contacto =  new ContactoPersonalDTO();
//            contacto.setIdTrabajador(usuario.getIdTrabajador());
//            if(dirB.insert(contacto) != 1)
//                System.out.println("No fue posible guardar el nuevo contacto");
//            actualizarDirectorioPersonal();
//        }catch(Exception er){
//            er.printStackTrace();
//        }
//        
//    }
//    
 
 
    
//    public void eliminarContactoEmpresarial(ContactoEmpresarialDTO contacto){      
//            Directorio dir = usuario.getDirEmpresarial();
//            if(!dir.eliminarContacto(contacto))
//                mostrarVentanaError("Error. No fue posible eliminar el contacto de la base de datos");
//            else
//                actualizarDirectorioEmpresarial();
//
//    }
//
//    public void eliminarContactoPersonal(ContactoPersonalDTO contacto){
//        Directorio dir = usuario.getDirPersonal();
//        if(dir.eliminarContacto(contacto))
//            mostrarVentanaError("Error. No fue posible eliminar el contacto de la base de datos");
//        else
//            actualizarDirectorioPersonal();
//    }
    
    public void mostrarVentanaError(String error){
         try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaError.fxml"));
            AnchorPane contenedor = loader.load();
            VentanaErrorController controller = loader.getController();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(contenedorPrincipal.getScene().getWindow());
            controller.setMensaje(error);
            stage.setScene(new Scene(contenedor));
            stage.setResizable(false);
            stage.setTitle("Mensaje de error.");
            stage.show();
        }catch(Exception exx){
            exx.printStackTrace();
        }
    }
    
}
