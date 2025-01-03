package com.todo.OnlineBookstore.servlets.Cruds;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.sql.Connection;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.http.HttpServlet;


import java.sql.Statement;

import com.todo.OnlineBookstore.db.DBConnectionManager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet(name = "DisplayBooksServlet", urlPatterns = { "/viewBooks" })
public class DisplayBooksServlet extends HttpServlet {

    @Autowired
    private DBConnectionManager dbManager;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
                                                                                 
            throws ServletException, IOException {

        response.setContentType("text/html"); // set the response content type to HTML of the response
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
        out.println("h2 { color: #333; margin: 16px; text-align: center; }");
        out.println("table { border-collapse: collapse; margin: 20px auto; width: 80%; }");
        out.println("th, td { border: 1px solid #ddd; padding: 8px; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
        out.println("form { text-align: center; margin: 20px; }");
        out.println("input[type='text'] { padding: 5px; }");
        out.println("input[type='submit'] { padding: 5px 10px; background-color: #4CAF50; border: none; color: white; cursor: pointer; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>All Books are here</h2>");
        out.println("<form action='viewBooks' method='GET'>");
        out.println("Search by Title: <input type='text' name='search' />");
        out.println("<input type='submit' value='Search' />");
        out.println("</form>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>title</th><th>author</th><th>price</th> <th>action</th></tr>");
        try {
            dbManager.openConnection(); // open a connection to the database
            Connection conn = dbManager.getConnection();

            String search = request.getParameter("search");
            String sql = "SELECT id, title, author, price FROM Books";
            if (search != null && !search.isEmpty()) {
                sql += " WHERE title LIKE '%" + search + "%'";
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                Double price = rs.getDouble("price");
                

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + title + "</td>");
                out.println("<td>" + author + "</td>");
                out.println("<td>" + (price != null ? price : "") + "</td>");
                out.println("<td><a href='deleteBook?id=" + id + "'>Delete</a></td>");
                out.println("</tr>");
            }
            rs.close();
            stmt.close();

            out.println("</table>");
            out.println("<p class='back'><a href=\"index.html\"  > Go back</a></p>");
            out.println("</body></html>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error retrieving tasks: " + e.getMessage() + "</p>");
            out.println("</body></html>");
        } finally {
            try {
                dbManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
