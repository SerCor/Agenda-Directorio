/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.ContactoDTO;
import java.sql.SQLException;

/**
 *
 * @author SerCo
 */
public interface ContactoDAO {
    public int insert(ContactoDTO contacto) throws SQLException, IllegalArgumentException;
    public boolean select(String id_usuario,String nombre_Contacto) throws SQLException,IllegalArgumentException;
    public int delete(ContactoDTO contacto) throws SQLException, IllegalArgumentException;
    public int update(ContactoDTO contacto) throws SQLException, IllegalArgumentException;
}
