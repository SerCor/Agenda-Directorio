/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.ContactoDTO;
import DTO.ContactoEmpresarialDTO;
import DTO.ContactoPersonalDTO;
import DTO.Directorio;
import DTO.DirectorioEmpresarialDTO;
import DTO.DirectorioPersonalDTO;
import DTO.TrabajadorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/*id_contacto,id_trabajador,nombre,direccion,email,telefono_particular,telefono_movil,parentesco*/
public class DirectorioPersonalSql implements DirectorioDAO{

    private Connection userConn;
    private final String SQL_SELECT = "SELECT id_contacto,Usuario_id_trabajador,nombre,telefono,teldefonocelular,direccionPostal,email,parentesco FROM  Contacto_personal WHERE Usuario_id_trabajador = ? ORDER BY nombre";
    
    
    @Override
    public Directorio getDirectorio(TrabajadorDTO usuario) throws SQLException {
        /*Obtener un directorio que contenga todos los contactos relacionados con un trabajador en especifico*/
        
        Connection conn = null; 
        PreparedStatement stmt = null;
        ResultSet rs = null; 
        ContactoPersonalDTO contacto = null; 
        Directorio directorio = new DirectorioPersonalDTO();
        try { 
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection(); 
            stmt = conn.prepareStatement(SQL_SELECT);
            stmt.setString(1,usuario.getIdTrabajador());
            rs = stmt.executeQuery(); 
            while (rs.next()) {
                int index = 1;
                int id_contacto =  rs.getInt(index++);
                String id_trabajador = rs.getString(index++);
                String nombre = rs.getString(index++);
                String telefonoFijo = rs.getString(index++);
                String telefonoCelular = rs.getString(index++);
                String direccionPostal = rs.getString(index++);
                String email = rs.getString(index++);
                String parentesco = rs.getString(index++);


     //int id,int id_trabajador,String nombre,String telefono_fijo,String telefono_celular,String direccion_postal,String email,String parentesco
            contacto = new ContactoPersonalDTO(id_contacto,id_trabajador,nombre,telefonoFijo,telefonoCelular,direccionPostal,email,parentesco);
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
