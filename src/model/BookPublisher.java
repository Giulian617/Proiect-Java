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

public final class BookPublisher implements CRUD<BookPublisher> {
    private int idBook;
    private int idPublisher;
    private int noCopies;

    public BookPublisher(int idBook, int idPublisher, int noCopies) {
        this.idBook = idBook;
        this.idPublisher = idPublisher;
        this.noCopies = noCopies;
    }

    @Override
    public String toString() {
        return "BookPublisher{" +
                "idBook=" + idBook +
                ", idPublisher=" + idPublisher +
                ", noCopies=" + noCopies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookPublisher that = (BookPublisher) o;
        return idBook == that.idBook && idPublisher == that.idPublisher && noCopies == that.noCopies;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBook, idPublisher, noCopies);
    }

    public Pair<Integer, Integer> getID() {
        return new Pair<>(idBook, idPublisher);
    }

    public int getIdBook() {
        return idBook;
    }

    public int getIdPublisher() {
        return idPublisher;
    }

    public int getNoCopies() {
        return noCopies;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public void setIdPublisher(int idPublisher) {
        this.idPublisher = idPublisher;
    }

    public void setNoCopies(int noCopies) {
        this.noCopies = noCopies;
    }

    @Override
    public void inputCreate() throws PrimaryKeyException {
        Scanner sc = new Scanner(System.in);
        boolean check = false;
        while(!check) {
            System.out.println("\nType the ID of the book:");
            idBook = sc.nextInt();
            System.out.println("\nType the ID of the publisher:");
            idPublisher = sc.nextInt();
            System.out.println("\nType the number of copies:");
            noCopies = sc.nextInt();
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
                                              .prepareStatement("SELECT * FROM publisher WHERE id = ? ");
                preparedStatement.setInt(1, idPublisher);
                resultSet = preparedStatement.executeQuery();
                if(!resultSet.next()) {
                    System.out.println("This publisher does not exist.");
                    continue;
                }
                preparedStatement = Repository.getInstance()
                                              .getConnection()
                                              .prepareStatement("SELECT * FROM BookPublisher WHERE idBook = ? AND idPublisher = ?");
                preparedStatement.setInt(1, idBook);
                preparedStatement.setInt(2, idPublisher);
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
                .prepareStatement("INSERT INTO BookPublisher VALUES (?, ?, ?)");
        preparedStatement.setInt(1, idBook);
        preparedStatement.setInt(2, idPublisher);
        preparedStatement.setInt(3, noCopies);

        preparedStatement.executeUpdate();
    }

    @Override
    public void inputRead() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the book:");
        idBook = sc.nextInt();
        System.out.println("\nType the ID of the publisher:");
        idPublisher = sc.nextInt();
    }

    @Override
    public BookPublisher read() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM BookPublisher WHERE idBook = ? AND idPublisher = ?");
        preparedStatement.setInt(1, idBook);
        preparedStatement.setInt(2, idPublisher);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return new BookPublisher(resultSet.getInt("idBook"),
                                     resultSet.getInt("idPublisher"),
                                     resultSet.getInt("noCopies"));
        }
        return null;
    }

    @Override
    public void inputUpdate() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the book:");
        idBook = sc.nextInt();
        System.out.println("\nType the ID of the publisher:");
        idPublisher = sc.nextInt();
        System.out.println("\nType the number of copies:");
        noCopies = sc.nextInt();
    }

    @Override
    public void update() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("UPDATE BookPublisher SET noCopies = ? WHERE idBook = ? AND idPublisher = ?");
        preparedStatement.setInt(1, noCopies);
        preparedStatement.setInt(2, idBook);
        preparedStatement.setInt(3, idPublisher);

        preparedStatement.executeUpdate();
    }

    @Override
    public void inputDelete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the book:");
        idBook = sc.nextInt();
        System.out.println("\nType the ID of the publisher:");
        idPublisher = sc.nextInt();
    }

    @Override
    public void delete() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("DELETE FROM BookPublisher WHERE idBook = ? AND idPublisher = ?");
        preparedStatement.setInt(1, idBook);
        preparedStatement.setInt(2, idPublisher);

        preparedStatement.executeUpdate();
    }

    @Override
    public List<BookPublisher> showAll() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM BookPublisher");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<BookPublisher> bookPublishers = new ArrayList<>();
        while(resultSet.next()) {
            bookPublishers.add(new BookPublisher(resultSet.getInt("idBook"),
                                                 resultSet.getInt("idPublisher"),
                                                 resultSet.getInt("noCopies")));
        }
        return bookPublishers;
    }
}