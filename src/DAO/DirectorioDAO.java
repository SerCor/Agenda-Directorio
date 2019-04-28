/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Directorio;
import DTO.TrabajadorDTO;
import java.sql.SQLException;

/**
 *
 * @author SerCo
 */
public interface DirectorioDAO {
    
    public Directorio getDirectorio(TrabajadorDTO usuario)throws SQLException;
    
}
