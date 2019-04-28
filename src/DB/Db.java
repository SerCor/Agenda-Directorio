package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Db {
    String dbUrl;
    Connection connection=null;
    private boolean connected;
    
    public Db(){
      try{
         Class.forName("org.sqlite.JDBC");
         connection = DriverManager.getConnection("jdbc:sqlite:test.db");
         if(connection==null)
            this.connected=false;
         else
            this.connected=true;

      }catch (Exception e){
         System.out.println(e.getMessage());
         System.exit(0);
      }
    }
    
    public boolean isLogin(String user, String pass) throws SQLException{
        PreparedStatement peticion=null;
        ResultSet resultset=null;
        String query= "SELECT * FROM Usuario WHERE usuario = ? and password = ? ";
        try{
            peticion=connection.prepareStatement(query);
            peticion.setString(1, user);
            peticion.setString(2, pass);
            resultset=peticion.executeQuery();
            if(resultset.next())
                return true;
            else
                return false;
            
        }catch(Exception e){
            return false;
        }finally{
            peticion.close();
            resultset.close();
        }
    }
    public boolean isConnected(){
        return connected;
    }
    public void close(){
        try{
            connection.close();
        }
        catch(SQLException ex){
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
}
