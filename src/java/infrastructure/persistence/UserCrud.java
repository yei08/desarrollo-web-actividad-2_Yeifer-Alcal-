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
// Método para agregar un nuevo usuario
public void addUser(User user) throws SQLException, DuplicateUserException {
    String query = "INSERT INTO Users (password, nombre, apellidos, rol, email, telefono, estado, fecha_registro, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = ConnectionDbMySql.getConnection();
         PreparedStatement stmt = con.prepareStatement(query)) {

        // Asignación de valores a los parámetros
    
        stmt.setString(1, user.getPassword());
        stmt.setString(2, user.getNombre());
        stmt.setString(3, user.getApellidos());
        stmt.setString(4, user.getRol());
        stmt.setString(5, user.getEmail());
        stmt.setString(6, user.getTelefono());
        stmt.setString(7, user.getEstado());
        stmt.setString(8, user.getFecha_registro());
        stmt.setString(9, user.getId()); 

        stmt.executeUpdate();

    } catch (SQLException e) {
        // Manejamos una posible excepción de clave duplicada
        if (e.getErrorCode() == 1062) { // Código de error de clave duplicada en MySQL
            throw new DuplicateUserException("El usuario con el código o email ya existe.");
        } else {
            throw e; // Propagamos la excepción SQLException para que la maneje el servicio
        }
    }
}

// Método para actualizar un usuario
public void updateUser(User user) throws SQLException, UserNotFoundException {
    String query = "UPDATE Users SET password = ?, nombre = ?, apellidos = ?, rol = ?, email = ?, telefono = ?, estado = ?, fecha_registro = ? WHERE id = ?";

    try (Connection con = ConnectionDbMySql.getConnection();
         PreparedStatement stmt = con.prepareStatement(query)) {

        // Asignación de valores a los parámetros
        stmt.setString(1, user.getPassword());
        stmt.setString(2, user.getNombre());
        stmt.setString(3, user.getApellidos());
        stmt.setString(4, user.getRol());
        stmt.setString(5, user.getEmail());
        stmt.setString(6, user.getTelefono());
        stmt.setString(7, user.getEstado());
        stmt.setString(8, user.getFecha_registro());
        stmt.setString(9, user.getId()); // Ahora usamos getId() en lugar de getCode()

        int rowsAffected = stmt.executeUpdate();
        
        if (rowsAffected == 0) {
            throw new UserNotFoundException("El usuario con el ID " + user.getId() + " no existe.");
        }

    } catch (SQLException e) {
        throw e; 
    }
}

// Método para eliminar un usuario
public void deleteUser(String id) throws SQLException, UserNotFoundException {
    String query = "DELETE FROM Users WHERE id = ?";

    try (Connection con = ConnectionDbMySql.getConnection();
         PreparedStatement stmt = con.prepareStatement(query)) {

        stmt.setString(1, id);

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new UserNotFoundException("El usuario con el ID " + id + " no existe.");
        }

    } catch (SQLException e) {
        throw e; // Propagamos la excepción SQLException para que la maneje el servicio
    }
}
// Método para obtener un usuario por ID
public User getUserById(String id) throws SQLException, UserNotFoundException {
    String query = "SELECT * FROM Users WHERE id = ?";
    User user = null;

    try (Connection con = ConnectionDbMySql.getConnection();
         PreparedStatement stmt = con.prepareStatement(query)) {

        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            user = new User(
                rs.getString("id"),
                rs.getString("password"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("rol"),
                rs.getString("email"),
                rs.getString("telefono"),
                rs.getString("estado"),
                rs.getString("fecha_registro")
            );
        } else {
            throw new UserNotFoundException("El usuario con el ID " + id + " no existe.");
        }

    } catch (SQLException e) {
        throw e; // Propagamos la excepción SQLException para que la maneje el servicio
    }

    return user;
}
// Método para autenticar un usuario por email y contraseña (Login)
public User getUserByEmailAndPassword(String email, String password) throws UserNotFoundException {
    User user = null;
    String query = "SELECT * FROM Usuarios WHERE email = ? AND password = ?";

    try (Connection con = ConnectionDbMySql.getConnection();
         PreparedStatement stmt = con.prepareStatement(query)) {

        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            user = new User(
               
                rs.getString("password"), // password (varchar)
                rs.getString("nombre"),    // nombre (varchar)
                rs.getString("email"),    // email (varchar)
                rs.getString("apellidos"), // apellidos (varchar)
                rs.getString("rol"),       // rol (varchar)
                rs.getString("telefono"),  // telefono (varchar)
                rs.getString("estado"),    // estado (varchar)
                rs.getString("fecha_registro"), // fecha_registro (fecha)
                rs.getString("id")          // id (int, PK)
                );
        } else {
            String message = "Credenciales incorrectas. No se encontró el usuario.";
            throw new UserNotFoundException(message);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return user;
}
// Método para obtener un usuario por email
public User getUserByEmail(String email) throws SQLException, UserNotFoundException {
    User user = null;
    String query = "SELECT * FROM Users WHERE email = ?";

    try (Connection con = ConnectionDbMySql.getConnection();
         PreparedStatement stmt = con.prepareStatement(query)) {

        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            user = new User(
                rs.getString("id"),
                rs.getString("password"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("rol"),
                rs.getString("email"),
                rs.getString("telefono"),
                rs.getString("estado"),
                rs.getString("fecha_registro")
            );
        } else {
            throw new UserNotFoundException("El usuario con el email " + email + " no existe.");
        }

    } catch (SQLException e) {
        throw e; // Propagamos la excepción SQLException para que la maneje el servicio
    }

    return user; // Retorno del usuario encontrado
}


}