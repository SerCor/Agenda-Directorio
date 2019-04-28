package DTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DirectorioEmpresarialDTO implements Directorio{
        
     List<ContactoEmpresarialDTO> contactos;
    
    public DirectorioEmpresarialDTO(){
        contactos = new ArrayList<>();
    }
    
    @Override
    public boolean eliminarContacto(ContactoDTO c){
        //TODO: Modificacion en la base de datos
        /*Hace polimorfismo con el metodo equals . No hay problema */
        return contactos.remove(c);
    }
    
    @Override  
    public boolean agregarContacto(ContactoDTO c)throws IllegalArgumentException{
        //TODO: Modificacion en la base de datos
        if(!(c instanceof ContactoEmpresarialDTO))
            throw new IllegalArgumentException();
         return contactos.add((ContactoEmpresarialDTO)c);
    }
    
    
    
    @Override
    public List<ContactoDTO> getContactos(){
        List<ContactoDTO> lista = new ArrayList<>();
        Iterator itr1 = contactos.iterator();
        while(itr1.hasNext()){
            lista.add((ContactoDTO)itr1.next());
        }
        return lista;
    }
    
    @Override
    public boolean modificarContacto(int id,ContactoDTO c)throws IllegalArgumentException{
        //TODO: Modificacion en la base de datos
        if(!(c instanceof ContactoEmpresarialDTO))
            throw new IllegalArgumentException();
        
        ContactoEmpresarialDTO contacto = (ContactoEmpresarialDTO)getContacto(id);
        if( contacto == null)
             return false;
        contacto.swap(c);
        return true;
    }
    
/*Metodo auxiliar para modificar contacto */
    private ContactoDTO getContacto(int id){
        Iterator itr = contactos.iterator();
        ContactoEmpresarialDTO aux = null;
        
        while(itr.hasNext()){
            aux = (ContactoEmpresarialDTO)itr.next();
            if(aux.getId() == id)
                return aux;
        }
        
        return null;
    }
}
