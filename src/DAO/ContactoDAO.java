
package DAO;

import DTO.ContactoDTO;
import java.sql.SQLException;


public interface ContactoDAO {
    public int insert(ContactoDTO contacto) throws SQLException, IllegalArgumentException;
    public boolean select(String id_usuario,String nombre_Contacto) throws SQLException,IllegalArgumentException;
    public int delete(ContactoDTO contacto) throws SQLException, IllegalArgumentException;
    public int update(ContactoDTO contacto) throws SQLException, IllegalArgumentException;
}
