
package DTO;

import java.time.LocalDate;
import java.util.Date;

public class CitaDTO {
    
    private int id;
    private String id_trabajador;
    private String contacto;
    private LocalDate fecha;
    private String asunto;
    private int hrInicio;
    private int hrFin;
    private String lugar;
    
    public CitaDTO(){
        id = -1;
    }
    
    public CitaDTO(CitaDTO cita){
        id = cita.getId();
        id_trabajador = cita.getIdTrabajador();
        contacto = cita.getContacto();
        fecha = cita.getFecha();
        asunto = cita.getAsunto();
        lugar = cita.getLugar();
        hrInicio = cita.getHrInicio();
        hrFin  = cita.getHrFin();
    }

//id_cita,id_trabajador,nombre_citado,fech,hr_inicio,hr_final,lugar,asunto
    
    public CitaDTO(int id){
        this.id = id;
    }

    public CitaDTO(int id,String id_trabajador,String nombre,LocalDate f,int hrI,int hrF,String lug, String a){
        this.id_trabajador = id_trabajador;
        this.id = id;
        contacto = nombre;
        fecha = f;
        asunto = a;
        hrInicio = hrI;
        hrFin = hrF;
        lugar = lug;
    }
    
    public CitaDTO(String id_trabajador,String nombre,LocalDate f,int hrI,int hrF,String lug, String a){
        this.id_trabajador = id_trabajador;
        contacto = nombre;
        fecha = f;
        asunto = a;
        hrInicio = hrI;
        hrFin = hrF;
        lugar = lug;
    }
    
     public String getContacto() {
        return contacto;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    
    public int getId(){
        return id;
    }

    public String getAsunto() {
        return asunto;
    }

    public int getHrInicio() {
        return hrInicio;
    }

    public int getHrFin() {
        return hrFin;
    }

    public String getLugar() {
        return lugar;
    }
    
    public String getIdTrabajador(){
        return id_trabajador;
    }
    
    public void setIdTrabajador(String id){
        id_trabajador = id;
    }
    
       public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public void setHrInicio(int hrInicio) {
        this.hrInicio = hrInicio;
    }

    public void setHrFin(int hrFin) {
        this.hrFin = hrFin;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    
    public void swap(CitaDTO c){
        id = c.getId();
        contacto = c.getContacto();
        fecha = c.getFecha();
        asunto = c.getAsunto();
        hrInicio = c.getHrInicio();
        hrFin = c.getHrFin();
        lugar = c.getLugar();
    }
    
    public String toString(){
        return "id: " + id+ " contacto: " + contacto + " fecha: " + fecha+ " asunto: " +asunto + " hrInicio: " + hrInicio + " grFin: " + hrFin + "  lugar:" + lugar + "\n";
    }
    
    
}
