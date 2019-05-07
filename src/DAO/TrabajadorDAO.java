package DAO;

import java.sql.SQLException;
import DTO.TrabajadorDTO;

public interface TrabajadorDAO {

    public boolean insert(TrabajadorDTO trabajador) throws SQLException;

    public TrabajadorDTO select(String user,String pass) throws SQLException;
    
    public boolean availableId(String id)throws SQLException;
    
    public boolean availableUsuario(String user)throws SQLException;
}
