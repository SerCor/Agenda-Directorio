
package DTO;

import java.sql.Date;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class AgendaDTO {
    private List<CitaDTO> citas;
    
    public AgendaDTO(){
        citas = new ArrayList<>();
    }
    
    public AgendaDTO(List<CitaDTO> citas){
        this.citas = citas;
        
    }
    
    public boolean eliminarCita(CitaDTO c){
        //TODO: Modificacion en la base de datos
        return citas.remove(c);
    }
    public boolean agregarCita(CitaDTO c){
        //TODO: Modificacion en la base de datos
        return citas.add(c);
    }
    public boolean modificarContacto(int id,CitaDTO c){
        //TODO: Modificacion en la base de datos
       CitaDTO cita = buscarCita(id);
       if(cita == null)
           return false;
       cita.swap(c);
       return true;
    }
    
    
    public List<CitaDTO> getCitas(){
        /*Copia de las citas */
        List<CitaDTO> lista = new ArrayList<>();
        Iterator itr = citas.iterator();
        CitaDTO cita = null;
        while(itr.hasNext()){
            cita = new CitaDTO((CitaDTO)itr.next());
            lista.add(cita);
        }
        return lista;
    }
    
    private CitaDTO buscarCita(int id){
         Iterator itr = citas.iterator();
        CitaDTO cita = null;
        while(itr.hasNext()){
            cita = (CitaDTO) itr.next();
            if(cita.getId() == id)
                return cita;
        }
        
        return null;
    }
    
    public List<CitaDTO> getCitas(Date fecha){
        List<CitaDTO> lista = new ArrayList<>();
        Iterator itr = citas.iterator();
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.setTime(fecha);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DATE);
        int hora = cal.get(Calendar.HOUR);
        CitaDTO cita = null;
        while(itr.hasNext()){
            cita = new CitaDTO((CitaDTO)itr.next());
            
            if(cal2.get(Calendar.DATE) == dia && cal2.get(Calendar.MONTH) == mes  && cal2.get(Calendar.HOUR) == hora)
                lista.add(cita);
        }
        return lista;
    }
    
    
}
