/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Infrastructure.Persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Bussines.execptions.DuplicateUserException;
import Bussines.execptions.UserNotFoundException;
import Domain.model.User;
import Infrastructure.Database.ConnectionDbMySql;

/**
 *
 * @author JEIFER ALCALA
 */
public class UserCrud {
   // Método para obtener todos los usuarios
public List<User> getAllUsers() {
    List<User> userList = new ArrayList<>();
    String query = "SELECT * FROM Users";

    try (Connection con = ConnectionDbMySql.getConnection();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            userList.add(new User(
                rs.getString("password"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("rol"),
                rs.getString("email"),
                rs.getString("telefono"),
                rs.getString("estado"),
                rs.getString("fecha_registro"),
                rs.getString("id") 
            ));
        }

    } catch (Exception e) {
        e.printStackTrace(); 
    }

    return userList;
}


}