
package DAO;

import DTO.CitaDTO;
import DTO.ContactoDTO;
import DTO.TrabajadorDTO;
import java.sql.SQLException;

public interface CitaDAO {
    public int insert(CitaDTO cita)throws SQLException;
    public int update(String NuevoCitado,String id_trabajador,String citado) throws SQLException;
    public int delete(CitaDTO cita)throws SQLException;
    public int delete(String id,String id_trabajador)throws SQLException;
    public CitaDTO select(CitaDTO cita) throws SQLException ;
}
