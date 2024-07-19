package com.example;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddProductServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static String JDBC_URL = "jdbc:mysql://mysql:3306/inventariodb";
  private static String JDBC_USER = "root";
  private static String JDBC_PASS = "password";
  // private static String JDBC_PASS = "123";

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    // Generar HTML para el formulario de agregar producto
    out.println("<html><head><title>Agregar Producto</title>");
    out.println(
        "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">");

    out.println("<link rel='stylesheet' type='text/css' href='css/styles.css'>"); // Referencia al archivo CSS externo
    out.println("</head><body>");

    out.println("<h1>Agregar un nuevo Producto</h1>");
    out.println("<form action='tomcat_server_add' method='post' class='form-container'>");
    out.println("Nombre: <input type='text' name='nombre'><br>");
    out.println("Cantidad: <input type='number' name='cantidad'><br>");
    out.println("Precio: <input type='number' step='0.01' name='precio'><br>");
    out.println("<input type='submit' class='button add-btn' value='Agregar Producto'>");
    out.println("</form>");
    out.println("<br>");
    out.println("<a href='index.html' class='button'>Regresar</a>");

    out.println("</body></html>");
    out.close();
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String nombre = request.getParameter("nombre");
    int cantidad = Integer.parseInt(request.getParameter("cantidad"));
    double precio = Double.parseDouble(request.getParameter("precio"));

    Connection conn = null;
    PreparedStatement stmt = null;

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

      String sql = "INSERT INTO Productos (nombre, cantidad, precio) VALUES (?, ?, ?)";
      stmt = conn.prepareStatement(sql);
      stmt.setString(1, nombre);
      stmt.setInt(2, cantidad);
      stmt.setDouble(3, precio);
      stmt.executeUpdate();

      // Redirige a la página principal después de agregar
      response.sendRedirect("tomcat_server");
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      response.getWriter().println("<h3>Error al agregar producto</h3>");
    } finally {
      try {
        if (stmt != null)
          stmt.close();
        if (conn != null)
          conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
