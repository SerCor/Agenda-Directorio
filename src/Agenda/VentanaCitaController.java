package Agenda;
import DAO.CitaDAO;
import DAO.CitaSql;
import DAO.ContactoDAO;
import DAO.ContactoEmpresarialSql;
import DAO.ContactoPersonalSql;
import DAO.DirectorioDAO;
import DAO.DirectorioEmpresarialSql;
import DAO.DirectorioPersonalSql;
import DTO.CitaDTO;
import DTO.ContactoDTO;
import DTO.ContactoEmpresarialDTO;
import DTO.DirectorioEmpresarialDTO;
import DTO.DirectorioPersonalDTO;
import DTO.TrabajadorDTO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class VentanaCitaController implements Initializable {
    private List<CitaDTO> citasModificadas; //Contiene los id de las citas existentes
    private List<CitaDTO> citasNuevas;      //Contiene las hrs iniciales de las citas nuevas
    @FXML ComboBox campoContacto;
    @FXML TextField campoLugar;
    @FXML TextField campoAsunto;   
    private VentanaPrincipalController controllerPrincipal;
    @FXML Label etiquetaError;
    @FXML Button btnGuardar;
    private TrabajadorDTO usuario;
    private ContenedorAgendaController controllerAgenda;
    private CitaDTO citaPrincipal ;
    
     public void setControllerAgenda(ContenedorAgendaController controllerAgenda){
        this.controllerAgenda = controllerAgenda;
    }
    
    public void setTrabajadorDTO(TrabajadorDTO usuario){
        this.usuario = usuario;
    }
    
    public void setControllerPrincipal(VentanaPrincipalController controller){
        controllerPrincipal = controller;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        citasModificadas = new ArrayList<>();
        citasNuevas = new ArrayList<>();
    }    
    
    
    
    public void setCitasNuevas(List<CitaDTO> citas){
         List<String> nombre = new ArrayList<>();
        try{
            DirectorioDAO baseD = new DirectorioEmpresarialSql();
            DirectorioDAO baseD2 = new DirectorioPersonalSql();
            DirectorioEmpresarialDTO directorioEmpresarial =(DirectorioEmpresarialDTO) baseD.getDirectorio(usuario);
            DirectorioPersonalDTO directorioPersonal =(DirectorioPersonalDTO) baseD2.getDirectorio(usuario);
            List<ContactoDTO> contactosE = directorioEmpresarial.getContactos();
            List<ContactoDTO> contactosP = directorioPersonal.getContactos();
            
            for(int i = 0; i < contactosE.size();i++){
                nombre.add(contactosE.get(i).getNombre());
            }

            for(int i = 0; i < contactosP.size();i++){
                nombre.add(contactosP.get(i).getNombre());
            }
            
            campoContacto.getItems().addAll(nombre);
            campoContacto.getSelectionModel().select(0);
            citasNuevas = citas;  
            
        }catch(SQLException e){
            
        }
            
    }

    public void setCitasModificas(List<CitaDTO> citas){
        
        campoContacto.getItems().clear();
        List<String> nombre = new ArrayList<>();
        try{
            DirectorioDAO baseD = new DirectorioEmpresarialSql();
            DirectorioDAO baseD2 = new DirectorioPersonalSql();
            
            //Recoleccion de nombres de sus contactos para poder elegir uno.
            DirectorioEmpresarialDTO directorioEmpresarial =(DirectorioEmpresarialDTO) baseD.getDirectorio(usuario);
            DirectorioPersonalDTO directorioPersonal =(DirectorioPersonalDTO) baseD2.getDirectorio(usuario);
            List<ContactoDTO> contactosE = directorioEmpresarial.getContactos();
            List<ContactoDTO> contactosP = directorioPersonal.getContactos();
            
            for(int i = 0; i < contactosE.size();i++){
                nombre.add(contactosE.get(i).getNombre());
            }

            for(int i = 0; i < contactosP.size();i++){
                nombre.add(contactosP.get(i).getNombre());
            }
            
            campoContacto.getItems().addAll(nombre);
            campoContacto.getSelectionModel().select(0);
            citasModificadas = citas;
        }catch(SQLException e){
            
        }
        
        
        
   
        CitaDTO citaPrincipal = (citasModificadas.isEmpty())?citasNuevas.get(0):citasModificadas.get(0);//La primera cita va ser la cita a partir de la cual se van a hacer las modificaciones
        
        campoContacto.getSelectionModel().select(citaPrincipal.getContacto());
        campoLugar.setText(citaPrincipal.getLugar());
        campoAsunto.setText(citaPrincipal.getAsunto());
            
    }
    
    @FXML 
    public void guardarEnter(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER){
            if(comprobacionIntegridadCampos())
                 guardarCambios();
        }
            
    }
    
    
    @FXML
    public void guardarBoton(ActionEvent e){
        /* Realiza insert para las nuevas citas y update para las consultas modificadas*/
        if(comprobacionIntegridadCampos())
            guardarCambios();
    }
    
    public void guardarCambios(){
        System.out.println("Guardando todo");
        CitaDAO baseD = new CitaSql();
        CitaDTO citaAux = null;
        
        try{
                //Citas modificadas
            String citado = (String)campoContacto.getSelectionModel().getSelectedItem();
            String lugar = campoLugar.getText();
            String asunto = campoAsunto.getText();
            
            
            Iterator itrModificadas = citasModificadas.iterator();
            while(itrModificadas.hasNext()){
                System.out.println("Datos actualizados: " + citado +" " + lugar + " " + asunto);
                citaAux = (CitaDTO) itrModificadas.next();
                citaAux.setContacto(citado);
                citaAux.setLugar(lugar);
                citaAux.setAsunto(asunto);
                baseD.update(citaAux);
            }

            //Citas nuevas
            Iterator itrNuevas = citasNuevas.iterator();
            while(itrNuevas.hasNext()){
                System.out.println("Datos insertados: " + citado +" " + lugar + " " + asunto);
                citaAux = (CitaDTO) itrNuevas.next();
                citaAux.setContacto(citado);
                citaAux.setLugar(lugar);
                citaAux.setAsunto(asunto);
                baseD.insert(citaAux);
            }
            
            //Cerar ventana
          ((Stage)btnGuardar.getScene().getWindow()).close();
          
          //Actualizar las citas mostradas en la agenda
          controllerAgenda.actualizarTableroCitasDia();
          
        }catch(SQLException ex){
            etiquetaError.setText("Error. No fue posible guardar los datos en la base de datos.");
        }
    }
    
    private boolean comprobacionIntegridadCampos(){
        //Comprueba que los datos ingresados puedan ser almacenados en la base de datos
        //Maximo de 15 caracteres en campos lugar y asunto
        Boolean banderaRetorno = false;
        try{
            String lugar = campoLugar.getText();
            String asunto = campoAsunto.getText();
            if(campoContacto.getItems().isEmpty())
                throw new Exception("Error. No se tiene contactos para agendar.");
            if(lugar.equals("")|| asunto.equals("") )
                throw new Exception("Error. No se permiten campos vacíos.");
            if(lugar.length() >= 15)    //Limite de caracteres por campo
                campoLugar.setText(lugar.substring(0, 15));
            if(asunto.length() >= 15)
                campoAsunto.setText(asunto.substring(0,15));
            banderaRetorno = true;
        }catch(Exception e){
            etiquetaError.setText(e.getMessage());
        }finally{
            return banderaRetorno;
        }
        
    }
    
}
