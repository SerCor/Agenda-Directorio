
package DTO;
public class ContactoPersonalDTO extends ContactoDTO{
    
    private String parentesco;
    private String telefonoCelular;

    
    public ContactoPersonalDTO(){
        super();
        parentesco = "Sin especificar";
        telefonoCelular = "Desconocido";
        
    }

    public ContactoPersonalDTO(int id,String id_trabajador,String nombre,String telefonoFijo,String telefonoCelular,String direccionPostal,String email,String parentesco){
        super(id,id_trabajador,nombre,telefonoFijo,direccionPostal,email);
        this.parentesco = parentesco;
        this.telefonoCelular = telefonoCelular;
    }
    
    public ContactoPersonalDTO(String id_trabajador,String nombre,String telefonoFijo,String telefonoCelular,String direccionPostal,String email,String parentesco){
        super(id_trabajador,nombre,telefonoFijo,direccionPostal,email);
        this.parentesco = parentesco;
        this.telefonoCelular = telefonoCelular;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
    
    public String getTelefonoCelular(){
        return telefonoCelular;
    }
    
    public void setTelefonoCelular(String telefonoCelular){
        this.telefonoCelular = telefonoCelular;
    }
    
    
    @Override
    public void swap(ContactoDTO c)throws IllegalArgumentException{
        if(!(c instanceof ContactoPersonalDTO))
          throw new IllegalArgumentException("Los contactos deben ser del mismo tipo.");
        super.swap(c);
        parentesco = ((ContactoPersonalDTO)c).getParentesco();
    }
    
    @Override
    public boolean equals(Object o){
        ContactoPersonalDTO contacto = (ContactoPersonalDTO) o;
        return super.equals(o) && parentesco.equals(contacto.getParentesco()) && telefonoCelular.equals(contacto.getTelefonoCelular());
    }
 
    
}
