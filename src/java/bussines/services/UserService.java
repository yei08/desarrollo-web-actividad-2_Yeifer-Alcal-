/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Bussines.services;
import java.sql.SQLException;
import java.util.List;
import Bussines.execptions.DuplicateUserException;
import Bussines.execptions.UserNotFoundException;
import Domain.model.User;
import Infrastructure.Persistence.UserCrud;

/**
 *
 * @author JEIFER ALCALA
 */
public class UserService {
      
   
    private UserCrud userCrud;

    // Constructor
    public UserService() {
        this.userCrud = new UserCrud();
    }
    
    // Método para obtener todos los usuarios
    public List<User> getAllUsers() throws SQLException {
        return userCrud.getAllUsers();
    }
    
    // Método para agregar un nuevo usuario
    public void createUser(String id, String nombre, String email, String password, String apellidos,String rol, String fecha_registro,String telefono,String estado)
            throws DuplicateUserException, SQLException {
            User user = new User(id, password,nombre, apellidos, rol,email,fecha_registro, telefono, estado);
        userCrud.addUser(user);
    }
    
    // Método para actualizar un usuario
    public void updateUser(String id, String nombre, String email, String password, String apellidos,String rol, String fecha_registro,String telefono,String estado)
        throws UserNotFoundException, SQLException {
        User user = new User(id, password,nombre, apellidos, rol,email,fecha_registro, telefono, estado);
        userCrud.updateUser(user);
    }
    
    // Método para eliminar un usuario
    public void deleteUser(String id) throws UserNotFoundException, SQLException {
        userCrud.deleteUser(id);
    }
    
    // Método para obtener un usuario por ID (antes era por código)
    public User getUserById(String id) throws UserNotFoundException, SQLException {
        return userCrud.getUserById(id);
    }

    // Método para autenticar un usuario (login)
    public User loginUser(String email, String password) throws UserNotFoundException, SQLException {
        // Usamos el método getUserByEmail en lugar de obtener todos los usuarios
        User user = userCrud.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            throw new UserNotFoundException("Credenciales incorrectas. No se encontró el usuario o la contraseña es incorrecta.");
        }
    }

 // Método para obtener un usuario por email desde UserService
public User getUserByEmail(String email) throws SQLException, UserNotFoundException {
    return userCrud.getUserByEmail(email);
}

}
