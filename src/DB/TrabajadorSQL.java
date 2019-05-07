package DB;

import DAO.TrabajadorDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DTO.TrabajadorDTO;

public class TrabajadorSQL implements TrabajadorDAO{
    @Override
    public boolean insert(TrabajadorDTO trabajador) throws SQLException {
        PreparedStatement st = null;
        Connection con = null;
        try{
            con = Conection.getConnection();
            st = con.prepareStatement("INSERT INTO Usuario VALUES (?,?,?,?,?,?,?,?)");
            st.setString(1, trabajador.getIdTrabajador());
            st.setString(2,trabajador.getNombre());
            st.setString(3,trabajador.getPuesto());
            st.setString(4,trabajador.getUsuario());
            st.setString(5,trabajador.getPassword());
            st.setString(6,trabajador.getHrEntrada());
            st.setString(7,trabajador.getHrSalida());
            st.setString(8,trabajador.getHrComida());
            st.executeUpdate();
            
        }catch(SQLException e){
            return false;
        }
        finally{
            Conection.close(st);
            Conection.close(con);
        }
        return true;
    }

    @Override
    public TrabajadorDTO select(String user,String pass) throws SQLException {
        Connection con= null;
        PreparedStatement peticion=null;
        ResultSet rs=null;
        String query= "SELECT * FROM Usuario WHERE usuario = ? and password = ? ";
        TrabajadorDTO trabajador=null;
        try{
            con=Conection.getConnection();
            peticion=con.prepareStatement(query);
            peticion.setString(1, user);
            peticion.setString(2, pass);
            rs = peticion.executeQuery();
            trabajador=new TrabajadorDTO();
            if(rs.next()){
                trabajador.setIdTrabajador(rs.getString("id_trabajador"));                
                trabajador.setNombre(rs.getString("nombre"));
                trabajador.setUsuario(rs.getString("usuario"));
                trabajador.setPuesto(rs.getString("puesto"));
            }
       }catch(SQLException e){
           return null;
       }finally{
            Conection.close(rs);
            Conection.close(peticion);
            Conection.close(con);
        }   
        return trabajador;
    }
    @Override
    public boolean availableId(String id)throws SQLException{
        Connection con= null;
        PreparedStatement peticion=null;
        ResultSet rs=null;
        String query= "SELECT id_trabajador FROM Usuario WHERE id_trabajador = ?";
        try{
            con=Conection.getConnection();
            peticion=con.prepareStatement(query);
            peticion.setString(1, id);
            rs = peticion.executeQuery();
            if(rs.next()){
                return false;
            }
       }catch(SQLException e){
           return false;
       }finally{
            Conection.close(rs);
            Conection.close(peticion);
            Conection.close(con);
        }   
        return true;
    }
    @Override
    public boolean availableUsuario(String user)throws SQLException{
        Connection con= null;
        PreparedStatement peticion=null;
        ResultSet rs=null;
        String query= "SELECT usuario FROM Usuario WHERE usuario = ?";
        try{
            con=Conection.getConnection();
            peticion=con.prepareStatement(query);
            peticion.setString(1, user);
            rs = peticion.executeQuery();
            if(rs.next()){
                return false;
            }
       }catch(SQLException e){
           return false;
       }finally{
            Conection.close(rs);
            Conection.close(peticion);
            Conection.close(con);
        }   
        return true;
    }
    
}
