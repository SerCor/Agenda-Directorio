package DB;

import java.sql.SQLException;
import DTO.TrabajadorDTO;

public interface TrabajadorDAO {

    public boolean insert(TrabajadorDTO trabajador) throws SQLException;

    public TrabajadorDTO select(String user,String pass) throws SQLException;
}
