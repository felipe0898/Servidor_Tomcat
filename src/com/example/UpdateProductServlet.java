package com.example;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateProductServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static String JDBC_URL = "jdbc:mysql://mysql:3306/inventariodb";
  private static String JDBC_USER = "root";
  private static String JDBC_PASS = "password";
  // private static String JDBC_PASS = "123";

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>Actualizar Producto</title></head><body>");
    out.println(
        "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">");
    out.println("<link rel='stylesheet' type='text/css' href='css/styles.css'>");

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

      String sql = "SELECT nombre, cantidad, precio FROM Productos WHERE id=?";
      stmt = conn.prepareStatement(sql);
      stmt.setInt(1, id);
      rs = stmt.executeQuery();

      if (rs.next()) {
        String nombre = rs.getString("nombre");
        int cantidad = rs.getInt("cantidad");
        double precio = rs.getDouble("precio");

        out.println("<h1>Actualizar Producto</h1>");
        out.println("<form action='tomcat_server_update' method='post' class='form-container'>");
        out.println("<input type='hidden' name='id' value='" + id + "'>");
        out.println("<label for='nombre'>Nombre:</label>");
        out.println("<input type='text' id='nombre' name='nombre' value='" + nombre + "' size='50'><br>");
        out.println("<label for='cantidad'>Cantidad:</label>");
        out.println("<input type='number' id='cantidad' name='cantidad' value='" + cantidad + "'size='50'><br>");
        out.println("<label for='precio'>Precio:</label>");
        out.println("<input type='number' step='0.01' id='precio' name='precio' value='" + precio + "'size='50'><br>");
        out.println("<input type='submit' class='button update-btn' value='Actualizar Producto'>");
        out.println("<br> <br>");
        out.println("<a href='tomcat_server' class='button'>Regresar</a>");
        out.println("</form>");
      } else {
        out.println("<h3>Producto no encontrado</h3>");
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      out.println("<h3>Error al buscar producto</h3>");
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        if (conn != null)
          conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    out.println("</body></html>");
    out.close();
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));
    String nombre = request.getParameter("nombre");
    int cantidad = Integer.parseInt(request.getParameter("cantidad"));
    double precio = Double.parseDouble(request.getParameter("precio"));

    Connection conn = null;
    PreparedStatement stmt = null;

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

      String sql = "UPDATE Productos SET nombre=?, cantidad=?, precio=? WHERE id=?";
      stmt = conn.prepareStatement(sql);
      stmt.setString(1, nombre);
      stmt.setInt(2, cantidad);
      stmt.setDouble(3, precio);
      stmt.setInt(4, id);
      stmt.executeUpdate();

      response.sendRedirect("tomcat_server"); // Redirige a HelloServlet después de actualizar
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      response.getWriter().println("<h3>Error al actualizar producto</h3>");
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
