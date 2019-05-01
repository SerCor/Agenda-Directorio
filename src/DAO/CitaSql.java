/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.CitaDTO;
import DTO.ContactoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

/**
 *
 * @author SerCo
 */
public class CitaSql implements CitaDAO {
    private Connection userConn;

    private final String SQL_INSERT = "INSERT INTO Cita(Usuario_id_trabajador,citado, fecha,hora_inicio,hora_final,lugar,asunto) VALUES(?,?,?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE Cita SET citado=? WHERE Usuario_id_trabajador=? AND citado=?";
     private final String SQL_UPDATE_COMPLETO = "UPDATE Cita SET citado=?,fecha=?,hora_inicio=?,hora_final=?,lugar=?,asunto=? WHERE id_cita=?";
    private final String SQL_DELETE = "DELETE FROM Cita WHERE id_cita= ?";
    private final String SQL_DELETE_ID_USUARIO = "DELETE FROM Cita WHERE citado = ? AND Usuario_id_trabajador = ?";
    private final String SQL_SELECT = "select Usuario_id_trabajador,citado, fecha,hora_inicio,hora_final,lugar,asunto FROM Cita WHERE id_cita=?";

    public CitaSql() {

    }
    
    public CitaSql(Connection conn) {
        this.userConn = conn;
    }
    
    @Override
    public int insert(CitaDTO cita) throws SQLException{
   //     private final String SQL_INSERT = "INSERT INTO cita(Usuario_id_trabajador,citado, fecha,hora_inicio,hora_final,lugar,asunto) VALUES(?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;
            stmt.setString(index++, cita.getIdTrabajador());
            stmt.setString(index++, cita.getContacto());
            stmt.setString(index++, cita.getFecha().toString());
            stmt.setInt(index++, cita.getHrInicio());
            stmt.setInt(index++, cita.getHrFin());
            System.out.println("Lugar de la cita: " + cita.getLugar());
            stmt.setString(index++, cita.getLugar());
            stmt.setString(index++, cita.getAsunto());
            rows = stmt.executeUpdate();
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return rows;
    }

    
    public int update(CitaDTO cita)
            throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_COMPLETO);
            int index = 1;
            stmt.setString(index++, cita.getContacto());
            stmt.setString(index++, cita.getFecha().toString());
            stmt.setInt(index++, cita.getHrInicio());
            stmt.setInt(index++, cita.getHrFin());
            stmt.setString(index++, cita.getLugar());
            stmt.setString(index++, cita.getAsunto());
            stmt.setInt(index++,cita.getId());
            rows = stmt.executeUpdate();
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }
    
    
    @Override
    //    private final String SQL_UPDATE = "UPDATE Cita SET citado=? WHERE Usuario_id_trabajador=? AND citado=?";
    public int update(String NuevoCitado,String id_trabajador,String citado)
            throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1;
            stmt.setString(index++, NuevoCitado);
            stmt.setString(index++, id_trabajador);
            stmt.setString(index++, citado);
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
    @Override
    public int delete(CitaDTO cita) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, cita.getId());
            rows = stmt.executeUpdate();
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }
    
    @Override
        public int delete(String id,String id_trabajador)throws SQLException{
            Connection conn = null;
            PreparedStatement stmt = null;
            int rows = 0;
            try {
                conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
                stmt = conn.prepareStatement(SQL_DELETE_ID_USUARIO);
                stmt.setString(1, id);
                stmt.setString(2, id_trabajador);
                rows = stmt.executeUpdate();
            } finally {
                Conexion.close(stmt);
                if (this.userConn == null) {
                    Conexion.close(conn);
                }
            }
            return rows;
        }

    
    @Override
    public CitaDTO select(CitaDTO cita) throws SQLException {
        // private final String SQL_SELECT = "select Usuario_id_trabajador,citado, fecha,hora_inicio,hora_final,lugar,asunto FROM cita WHERE id_cita=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            stmt.setInt(1, cita.getId());
            rs = stmt.executeQuery();
            int index =1;
            String id_trabajador = rs.getString(index++);
            String citado = rs.getString(index++);
            String fecha = rs.getString(index++);
            int hrInicio = rs.getInt(index++);
            int hrFinal = rs.getInt(index++);
            String lugar = rs.getString(index++);
            String asunto = rs.getString(index++);
            //    public CitaDTO(int id,String id_trabajador,String nombre,LocalDate f,int hrI,int hrF,String lug, String a){
            System.out.println(lugar);
           cita = new CitaDTO(id_trabajador,citado,LocalDate.parse(fecha),hrInicio,hrFinal,lugar,asunto);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return cita;
    }
    
    
}
