/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.ContactoDTO;
import DTO.ContactoEmpresarialDTO;
import DTO.ContactoPersonalDTO;
import DTO.TrabajadorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactoPersonalSql implements ContactoDAO{
    //int id,int id_trabajador,String nombre,String telefonoFijo,String telefonoCelular,String direccionPostal,String email,String parentesco
    private Connection userConn;
    private final String SQL_INSERT = "INSERT INTO Contacto_personal (Usuario_id_trabajador,nombre,direccionPostal ,email,parentesco,telefono,teldefonoCelular) VALUES(?,?,?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE Contacto_personal  SET nombre=?,direccionPostal =?,email=?,telefono=?,teldefonoCelular=?,parentesco=? WHERE id_contacto=?";
    private final String SQL_DELETE = "DELETE FROM Contacto_personal  WHERE id_contacto= ?";
    private final String SQL_SELECT = "SELECT email FROM Contacto_personal WHERE Usuario_id_trabajador=? AND nombre=?";
    private final String SQL_SELECT_TRABAJADOR = "SELECT nombre FROM Contacto_personal WHERE Usuario_id_trabajador = ?";
    
    public ContactoPersonalSql() {

    }
    
    public ContactoPersonalSql(Connection conn) {
        this.userConn = conn;
    }
    
    public int insert(ContactoDTO contact) throws SQLException,IllegalArgumentException{
    //int id,int id_trabajador,String nombre,String telefonoFijo,String telefonoCelular,String direccionPostal,String email,String parentesco
    if(!(contact instanceof ContactoPersonalDTO))
            throw new IllegalArgumentException();
        ContactoPersonalDTO contacto = (ContactoPersonalDTO) contact;
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
            stmt.setString(index++, contacto.getParentesco());
            stmt.setString(index++, contacto.getTelefono());
            stmt.setString(index++, contacto.getTelefonoCelular());
            
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
    //int id,int id_trabajador,String nombre,String telefonoFijo,String telefonoCelular,String direccionPostal,String email,String parentesco
    //    private final String SQL_UPDATE = "UPDATE Contacto_personal  SET nombre=?,direccionPostal =?,email=?,telefono=?,telefonoCelular=?,parentesco=? WHERE id_contacto=?";

    if(!(contact instanceof ContactoPersonalDTO))
            throw new IllegalArgumentException();
        ContactoPersonalDTO contacto = (ContactoPersonalDTO) contact;
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
            stmt.setString(index++, contacto.getTelefono());
            stmt.setString(index++, contacto.getTelefonoCelular());
            stmt.setString(index++, contacto.getParentesco());
            stmt.setInt(index++, contact.getId());
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
    public int delete(ContactoDTO contact) throws SQLException,IllegalArgumentException {
    //int id,int id_trabajador,String nombre,String telefonoFijo,String telefonoCelular,String direccionPostal,String email,String parentesco
    if(!(contact instanceof ContactoPersonalDTO))
            throw new IllegalArgumentException();
        ContactoPersonalDTO contacto = (ContactoPersonalDTO) contact;
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
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> lista = new ArrayList<>();
        try {
                conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
                stmt = conn.prepareStatement(SQL_SELECT_TRABAJADOR);
                stmt.setString(1,id);
                rs = stmt.executeQuery();
               int index = 1;
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
    //private final String SQL_SELECT_ = "SELECT  id_contacto,Usuario_id_trabajador,direccionPostal,email,giro,telefono,nombre_representante FROM Contacto_empresarial WHERE Usuario_id_trabajador=? nombre=?";

    //int id,int id_trabajador,String nombre,String telefono_fijo,String telefono_celular,String direccion_postal,String email,String parentesco
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ContactoPersonalDTO contacto = null;
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
