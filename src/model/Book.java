package model;

import exceptions.ISBNException;
import interfaces.CRUD;
import repository.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public final class Book implements CRUD<Book> {
    private int ID;
    private String name;
    private int noChapters;
    private int noPages;
    private String isbn;
    private int productionPrice;
    private int price;
    private String language;
    private int idTranslator;
    public static int lastID = 0;

    public Book(int ID,
                String name,
                int noChapters,
                int noPages,
                String isbn,
                int productionPrice,
                int price,
                String language,
                int idTranslator) {
        this.ID = ID;
        this.name = name;
        this.noChapters = noChapters;
        this.noPages = noPages;
        this.isbn = isbn;
        this.productionPrice = productionPrice;
        this.price = price;
        this.language = language;
        this.idTranslator = idTranslator;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", noChapters=" + noChapters +
                ", noPages=" + noPages +
                ", isbn='" + isbn + '\'' +
                ", productionPrice=" + productionPrice +
                ", price=" + price +
                ", language='" + language + '\'' +
                ", idTranslator=" + idTranslator +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return ID == book.ID && noChapters == book.noChapters && noPages == book.noPages && productionPrice == book.productionPrice && price == book.price && idTranslator == book.idTranslator && Objects.equals(name, book.name) && Objects.equals(isbn, book.isbn) && Objects.equals(language, book.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, noChapters, noPages, isbn, productionPrice, price, language, idTranslator);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getNoChapters() {
        return noChapters;
    }

    public int getNoPages() {
        return noPages;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getProductionPrice() {
        return productionPrice;
    }

    public int getPrice() {
        return price;
    }

    public String getLanguage() {
        return language;
    }

    public int getIdTranslator() {
        return idTranslator;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNoChapters(int noChapters) {
        this.noChapters = noChapters;
    }

    public void setNoPages(int noPages) {
        this.noPages = noPages;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setProductionPrice(int productionPrice) {
        this.productionPrice = productionPrice;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setIdTranslator(int idTranslator) {
        this.idTranslator = idTranslator;
    }

    public void inputCreate() throws ISBNException {
        Scanner sc = new Scanner(System.in);
        inputData(sc);
    }

    public void create() throws SQLException {
        lastID = Integer.max(lastID,ID); // This line should execute only if we create a new object in the database
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("INSERT INTO book VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, ID);
        preparedStatement.setString(2, name);
        preparedStatement.setInt(3, noChapters);
        preparedStatement.setInt(4, noPages);
        preparedStatement.setString(5, isbn);
        preparedStatement.setInt(6, productionPrice);
        preparedStatement.setInt(7, price);
        preparedStatement.setString(8, language);
        preparedStatement.setInt(9, idTranslator);

        preparedStatement.executeUpdate();
    }

    public void inputRead() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the book you want to see the data for:");
        ID = sc.nextInt();
    }

    public Book read() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM book WHERE id = ?");
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return new Book(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("noChapters"),
                            resultSet.getInt("noPages"),
                            resultSet.getString("isbn"),
                            resultSet.getInt("productionPrice"),
                            resultSet.getInt("price"),
                            resultSet.getString("language"),
                            resultSet.getInt("idTranslator"));
        }
        return null;
    }

    public void inputUpdate() throws ISBNException {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the book you want to update:");
        ID = sc.nextInt();
        sc.nextLine();
        inputData(sc);
    }

    public void update() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("UPDATE book SET name = ?, noChapters = ?, noPages = ?, isbn = ?, productionPrice = ?, price = ?, language = ?, idTranslator = ? WHERE id = ?");
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, noChapters);
        preparedStatement.setInt(3, noPages);
        preparedStatement.setString(4, isbn);
        preparedStatement.setInt(5, productionPrice);
        preparedStatement.setInt(6, price);
        preparedStatement.setString(7, language);
        preparedStatement.setInt(8, idTranslator);
        preparedStatement.setInt(9, ID);

        preparedStatement.executeUpdate();
    }

    public void inputDelete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nType the ID of the book you want to delete:");
        ID = sc.nextInt();
    }

    public void delete() throws SQLException {
        // delete the entries that mention the book from BookCategory
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("DELETE FROM BookCategory WHERE idBook = ?");
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        // delete the entries that mention the book from BookPublisher
        preparedStatement = Repository.getInstance()
                                      .getConnection()
                                      .prepareStatement("DELETE FROM BookPublisher WHERE idBook = ?");
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        // delete the entries that mention the book from BookAuthorEditor
        preparedStatement = Repository.getInstance()
                                      .getConnection()
                                      .prepareStatement("DELETE FROM BookAuthorEditor WHERE idBook = ?");
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        // delete the book
        preparedStatement = Repository.getInstance()
                                      .getConnection()
                                      .prepareStatement("DELETE FROM book WHERE id = ?");
        preparedStatement.setInt(1, ID);

        preparedStatement.executeUpdate();
    }

    public List<Book> showAll() throws SQLException {
        PreparedStatement preparedStatement = Repository.getInstance()
                                                        .getConnection()
                                                        .prepareStatement("SELECT * FROM book");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Book> books = new ArrayList<>();
        while(resultSet.next()){
            books.add(new Book(resultSet.getInt("id"),
                               resultSet.getString("name"),
                               resultSet.getInt("noChapters"),
                               resultSet.getInt("noPages"),
                               resultSet.getString("isbn"),
                               resultSet.getInt("productionPrice"),
                               resultSet.getInt("price"),
                               resultSet.getString("language"),
                               resultSet.getInt("idTranslator")));
        }
        return books;
    }

    private void inputData(Scanner sc) throws ISBNException {
        System.out.println("\nType the name of the book:");
        name = sc.nextLine();
        System.out.println("\nType the number of chapters:");
        noChapters = sc.nextInt();
        System.out.println("\nType the number of pages:");
        noPages = sc.nextInt();
        sc.nextLine();
        System.out.println("\nType the isbn of the book:");
        isbn = sc.nextLine();
        if(!isbn.matches("(?:ISBN(?:-10)?:?●)?(?=[0-9X]{10}$|(?=(?:[0-9]+[-●]){3})[-●0-9X]{13}$)[0-9]{1,5}[-●]?[0-9]+[-●]?[0-9]+[-●]?[0-9X]")
                && !isbn.matches("(?:ISBN(?:-13)?:?●)?(?=[0-9]{13}$|(?=(?:[0-9]+[-●]){4})[-●0-9]{17}$)97[89][-●]?[0-9]{1,5}[-●]?[0-9]+[-●]?[0-9]+[-●]?[0-9]"))
            throw new ISBNException();
        System.out.println("\nType the production price:");
        productionPrice = sc.nextInt();
        System.out.println("\nType the price of the book:");
        price = sc.nextInt();
        sc.nextLine();
        System.out.println("\nType the language of the book:");
        language = sc.nextLine();
        System.out.println("\nIs the book in its original language? [y/n]");
        String ans = sc.nextLine();
        if (ans.equals("n")) {
            boolean check = false;

            while (!check) {
                System.out.println("\nType the ID of the translator:");
                idTranslator = sc.nextInt();
                try {
                    // we need to check that the ID of the translator exists in the database
                    PreparedStatement preparedStatement = Repository.getInstance()
                                                                    .getConnection()
                                                                    .prepareStatement("SELECT * FROM translator WHERE id = ?");
                    preparedStatement.setInt(1, idTranslator);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next())
                        check = true;
                    if (!check)
                        System.out.println("\nThis ID is not valid!");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            idTranslator = -1;
        }
    }
}