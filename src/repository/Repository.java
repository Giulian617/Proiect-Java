package repository;

import model.Book;
import model.Translator;
import model.Category;
import model.Publisher;
import model.Author;
import model.Editor;

import java.sql.*;
public final class Repository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/BookDatabase";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "root";
    private static Repository INSTANCE = null;
    private static Connection connection = null;

    static {
        try {
            // connecting to the database
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            // getting the maximum ID from the database for each table
            getID("Book");
            getID("Translator");
            getID("Category");
            getID("Publisher");
            getID("Author");
            getID("Editor");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getID(String object_type) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id) FROM " + object_type);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            if (object_type.equals("Book")) {
                Book.lastID = resultSet.getInt(1);
            }
            else if (object_type.equals("Translator")) {
                Translator.lastID = resultSet.getInt(1);
            }
            else if (object_type.equals("Category")) {
                Category.lastID = resultSet.getInt(1);
            }
            else if (object_type.equals("Publisher")) {
                Publisher.lastID = resultSet.getInt(1);
            }
            else if (object_type.equals("Author")) {
                Author.lastID = resultSet.getInt(1);
            }
            else if (object_type.equals("Editor")) {
                Editor.lastID = resultSet.getInt(1);
            }
        }
        // else isn't neccessary ince lastID is 0 by default
    }

    private Repository() {}

    // closing the connection to the database
    public void closeConnection() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Repository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Repository();
        }
        return INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }
}