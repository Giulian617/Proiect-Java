package model;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;
import exceptions.PrimaryKeyException;
import interfaces.CRUD;
import repository.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public final class BookCategory implements CRUD<BookCategory> {
    private int idBook;
    private int idCategory;

    public BookCategory(int idBook, int idCategory) {
        this.idBook = idBook;
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return "BookCategory{" +
                "idBook=" + idBook +
                ", idCategory=" + idCategory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCategory that = (BookCategory) o;
        return idBook == that.idBook && idCategory == that.idCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBook, idCategory);
    }

    public Pair<Integer, Integer> getID() {
        return new Pair<>(idBook, idCategory);
    }

    public int getIdBook() {
        return idBook;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public void inputCreate() throws PrimaryKeyException {
        Scanner sc = new Scanner(System.in);
        boolean check = false;
        while(!check) {
            System.out.println("\nType the ID of the book:");
            idBook = sc.nextInt();
            System.out.println("\nType the ID of the category:");
            idCategory = sc.nextInt();
            try {
                PreparedStatement preparedStatement = Repository.getInstance()
                                                                .getConnection()
                                                                .prepareStatement("SELECT * FROM book WHERE id = ? ");
                preparedStatement.setInt(1, idBook);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.next()) {
                    System.out.println("This book does not exist.");
                    continue;
                }
                preparedStatement = Repository.getInstance()
                                              .getConnection()
                                              .prepareStatement("SELECT * FROM category WHERE id = ? ");
                preparedStatement.setInt(1, idCategory);
                resultSet = preparedStatement.executeQuery();
                if(!resultSet.next()) {
                    System.out.println("This category does not exist.");
                    continue;
                }
                preparedStatement = Repository.getInstance()
                                              .getConnection()
                                              .prepareStatement("SELECT * FROM BookCategory WHERE idBook = ? AND idCategory = ?");
                preparedStatement.setInt(1, idBook);
                preparedStatement.setInt(2, idCategory);
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    throw new PrimaryKeyException();
                }
                else {
                    check = true;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void create() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("INSERT INTO BookCategory VALUES (?, ?)");
        preparedStatement.setInt(1, idBook);
        preparedStatement.setInt(2, idCategory);

        preparedStatement.executeUpdate();
    }

    @Override
    public void inputRead() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the book:");
        idBook = sc.nextInt();
        System.out.println("\nType the ID of the category:");
        idCategory = sc.nextInt();
    }

    @Override
    public BookCategory read() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM BookCategory WHERE idBook = ? AND idCategory = ?");
        preparedStatement.setInt(1, idBook);
        preparedStatement.setInt(2, idCategory);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return new BookCategory(resultSet.getInt("idBook"),
                                    resultSet.getInt("idCategory"));
        }
        return null;
    }

    @Override
    public void inputUpdate() {
        System.out.println("There is no attribute you can update for this object.");
    }

    @Override
    public void update() throws SQLException {
        // we don't have anything to update
    }

    @Override
    public void inputDelete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the book:");
        idBook = sc.nextInt();
        System.out.println("\nType the ID of the category:");
        idCategory = sc.nextInt();
    }

    @Override
    public void delete() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("DELETE FROM BookCategory WHERE idBook = ? AND idCategory = ?");
        preparedStatement.setInt(1, idBook);
        preparedStatement.setInt(2, idCategory);

        preparedStatement.executeUpdate();
    }

    @Override
    public List<BookCategory> showAll() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM BookCategory");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<BookCategory> bookCategories = new ArrayList<>();
        while(resultSet.next()) {
            bookCategories.add(new BookCategory(resultSet.getInt("idBook"),
                                                resultSet.getInt("idCategory")));
        }
        return bookCategories;
    }
}