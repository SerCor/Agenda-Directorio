/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.List;

public interface Directorio {
    public boolean eliminarContacto(ContactoDTO c);
    public boolean agregarContacto(ContactoDTO c)throws IllegalArgumentException;
    public boolean modificarContacto(int id,ContactoDTO c)throws IllegalArgumentException;
    public List<ContactoDTO> getContactos();
}
