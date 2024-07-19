package com.example;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static String JDBC_URL = "jdbc:mysql://mysql:3306/inventariodb";
  private static String JDBC_USER = "root";
  private static String JDBC_PASS = "password";
  // private static String JDBC_PASS = "123";

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    // Generar HTML
    out.println("<html><head><title>Productos</title>");
    out.println(
        "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">");

    out.println("<link rel='stylesheet' type='text/css' href='css/styles.css'>");
    out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>");

    out.println("</head><body>");

    out.println("<h1>Lista de Productos</h1>");
    out.println("<table>");
    out.println("<tr>");
    out.println("<th>Nombre</th>");
    out.println("<th>Cantidad</th>");
    out.println("<th>Precio</th>");
    out.println("<th>Acciones</th>"); // Nueva columna para acciones
    out.println("</tr>");

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

      stmt = conn.createStatement();
      String sql = "SELECT id, nombre, cantidad, precio FROM Productos";
      rs = stmt.executeQuery(sql);

      int rowCount = 0;
      while (rs.next()) {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        int cantidad = rs.getInt("cantidad");
        double precio = rs.getDouble("precio");

        String rowColor = (rowCount % 2 == 0) ? "#FFFFFF" : "#F2F2F2";
        out.println("<tr style='background-color: " + rowColor + "'>");
        out.println("<td>" + nombre + "</td>");
        out.println("<td>" + cantidad + "</td>");
        out.println("<td>$" + precio + "</td>");
        out.println("<td>");
        out.println("<a href='tomcat_server_update?id=" + id + "' class='button update-btn'>Actualizar</a> ");
        out.println("<a href='#' onclick='deleteProduct(" + id + ");' class='button delete-btn'>Eliminar</a>");
        out.println("</td>");
        out.println("</tr>");
        rowCount++;
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      out.println("<tr><td colspan='4'>Productos no encontrados!</td></tr>");
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

    out.println("</table>");

    out.println("<br>");
    out.println("<a href='index.html' class='button'>Regresar</a>");

    // Script JavaScript para eliminar producto vía AJAX
    out.println("<script>");
    out.println("function deleteProduct(id) {");
    out.println("  if (confirm(' ¿Estas seguro de eliminar este producto?')) {");
    out.println("    $.ajax({");
    out.println("      type: 'POST',");
    out.println("      url: 'tomcat_server_delete?id=' + id,");
    out.println("      success: function(data) {");
    out.println("        location.reload(); // Recargar la página después de eliminar");
    out.println("      },");
    out.println("      error: function() {");
    out.println("        alert('Error al eliminar el producto.');");
    out.println("      }");
    out.println("    });");
    out.println("  }");
    out.println("}");
    out.println("</script>");

    out.println("</body></html>");
    out.close();
  }
}
