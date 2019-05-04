
package DTO;

import java.util.List;

public interface Directorio {
    public boolean eliminarContacto(ContactoDTO c);
    public boolean agregarContacto(ContactoDTO c)throws IllegalArgumentException;
    public boolean modificarContacto(int id,ContactoDTO c)throws IllegalArgumentException;
    public List<ContactoDTO> getContactos();
}
