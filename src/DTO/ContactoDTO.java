/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author SerCo
 */
public class ContactoDTO {
    
    protected int id;
    protected String id_trabajador;
    protected String nombre;
    protected String telefono;
    protected String direccionPostal;
    protected String email;
    
    public ContactoDTO(){
        id = -1;
        id_trabajador = "-1";
        nombre = "Desconocido";
        telefono = "Desconocido";
        direccionPostal = "Desconocida";
        email = "Desconocido";
    }
    
    public ContactoDTO(int id,String id_trabajador,String nombre, String telefono, String direccionPostal,String email) {
        this.id_trabajador = id_trabajador;
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccionPostal = direccionPostal;
        this.email = email;
    }
    
     public ContactoDTO(String id_trabajador,String nombre, String telefono, String direccionPostal,String email) {
        this.id_trabajador = id_trabajador;
        this.id = -1;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccionPostal = direccionPostal;
        this.email = email;
    }
    
    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccionPostal() {
        return direccionPostal;
    }
    
    public int getId(){
        return id;
    }
    
    public String getId_Trabajador(){
        return id_trabajador;
    }

    public void setDireccionPostal(String direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void  setIdTrabajador(String id_trabajador){
        this.id_trabajador = id_trabajador;
    }
    
    public void swap(ContactoDTO c){
        this.id = c.getId();
        this.nombre = c.getNombre();
        this.telefono = c.getTelefono();
        this.direccionPostal = c.getDireccionPostal();
        this.email = c.getEmail();
    }
    
    @Override
    public boolean equals(Object o){
        ContactoDTO contacto = (ContactoDTO) o;
        return (id == contacto.getId()) && (id_trabajador == contacto.getId_Trabajador()) && (email.equals(contacto.getEmail()))  && telefono.equals(contacto.getTelefono()) && direccionPostal.equals(contacto.getDireccionPostal());
    }
    
    
}
