package model;

import exceptions.ISBNException;
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

public final class BookAuthorEditor implements CRUD<BookAuthorEditor> {
    private int idBook;
    private int idAuthor;
    private int idEditor;
    private int price;

    public BookAuthorEditor(int idBook, int idAuthor, int idEditor, int price) {
        this.idBook = idBook;
        this.idAuthor = idAuthor;
        this.idEditor = idEditor;
        this.price = price;
    }

    @Override
    public String toString() {
        return "BookAuthorEditor{" +
                "idBook=" + idBook +
                ", idAuthor=" + idAuthor +
                ", idEditor=" + idEditor +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAuthorEditor that = (BookAuthorEditor) o;
        return idBook == that.idBook && idAuthor == that.idAuthor && idEditor == that.idEditor && price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBook, idAuthor, idEditor, price);
    }

    public List<Integer> getID() {
        List<Integer> ids = new ArrayList<>();
        ids.add(idBook);
        ids.add(idAuthor);
        ids.add(idEditor);
        return ids;
    }

    public int getIdBook() {
        return idBook;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public int getIdEditor() {
        return idEditor;
    }

    public int getPrice() {
        return price;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public void setIdEditor(int idEditor) {
        this.idEditor = idEditor;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public void inputCreate() throws PrimaryKeyException {
        Scanner sc = new Scanner(System.in);
        boolean check = false;
        while(!check) {
            System.out.println("\nType the ID of the book:");
            idBook = sc.nextInt();
            System.out.println("\nType the ID of the author:");
            idAuthor = sc.nextInt();
            System.out.println("\nType the ID of the editor:");
            idEditor = sc.nextInt();
            System.out.println("\nType the price payed by the author to the editor:");
            price = sc.nextInt();
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
                                              .prepareStatement("SELECT * FROM author WHERE id = ? ");
                preparedStatement.setInt(1, idAuthor);
                resultSet = preparedStatement.executeQuery();
                if(!resultSet.next()) {
                    System.out.println("This author does not exist.");
                    continue;
                }
                preparedStatement = Repository.getInstance()
                                              .getConnection()
                                              .prepareStatement("SELECT * FROM editor WHERE id = ? ");
                preparedStatement.setInt(1, idEditor);
                resultSet = preparedStatement.executeQuery();
                if(!resultSet.next()) {
                    System.out.println("This editor does not exist.");
                    continue;
                }
                preparedStatement = Repository.getInstance()
                                              .getConnection()
                                              .prepareStatement("SELECT * FROM BookAuthorEditor WHERE idBook = ? AND idAuthor = ? AND idEditor = ?");
                preparedStatement.setInt(1, idBook);
                preparedStatement.setInt(2, idAuthor);
                preparedStatement.setInt(3, idEditor);
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
                                                        .prepareStatement("INSERT INTO BookAuthorEditor VALUES (?, ?, ?, ?)");
        preparedStatement.setInt(1, idBook);
        preparedStatement.setInt(2, idAuthor);
        preparedStatement.setInt(3, idEditor);
        preparedStatement.setInt(4, price);

        preparedStatement.executeUpdate();
    }

    @Override
    public void inputRead() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the book:");
        idBook = sc.nextInt();
        System.out.println("\nType the ID of the author:");
        idAuthor = sc.nextInt();
        System.out.println("\nType the ID of the editor:");
        idEditor = sc.nextInt();
    }

    @Override
    public BookAuthorEditor read() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM BookAuthorEditor WHERE idBook = ? AND idAuthor = ? AND idEditor = ?");
        preparedStatement.setInt(1, idBook);
        preparedStatement.setInt(2, idAuthor);
        preparedStatement.setInt(3, idEditor);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return new BookAuthorEditor(resultSet.getInt("idBook"),
                                        resultSet.getInt("idAuthor"),
                                        resultSet.getInt("idEditor"),
                                        resultSet.getInt("price"));
        }
        return null;
    }

    @Override
    public void inputUpdate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the book:");
        idBook = sc.nextInt();
        System.out.println("\nType the ID of the author:");
        idAuthor = sc.nextInt();
        System.out.println("\nType the ID of the editor:");
        idEditor = sc.nextInt();
        System.out.println("\nType the price payed by the author to the editor:");
        price = sc.nextInt();
    }

    @Override
    public void update() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("UPDATE BookAuthorEditor SET price = ? WHERE idBook = ? AND idAuthor = ? AND idEditor = ?");
        preparedStatement.setInt(1, price);
        preparedStatement.setInt(2, idBook);
        preparedStatement.setInt(3, idAuthor);
        preparedStatement.setInt(4, idEditor);

        preparedStatement.executeUpdate();
    }

    @Override
    public void inputDelete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the book:");
        idBook = sc.nextInt();
        System.out.println("\nType the ID of the author:");
        idAuthor = sc.nextInt();
        System.out.println("\nType the ID of the editor:");
        idEditor = sc.nextInt();
    }

    @Override
    public void delete() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("DELETE FROM BookAuthorEditor WHERE idBook = ? AND idAuthor = ? AND idEditor = ?");
        preparedStatement.setInt(1, idBook);
        preparedStatement.setInt(2, idAuthor);
        preparedStatement.setInt(3, idEditor);

        preparedStatement.executeUpdate();
    }

    @Override
    public List<BookAuthorEditor> showAll() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM BookAuthorEditor");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<BookAuthorEditor> bookAuthorEditors = new ArrayList<>();
        while(resultSet.next()) {
            bookAuthorEditors.add(new BookAuthorEditor(resultSet.getInt("idBook"),
                                                       resultSet.getInt("idAuthor"),
                                                       resultSet.getInt("idEditor"),
                                                       resultSet.getInt("price")));
        }
        return bookAuthorEditors;
    }
}