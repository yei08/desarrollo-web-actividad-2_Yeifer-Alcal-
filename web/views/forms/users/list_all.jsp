<%-- 
    Document   : list_all
    Created on : 4/11/2024, 11:14:51 AM
    Author     : JEIFER ALCALA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="Domain.Model.User" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Usuarios</title>
</head>
<body>
    <h1>Lista de Todos los Usuarios</h1>

    <%-- Mensajes de error o éxito --%>
    <% if (request.getAttribute("errorMessage") != null) { %>
        <p style="color:red;"><%= request.getAttribute("errorMessage") %></p>
    <% } %>

    <% if (request.getAttribute("successMessage") != null) { %>
        <p style="color:green;"><%= request.getAttribute("successMessage") %></p>
    <% } %>

    <%-- Tabla para mostrar la lista de usuarios --%>
    <table border="1">
        <thead>
            <tr>
                <th>Código</th>
                <th>Nombre</th>
                <th>Email</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <% List<User> users = (List<User>) request.getAttribute("users"); %>
            <% if (users != null && !users.isEmpty()) { %>
                <% for (User user : users) { %>
                    <tr>
                        <td><%= user.getCode() %></td>
                        <td><%= user.getName() %></td>
                        <td>
                            <%-- Enlace mailto: para el cliente de correo --%>
                            <a href="mailto:<%= user.getEmail() %>?cc=&subject=Saludos de John Carlos Arrieta Arrieta&body=Atento saludo mi estimado">
                                <%= user.getEmail() %>
                            </a>
                        </td>
                        <td>
                            <a href="<%= request.getContextPath() %>/Controllers/UserController.jsp?action=search&code=<%= user.getCode() %>">Editar</a> |
                            <a href="<%= request.getContextPath() %>/Controllers/UserController.jsp?action=delete&code=<%= user.getCode() %>" onclick="return confirm('¿Seguro que deseas eliminar este usuario?');">Eliminar</a>
                        </td>
                    </tr>
                <% } %>
            <% } else { %>
                <tr>
                    <td colspan="4">No hay usuarios disponibles</td>
                </tr>
            <% } %>
        </tbody>
    </table>

    <br>
    <a href="<%= request.getContextPath() %>/Controllers/UserController.jsp?action=create">Agregar Nuevo Usuario</a>
</body>
</html>
