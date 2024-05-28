package model;

import interfaces.CRUD;
import repository.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public final class Author implements CRUD<Author> {
    private int ID;
    private String lastName;
    private String firstName;
    private String debutDate;// this is stored as String because it's easier with the input and the database
    public static int lastID = 0;

    public Author(int ID, String lastName, String firstName, String debutDate) {
        this.ID = ID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.debutDate = debutDate;
    }

    @Override
    public String toString() {
        return "Author{" +
                "ID=" + ID +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", debutDate='" + debutDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return ID == author.ID && Objects.equals(lastName, author.lastName) && Objects.equals(firstName, author.firstName) && Objects.equals(debutDate, author.debutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, lastName, firstName, debutDate);
    }

    public int getID() {
        return ID;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getDebutDate() {
        return debutDate;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setDebutDate(String debutDate) {
        this.debutDate = debutDate;
    }

    @Override
    public void inputCreate() {
        Scanner sc = new Scanner(System.in);
        inputData(sc);
    }

    @Override
    public void create() throws SQLException {
        lastID = Integer.max(lastID,ID); // This line should execute only if we create a new object in the database
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("INSERT INTO author VALUES (?,?,?,?)");
        preparedStatement.setInt(1, ID);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, firstName);
        preparedStatement.setDate(4, Date.valueOf(debutDate));

        preparedStatement.executeUpdate();
    }

    @Override
    public void inputRead() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the author you want to see the data for:");
        ID = sc.nextInt();
    }

    @Override
    public Author read() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM author WHERE id = ?");
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return new Author(resultSet.getInt("id"),
                    resultSet.getString("lastName"),
                    resultSet.getString("firstName"),
                    resultSet.getDate("debutDate").toString());
        }
        return null;
    }

    @Override
    public void inputUpdate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the author you want to update:");
        ID = sc.nextInt();
        sc.nextLine();
        inputData(sc);
    }

    @Override
    public void update() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("UPDATE author SET lastname = ?, firstname = ?, debutDate = ? WHERE id = ?");
        preparedStatement.setString(1, lastName);
        preparedStatement.setString(2, firstName);
        preparedStatement.setDate(3, Date.valueOf(debutDate));
        preparedStatement.setInt(4, ID);

        preparedStatement.executeUpdate();
    }

    @Override
    public void inputDelete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the author you want to delete:");
        ID = sc.nextInt();
    }

    @Override
    public void delete() throws SQLException {
        // delete the entries that mention the author from BookAuthorEditor
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("DELETE FROM BookAuthorEditor WHERE idAuthor = ?");
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        // delete the author
        preparedStatement = Repository.getInstance()
                                      .getConnection()
                                      .prepareStatement("DELETE FROM author WHERE id = ?");
        preparedStatement.setInt(1, ID);

        preparedStatement.executeUpdate();
    }

    @Override
    public List<Author> showAll() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM author");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Author> authors = new ArrayList<>();
        while(resultSet.next()) {
            authors.add(new Author(resultSet.getInt("id"),
                                   resultSet.getString("lastName"),
                                   resultSet.getString("firstName"),
                                   resultSet.getDate("debutDate").toString()));
        }
        return authors;
    }

    private void inputData(Scanner sc) {
        System.out.println("\nType the new last name of the author:");
        lastName = sc.nextLine();
        System.out.println("\nType the new first name of the author:");
        firstName = sc.nextLine();
        System.out.println("\nType the new debut date (format YYYY-MM-DD):");
        debutDate = sc.nextLine();
    }
}