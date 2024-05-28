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

public final class Category implements CRUD<Category> {
    private int ID;
    private String name;
    public static int lastID = 0;

    public Category(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return ID == category.ID && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void inputCreate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the name of the category:");
        name = sc.nextLine();
    }

    @Override
    public void create() throws SQLException {
        lastID = Integer.max(lastID,ID); // This line should execute only if we create a new object in the database
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("INSERT INTO category VALUES (?, ?)");
        preparedStatement.setInt(1, ID);
        preparedStatement.setString(2, name);

        preparedStatement.executeUpdate();
    }

    @Override
    public void inputRead() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the category you want to see the data for:");
        ID = sc.nextInt();
    }

    @Override
    public Category read() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM category WHERE id = ?");
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return new Category(
                    resultSet.getInt("id"),
                    resultSet.getString("name"));
        }
        return null;
    }

    @Override
    public void inputUpdate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the category you want to update:");
        ID = sc.nextInt();
        sc.nextLine();
        System.out.println("\nType the new name of the category:");
        name = sc.nextLine();
    }

    @Override
    public void update() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("UPDATE category SET name = ? WHERE id = ?");
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, ID);

        preparedStatement.executeUpdate();
    }

    @Override
    public void inputDelete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the category you want to delete:");
        ID = sc.nextInt();
    }

    @Override
    public void delete() throws SQLException {
        // delete the entries that mention the category from BookCategory
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("DELETE FROM BookCategory WHERE idCategory = ?");
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        // delete the category
        preparedStatement = Repository.getInstance()
                                      .getConnection()
                                      .prepareStatement("DELETE FROM category WHERE id = ?");
        preparedStatement.setInt(1, ID);

        preparedStatement.executeUpdate();
    }

    @Override
    public List<Category> showAll() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM category");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Category> categories = new ArrayList<>();
        while(resultSet.next()){
            categories.add(new Category(resultSet.getInt("id"),
                                        resultSet.getString("name")));
        }
        return categories;
    }
}