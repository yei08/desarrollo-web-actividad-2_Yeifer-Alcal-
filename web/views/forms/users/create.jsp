<%-- 
    Document   : create
    Created on : 4/11/2024, 11:05:13 AM
    Author     : JEIFER ALCALA
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
            <h1>Agregar usuario</h1>
        <%-- Mensajes de error o éxito --%>
        <% if (request.getAttribute("errorMessage") != null) { %>
            <p style="color:red;"><%= request.getAttribute("errorMessage") %></p>
        <% } %>
        
        <% if (request.getAttribute("successMessage") != null) { %>
            <p style="color:green;"><%= request.getAttribute("successMessage") %></p>
        <% } %>
        
        <%-- Formulario para agregar usuario --%>
        <form action="<%= request.getContextPath() %>/Controllers/UserController.jsp?action=create" method="post">
            <label for="code">Código:</label><br>
            <input type="text" id="code" name="code" required><br><br>
        
            <label for="name">Nombre:</label><br>
            <input type="text" id="name" name="name" required><br><br>
        
            <label for="email">Email:</label><br>
            <input type="email" id="email" name="email" required><br><br>
        
            <label for="password">Contraseña:</label><br>
            <input type="password" id="password" name="password" required><br><br>
        
            <input type="submit" value="Agregar Usuario">
        </form>
        
        <br>
        <a href="<%= request.getContextPath() %>/index.jsp">Menú Principal</a>
        


    </body>
</html>
