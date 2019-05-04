
package DAO;

import DTO.ContactoDTO;
import DTO.ContactoEmpresarialDTO;
import DTO.Directorio;
import DTO.DirectorioEmpresarialDTO;
import DTO.TrabajadorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DirectorioEmpresarialSql implements DirectorioDAO{
    private Connection userConn;
    private final String SQL_SELECT = "SELECT id_contacto,Usuario_id_trabajador,nombre_empresa,telefono,direccionPostal,email,giro,nombre_representante FROM  Contacto_empresarial WHERE Usuario_id_trabajador = ? ORDER BY nombre_empresa";
    
    
    @Override
    public Directorio getDirectorio(TrabajadorDTO usuario) throws SQLException {
        /*Obtener un directorio que contenga todos los contactos relacionados con un trabajador en especifico*/

        
        Connection conn = null; 
        PreparedStatement stmt = null;
        ResultSet rs = null; 
        ContactoEmpresarialDTO contacto = null; 
        Directorio directorio = new DirectorioEmpresarialDTO();
        try { 
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection(); 
            stmt = conn.prepareStatement(SQL_SELECT);
            stmt.setString(1,usuario.getIdTrabajador());
            rs = stmt.executeQuery(); 
            while (rs.next()) {
                int index = 1;
                int id_contacto =  rs.getInt(index++);
                String id_trabajador = rs.getString(index++);
                String nombre_empresa =rs.getString(index++);
                String telefono = rs.getString(index++);
                String direccion  = rs.getString(index++);
                String email = rs.getString(index++);
                String giro = rs.getString(index++); 
                String nombre_representante = rs.getString(index++);

                //(int id,int id_trabajador,String nombre,String telefono,String direccionPostal,String email,String giro,String representante)
                contacto = new ContactoEmpresarialDTO(id_contacto,id_trabajador,nombre_empresa,telefono,direccion,email,giro,nombre_representante);
                directorio.agregarContacto(contacto);
            } 
        }
        finally { 
            Conexion.close(rs); 
            Conexion.close(stmt); 
            if (this.userConn == null) { 
                Conexion.close(conn); 
            } 
        } 
        return directorio;

    }

   
}
