
package DTO;


public class ContactoEmpresarialDTO extends ContactoDTO{
    
    private String giro;
    private String representante;
    
    public ContactoEmpresarialDTO(){
        giro = "No especificado";
        representante = "Desconocido";
    }
    
    public ContactoEmpresarialDTO(int id,String id_trabajador,String nombre,String telefono,String direccionPostal,String email,String giro,String representante){
          super(id,id_trabajador,nombre,telefono,direccionPostal,email);
          this.giro = giro;
          this.representante = representante;
    }
    
    public ContactoEmpresarialDTO(String id_trabajador,String nombre,String telefono,String direccionPostal,String email,String giro,String representante){
          super(id_trabajador,nombre,telefono,direccionPostal,email);
          this.representante = representante; 
          this.giro = giro;
    }
    
    
    public String getGiro() {
        return giro;
    }
    
    public String getRepresentante(){
        return representante;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }
    
    public void setRepresentante(String representante){
        this.representante = representante;
    }
    
    @Override
    public void swap(ContactoDTO c)throws IllegalArgumentException{
        if(!(c instanceof ContactoEmpresarialDTO))
          throw new IllegalArgumentException("Los contactos deben ser del mismo tipo.");
        super.swap(c);
        giro = ((ContactoEmpresarialDTO)c).getGiro();
    }
    
    @Override
    public boolean equals(Object o){
        ContactoEmpresarialDTO contacto2 = (ContactoEmpresarialDTO) o;
        return super.equals(o) && giro.equals(contacto2.getGiro()) && representante.equals(contacto2.getRepresentante());
    }
    
    
}
