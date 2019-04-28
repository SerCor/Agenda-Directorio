/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.AgendaDTO;
import DTO.ContactoDTO;
import DTO.TrabajadorDTO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author SerCo
 */
public interface AgendaDAO {
    
    public AgendaDTO select(TrabajadorDTO trabajador,LocalDate fecha) throws SQLException;
}
