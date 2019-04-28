/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author SerCo
 */
public class ContactoEmpresarialSql implements ContactoDAO {
    private Connection userConn;
    //id_contacto,id_trabajador,nombre_empresa,direccion,email,giro,telefono,nombre_representante
    private final String SQL_INSERT = "INSERT INTO Contacto_empresarial(Usuario_id_trabajador,nombre_empresa,direccionPostal,email,giro,telefono,nombre_representante) VALUES(?,?,?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE Contacto_empresarial  SET nombre_empresa=?,direccionPostal=?,email=?,giro=?,telefono=?,nombre_representante=? WHERE id_contacto=?";
    private final String SQL_DELETE = "DELETE FROM Contacto_empresarial WHERE id_contacto= ?";
    private final String SQL_SELECT_FECHA = "SELECT  id_contacto,Usuario_id_trabajador,direccionPostal,email,giro,telefono,nombre_representante FROM Contacto_empresarial WHERE Usuario_id_trabajador=?";
    private final String SQL_SELECT_TRABAJADOR = "SELECT nombre_empresa FROM Contacto_empresarial WHERE Usuario_id_trabajador = ?";

    public ContactoEmpresarialSql() {

    }
    
    public ContactoEmpresarialSql(Connection conn) {
        this.userConn = conn;
    }
    
    public int insert(ContactoDTO contact) throws SQLException,IllegalArgumentException{
        //id_contacto,id_trabajador,nombre_empresa,telefono,direccion,email,giro,nombre_representante
        //private final String SQL_INSERT = "INSERT INTO Contacto_empresarial(Usuario_id_trabajador,nombre_empresa,direccionPostal,email,giro,telefono,nombre_representante) VALUES(?,?,?,?,?,?,?)";

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

   /* public ContactoEmpresarialDTO select() throws SQLException {
        //id_contacto,id_trabajador,nombre_empresa,telefono,direccion,email,giro,nombre_representante
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ContactoEmpresarialDTO contacto = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_FECHA);
            rs = stmt.executeQuery();
            int index = 1;
           int id_contacto =  rs.getInt(index++);
           int id_trabajador = rs.getInt(index++);
           String nombre_empresa =rs.getString(index++);
           String telefono = rs.getString(index++);
           String direccion  = rs.getString(index++);
           String email = rs.getString(index++);
           String giro = rs.getString(index++); 
           String nombre_representante = rs.getString(index++);
           
           //(int id,int id_trabajador,String nombre,String telefono,String direccionPostal,String email,String giro,String representante)
           contacto = new ContactoEmpresarialDTO(id_contacto,id_trabajador,nombre_empresa,telefono,direccion,email,giro,nombre_representante);
           
           
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return contacto;
    }*/
    
    
}
