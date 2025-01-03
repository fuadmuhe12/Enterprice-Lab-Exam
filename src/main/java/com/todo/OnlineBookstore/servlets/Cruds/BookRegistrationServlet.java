package com.todo.OnlineBookstore.servlets.Cruds;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.todo.OnlineBookstore.db.DBConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet(name = "BookRegistrationServlet", urlPatterns = { "/createBook" })
public class BookRegistrationServlet extends HttpServlet {

    @Autowired
    private DBConnectionManager dbManager;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String priceStr = request.getParameter("price"); // Ensure consistency with form

        Double price = null;
        if (priceStr != null && !priceStr.isEmpty()) {
            
            try {
                price = Double.parseDouble(priceStr);
            } catch (IllegalArgumentException e) {
                // Handle invalid date format
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid price format.");
                return;
            }
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            dbManager.openConnection();
            Connection conn = dbManager.getConnection();

            String sql = "INSERT INTO Books (title, author, price) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setDouble(3, price);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<html><body>");
                out.println("<h3>Book created successfully!</h3>");
                out.println("<p><a href=\"index.html\"> Go back</a></p>");
                out.println("</body></html>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h3>Error creating Book: " + e.getMessage() + "</h3>");
            out.println("<p><a href=\"index.html\"> Go back</a></p>");
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
