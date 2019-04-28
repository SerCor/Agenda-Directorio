/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.CitaDTO;
import DTO.ContactoDTO;
import DTO.TrabajadorDTO;
import java.sql.SQLException;

/**
 *
 * @author SerCo
 */
public interface CitaDAO {
    public int insert(CitaDTO cita)throws SQLException;
    public int update(CitaDTO cita)throws SQLException;
    public int delete(CitaDTO cita)throws SQLException;
    public CitaDTO select(CitaDTO cita) throws SQLException ;
}
