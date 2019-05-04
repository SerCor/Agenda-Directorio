
package DAO;

import DTO.Directorio;
import DTO.TrabajadorDTO;
import java.sql.SQLException;


public interface DirectorioDAO {
    
    public Directorio getDirectorio(TrabajadorDTO usuario)throws SQLException;
    
}
