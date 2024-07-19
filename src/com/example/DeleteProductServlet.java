package com.example;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteProductServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static String JDBC_URL = "jdbc:mysql://mysql:3306/inventariodb";
  private static String JDBC_USER = "root";
  private static String JDBC_PASS = "password";
  // private static String JDBC_PASS = "123";

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));

    Connection conn = null;
    PreparedStatement stmt = null;

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

      String sql = "DELETE FROM Productos WHERE id=?";
      stmt = conn.prepareStatement(sql);
      stmt.setInt(1, id);
      stmt.executeUpdate();

      // Respondemos con un mensaje (opcional)
      response.setContentType("text/plain");
      response.getWriter().write("Producto eliminado correctamente");
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      // Manejo de error, puedes enviar una respuesta de error al cliente si lo deseas
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar producto");
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
