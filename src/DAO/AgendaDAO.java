
package DAO;

import DTO.AgendaDTO;
import DTO.ContactoDTO;
import DTO.TrabajadorDTO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;


public interface AgendaDAO {
    
    public AgendaDTO select(TrabajadorDTO trabajador,LocalDate fecha) throws SQLException;
}
