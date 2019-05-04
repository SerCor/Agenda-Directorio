
package DAO;

import DTO.AgendaDTO;
import DTO.CitaDTO;
import DTO.ContactoDTO;
import DTO.ContactoEmpresarialDTO;
import DTO.TrabajadorDTO;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

public class AgendaSql  implements AgendaDAO{
    private Connection userConn;
    
    private final String SQL_SELECT = "SELECT id_cita,Usuario_id_trabajador,citado, fecha,hora_inicio,hora_final,lugar,asunto FROM Cita WHERE Usuario_id_trabajador = ? AND fecha = ?";
    public AgendaSql(){}
    
    public AgendaSql(Connection con){
        userConn = con;
    }
    
    @Override
    public AgendaDTO select(TrabajadorDTO trabajador,LocalDate fecha) throws SQLException{
        //private final String SQL_SELECT = "SELECT id_cita ,Usuario_id_trabajador,citado, fecha,hora_inicio,hora_final,lugar,asunto FROM Cita WHERE Usuario_id_trabajador = ? AND fecha = ?";
        //Retorna agenda con las citas relacionadas a un trabajador en una fecha especifica
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        AgendaDTO agenda = new AgendaDTO();
        try {
            
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            stmt.setString(1,trabajador.getIdTrabajador());
            stmt.setString(2,fecha.toString());
            rs = stmt.executeQuery();
            CitaDTO cita = null;
            
           while(rs.next()){
                int index = 1;
                int id_cita = rs.getInt(index++);
                String id_trabajador = rs.getString(index++);
                String nombre_citado = rs.getString(index++);
                String fech = rs.getString(index++);
                int hr_inicio = rs.getInt(index++);
                int hr_final = rs.getInt(index++);
                String lugar = rs.getString(index++);               
                String asunto = rs.getString(index++);
                System.out.println("Lugar en la base de datos: "+ lugar);
                System.out.println("Asunto en la base de datos: " + asunto);
                cita = new CitaDTO(id_cita,id_trabajador,nombre_citado,LocalDate.parse(fech),hr_inicio,hr_final,lugar,asunto);
                System.out.println(cita);
                agenda.agregarCita(cita);
           }
           
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return agenda;
        
    }
    
}
