package model;

import interfaces.CRUD;
import repository.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public final class Translator implements CRUD<Translator> {
    private int ID;
    private String lastName;
    private String firstName;
    public static int lastID = 0;

    public Translator(int ID, String lastName, String firstName) {
        this.ID = ID;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "Translator{" +
                "ID=" + ID +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Translator that = (Translator) o;
        return ID == that.ID && Objects.equals(lastName, that.lastName) && Objects.equals(firstName, that.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, lastName, firstName);
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


    @Override
    public void inputCreate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the last name of the translator:");
        lastName = sc.nextLine();
        System.out.println("\nType the first name of the translator:");
        firstName = sc.nextLine();
    }

    @Override
    public void create() throws SQLException {
        lastID = Integer.max(lastID,ID); // This line should execute only if we create a new object in the database
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("INSERT INTO translator VALUES (?, ?, ?)");
        preparedStatement.setInt(1, ID);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, firstName);

        preparedStatement.executeUpdate();
    }

    @Override
    public void inputRead() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the translator you want to see the data for:");
        ID = sc.nextInt();
    }

    @Override
    public Translator read() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM translator WHERE id = ?");
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return new Translator(
                    resultSet.getInt("id"),
                    resultSet.getString("lastName"),
                    resultSet.getString("firstName"));
        }
        return null;
    }

    @Override
    public void inputUpdate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the translator you want to update:");
        ID = sc.nextInt();
        sc.nextLine();
        System.out.println("\nType the new last name of the translator:");
        lastName = sc.nextLine();
        System.out.println("\nType the new first name of the translator:");
        firstName = sc.nextLine();
    }

    @Override
    public void update() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("UPDATE translator SET lastName = ?, firstName = ? WHERE id = ?");
        preparedStatement.setString(1, lastName);
        preparedStatement.setString(2, firstName);
        preparedStatement.setInt(3, ID);

        preparedStatement.executeUpdate();
    }

    @Override
    public void inputDelete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the translator you want to update:");
        ID = sc.nextInt();
    }

    @Override
    public void delete() throws SQLException {
        // update the entries that mention the translator from Book
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("UPDATE book SET idTranslator = -2 WHERE idTranslator = ?");
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        // delete the translator
        preparedStatement = Repository.getInstance()
                                      .getConnection()
                                      .prepareStatement("DELETE FROM translator WHERE id = ?");
        preparedStatement.setInt(1, ID);

        preparedStatement.executeUpdate();
    }

    @Override
    public List<Translator> showAll() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM translator");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Translator> translators = new ArrayList<>();

        while(resultSet.next()){
            translators.add(new Translator(resultSet.getInt("id"),
                                           resultSet.getString("lastName"),
                                           resultSet.getString("firstName")));
        }
        return translators;
    }
}