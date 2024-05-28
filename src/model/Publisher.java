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

public final class Publisher implements CRUD<Publisher> {
    private int ID;
    private String name;
    private String foundingDate; // this is stored as String because it's easier with the input and the database
    public static int lastID = 0;

    public Publisher(int ID, String name, String foundingDate) {
        this.ID = ID;
        this.name = name;
        this.foundingDate = foundingDate;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", foundingDate=" + foundingDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return ID == publisher.ID && Objects.equals(name, publisher.name) && Objects.equals(foundingDate, publisher.foundingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, foundingDate);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getFoundingDate() {
        return foundingDate;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoundingDate(String foundingDate) {
        this.foundingDate = foundingDate;
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
                                                        .prepareStatement("INSERT INTO publisher VALUES (?, ?, ?)");
        preparedStatement.setInt(1, ID);
        preparedStatement.setString(2, name);
        preparedStatement.setDate(3, Date.valueOf(foundingDate));

        preparedStatement.executeUpdate();
    }

    @Override
    public void inputRead() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the publisher you want to see the data for:");
        ID = sc.nextInt();
    }

    @Override
    public Publisher read() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM publisher WHERE id = ?");
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return new Publisher(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("foundingDate").toString());
        }
        return null;
    }

    @Override
    public void inputUpdate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the publisher you want to update:");
        ID = sc.nextInt();
        sc.nextLine();
        inputData(sc);
    }

    @Override
    public void update() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("UPDATE publisher SET name = ?, foundingDate = ? WHERE id = ?");
        preparedStatement.setString(1, name);
        preparedStatement.setDate(2, Date.valueOf(foundingDate));
        preparedStatement.setInt(3, ID);

        preparedStatement.executeUpdate();
    }

    @Override
    public void inputDelete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the publisher you want to delete:");
        ID = sc.nextInt();
    }

    @Override
    public void delete() throws SQLException {
        // delete the entries that mention the publisher from BookPublisher
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("DELETE FROM BookPublisher WHERE idPublisher = ?");
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        // delete the publisher
        preparedStatement = Repository.getInstance()
                                      .getConnection()
                                      .prepareStatement("DELETE FROM publisher WHERE id = ?");
        preparedStatement.setInt(1, ID);

        preparedStatement.executeUpdate();
    }

    @Override
    public List<Publisher> showAll() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM publisher");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Publisher> publishers = new ArrayList<>();
        while(resultSet.next()){
            publishers.add(new Publisher(resultSet.getInt("id"),
                                         resultSet.getString("name"),
                                         resultSet.getDate("foundingDate").toString()));
        }
        return publishers;
    }

    private void inputData(Scanner sc) {
        System.out.println("\nType the new name of the publisher:");
        name = sc.nextLine();
        System.out.println("\nType the founding date (format YYYY-MM-DD):");
        foundingDate = sc.nextLine();
    }
}