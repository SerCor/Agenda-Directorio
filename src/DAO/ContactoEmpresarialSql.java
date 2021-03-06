
package DAO;

import DTO.CitaDTO;
import DTO.ContactoDTO;
import DTO.ContactoEmpresarialDTO;
import DTO.TrabajadorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ContactoEmpresarialSql implements ContactoDAO {
    private Connection userConn;
    //id_contacto,id_trabajador,nombre_empresa,direccion,email,giro,telefono,nombre_representante
    private final String SQL_INSERT = "INSERT INTO Contacto_empresarial(Usuario_id_trabajador,nombre_empresa,direccionPostal,email,giro,telefono,nombre_representante) VALUES(?,?,?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE Contacto_empresarial  SET nombre_empresa=?,direccionPostal=?,email=?,giro=?,telefono=?,nombre_representante=? WHERE id_contacto=?";
    private final String SQL_DELETE = "DELETE FROM Contacto_empresarial WHERE id_contacto= ?";
    private final String SQL_SELECT = "SELECT email FROM Contacto_empresarial WHERE Usuario_id_trabajador=? AND nombre_empresa=?";
    private final String SQL_SELECT_TRABAJADOR = "SELECT nombre_empresa FROM Contacto_empresarial WHERE Usuario_id_trabajador = ?";

    public ContactoEmpresarialSql() {

    }
    
    public ContactoEmpresarialSql(Connection conn) {
        this.userConn = conn;
    }
    
    public int insert(ContactoDTO contact) throws SQLException,IllegalArgumentException{
        //id_contacto,id_trabajador,nombre_empresa,telefono,direccion,email,giro,nombre_representante
        //private final String SQL_INSERT = "INSERT INTO Contacto_empresarial(Usuario_id_trabajador,nombre_empresa,direccionPostal,email,giro,telefono,nombre_representante) VALUES(?,?,?,?,?,?,?)";
        //Inserta nuevo contacto
        if(!(contact instanceof ContactoEmpresarialDTO))
            throw new IllegalArgumentException();
        ContactoEmpresarialDTO contacto = (ContactoEmpresarialDTO) contact;
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;
            stmt.setString(index++, contacto.getId_Trabajador());
            stmt.setString(index++, contacto.getNombre());
            stmt.setString(index++, contacto.getDireccionPostal());
            stmt.setString(index++, contacto.getEmail());
            stmt.setString(index++, contacto.getGiro());
            stmt.setString(index++, contacto.getTelefono());

            stmt.setString(index++,contacto.getRepresentante());
            rows = stmt.executeUpdate();
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return rows;
    }

    public int update(ContactoDTO contact)
            throws SQLException,IllegalArgumentException {
        //id_contacto,id_trabajador,nombre_empresa,telefono,direccion,email,giro,nombre_representante
        //    private final String SQL_UPDATE = "UPDATE Contacto_empresarial  SET nombre_empresa=?,direccionPostal=?,email=?,giro=?,telefono=?,nombre_representante=? WHERE id_contacto=?";
        //Actualiza informacion de un contacto
        
        if(!(contact instanceof ContactoEmpresarialDTO))
            throw new IllegalArgumentException();
        ContactoEmpresarialDTO contacto = (ContactoEmpresarialDTO) contact;
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1;
            stmt.setString(index++, contacto.getNombre());
            stmt.setString(index++, contacto.getDireccionPostal());
            stmt.setString(index++, contacto.getEmail());
            stmt.setString(index++, contacto.getGiro());
            stmt.setString(index++, contacto.getTelefono());
            stmt.setString(index++,contacto.getRepresentante());
            stmt.setInt(index++, contacto.getId());


            rows = stmt.executeUpdate();
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }

    /**
     * Recibimos un objeto PersonaDTO no necesariamente debe venir lleno, sino
     * solo nos importa el atributo id_persona
     */
    public int delete(ContactoDTO contact) throws SQLException, IllegalArgumentException {
        //id_contacto,id_trabajador,nombre_empresa,telefono,direccion,email,giro,nombre_representante
        //    private final String SQL_DELETE = "DELETE FROM Contacto_empresarial WHERE id_contacto= ?";
        //Elimina contacto
        
        if(!(contact instanceof ContactoEmpresarialDTO))
            throw new IllegalArgumentException();
        ContactoEmpresarialDTO contacto = (ContactoEmpresarialDTO) contact;
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, contacto.getId());
            rows = stmt.executeUpdate();
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }
    
    public List<String> getNombresContactos(String id) throws SQLException{
        /*Retorna los nombres de los contactos relacionados con el id de un trabajador*/
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> lista = new ArrayList<>();
        try {
                conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
                stmt = conn.prepareStatement(SQL_SELECT_TRABAJADOR);
                stmt.setString(1,id);
                rs = stmt.executeQuery();
               while(rs.next()){
                   String nombre = rs.getString(1);
                    lista.add(nombre);
               }
           
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return lista;
    }

       public boolean select(String id_usuario,String nombre_Contacto) throws SQLException,IllegalArgumentException {
        //id_contacto,id_trabajador,nombre_empresa,telefono,direccion,email,giro,nombre_representante
        //private final String SQL_SELECT_ = "SELECT  id_contacto,Usuario_id_trabajador,direccionPostal,email,giro,telefono,nombre_representante FROM Contacto_empresarial WHERE Usuario_id_trabajador=? nombre=?";
        //Selecciona un contacto empresarial realacionado con un id_trabajador y un nombre*/
           
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ContactoEmpresarialDTO contacto = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            stmt.setString(1,id_usuario);
            stmt.setString(2,nombre_Contacto);
            rs = stmt.executeQuery();
            return (rs.next());
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        
    }
    
    
}
