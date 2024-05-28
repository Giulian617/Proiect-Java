package service;

import exceptions.ISBNException;
import exceptions.PrimaryKeyException;
import interfaces.CRUD;
import model.Book;
import model.Translator;
import model.Category;
import model.Publisher;
import model.Author;
import model.Editor;
import model.BookCategory;
import model.BookPublisher;
import model.BookAuthorEditor;
import repository.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public final class MainMenuService {
    private static MainMenuService INSTANCE = null;
    private final AuditService auditService;
    private Map<String, String> log;
    private boolean stop;

    private MainMenuService() {
        auditService = AuditService.getInstance();
        log = new HashMap<>();
        stop = false;
    }

    public static MainMenuService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new MainMenuService();
        }
        return INSTANCE;
    }

    public void mainMenu() {
        Repository repository = Repository.getInstance();
        while(!stop){
            readCommand();
            System.out.println("\n\n\n");
        }
        repository.closeConnection();
    }

    private void readCommand() {
        Scanner sc = new Scanner(System.in);

        System.out.println("[1] CRUD for book");
        System.out.println("[2] CRUD for translator");
        System.out.println("[3] CRUD for category");
        System.out.println("[4] CRUD for publisher");
        System.out.println("[5] CRUD for author");
        System.out.println("[6] CRUD for editor");
        System.out.println("[7] CRUD for book_category");
        System.out.println("[8] CRUD for book_publisher");
        System.out.println("[9] CRUD for book_author_editor");
        System.out.println("[10] Special queries");
        System.out.println("[0] Exit");

        int choice = sc.nextInt();
        while(choice < 0 || choice > 10) {
            System.out.println("Please enter a valid number.");
            choice = sc.nextInt();
        }
        if(choice == 0) {
            log.put("object", "exit");
            log.put("command", "exit");
            auditService.writeLog(log);
            stop = true;
            return;
        }

        System.out.println("\n\n\n");
        String object_type = null;

        if(choice == 1) {
            object_type = "Book";
            System.out.println("CRUD for book");
        }
        else if(choice == 2) {
            object_type = "Translator";
            System.out.println("CRUD for translator");
        }
        else if(choice == 3) {
            object_type = "Category";
            System.out.println("CRUD for category");
        }
        else if(choice == 4) {
            object_type = "Publisher";
            System.out.println("CRUD for publisher");
        }
        else if(choice == 5) {
            object_type = "Author";
            System.out.println("CRUD for author");
        }
        else if(choice == 6) {
            object_type = "Editor";
            System.out.println("CRUD for editor");
        }
        else if(choice == 7) {
            object_type = "BookCategory";
            System.out.println("CRUD for book_category");
        }
        else if(choice == 8) {
            object_type = "BookPublisher";
            System.out.println("CRUD for book_publisher");
        }
        else if(choice == 9) {
            object_type = "BookAuthorEditor";
            System.out.println("CRUD for book_author_editor");
        }
        else {
            object_type = "Special";
            System.out.println("Special queries");
        }

        if(choice != 10) {
            System.out.println("[1] Create");
            System.out.println("[2] Read");
            System.out.println("[3] Update");
            System.out.println("[4] Delete");
            System.out.println("[5] Show all");
            System.out.println("[0] Exit");

            int command_type = sc.nextInt();
            while (command_type < 0 || command_type > 5) {
                System.out.println("Please enter a valid number.");
                command_type = sc.nextInt();
            }
            if(command_type == 0) {
                log.put("object", "exit");
                log.put("command", "exit");
                auditService.writeLog(log);
                stop = true;
                return;
            }

            System.out.println("\n\n\n");

            if(command_type == 1)
                processCommand(object_type, "create");
            else if(command_type == 2)
                processCommand(object_type, "read");
            else if(command_type == 3)
                processCommand(object_type, "update");
            else if(command_type == 4)
                processCommand(object_type, "delete");
            else
                processCommand(object_type, "show all");
        }
        else {
            // special queries syntax
            System.out.println("[1] Show all books sorted alphabetically");
            System.out.println("[2] Show all categories for a specific book in alphabetical order");
            System.out.println("[3] Show all authors for a specific book ordered by their debut date");
            System.out.println("[4] Show all books and all their categories for a specific publisher ordered alphabetically by book and then in reverse order by category");
            System.out.println("[0] Exit");
            int command_type = sc.nextInt();
            while (command_type < 0 || command_type > 4) {
                System.out.println("Please enter a valid number.");
                command_type = sc.nextInt();
            }
            if(command_type == 0) {
                log.put("object", "exit");
                log.put("command", "exit");
                auditService.writeLog(log);
                stop = true;
                return;
            }

            System.out.println("\n\n\n");
            if(command_type == 1)
                processCommand(object_type, "AllBooksAlphabetically");
            else if(command_type == 2)
                processCommand(object_type, "AllCategoriesSpecificBookAlphabetically");
            else if(command_type == 3)
                processCommand(object_type, "AllAuthorsSpecificBookDebutDate");
            else
                processCommand(object_type, "AllBooksAndCategoriesSpecificPublisher");
        }
    }

    private void processCommand(String object_type, String command) {
        log.put("object", object_type);
        log.put("command", command);
        auditService.writeLog(log);

        if(object_type.equals("Book")) {
            Book book = new Book(Book.lastID + 1, null, 0, 0, null, 0, 0, null, 0);
            executeCommand(book, command);
        }
        else if(object_type.equals("Translator")) {
            Translator translator = new Translator(Translator.lastID + 1,null,null);
            executeCommand(translator, command);
        }
        else if(object_type.equals("Category")) {
            Category category = new Category(Category.lastID + 1, null);
            executeCommand(category, command);
        }
        else if(object_type.equals("Publisher")) {
            Publisher publisher = new Publisher(Publisher.lastID + 1, null, null);
            executeCommand(publisher, command);
        }
        else if(object_type.equals("Author")) {
            Author author = new Author(Author.lastID + 1, null, null, null);
            executeCommand(author, command);
        }
        else if(object_type.equals("Editor")) {
            Editor editor = new Editor(Editor.lastID + 1, null, null, null);
            executeCommand(editor, command);
        }
        else if(object_type.equals("BookCategory")) {
            BookCategory bookCategory = new BookCategory(0, 0);
            executeCommand(bookCategory, command);
        }
        else if(object_type.equals("BookPublisher")) {
            BookPublisher bookPublisher = new BookPublisher(0,0,0);
            executeCommand(bookPublisher, command);
        }
        else if(object_type.equals("BookAuthorEditor")) {
            BookAuthorEditor bookAuthorEditor = new BookAuthorEditor(0,0,0,0);
            executeCommand(bookAuthorEditor, command);
        }
        else if(object_type.equals("Special")) {
            if(command.equals("AllBooksAlphabetically"))
                AllBooksAlphabetically();
            else if(command.equals("AllCategoriesSpecificBookAlphabetically"))
                AllCategoriesSpecificBookAlphabetically();
            else if(command.equals("AllAuthorsSpecificBookDebutDate"))
                AllAuthorsSpecificBookDebutDate();
            else
                AllBooksAndCategoriesSpecificPublisher();
        }
    }
    private <T> void executeCommand(T object, String command) {
        try {
            if (command.equals("create")) {
                ((CRUD) object).inputCreate();
                ((CRUD) object).create();
            }
            else if (command.equals("read")) {
                ((CRUD) object).inputRead();
                System.out.println(((CRUD) object).read());
            }
            else if (command.equals("update")) {
                ((CRUD) object).inputUpdate();
                ((CRUD) object).update();
            }
            else if (command.equals("delete")) {
                ((CRUD) object).inputDelete();
                ((CRUD) object).delete();
            }
            else { // command.equals("show all")
                List<T> items = ((CRUD) object).showAll();
                for (T item : items)
                    System.out.println(item);
            }
        }
        catch(SQLException | ISBNException | PrimaryKeyException e) {
            e.printStackTrace();
        }
    }

    private void AllBooksAlphabetically() {
        try {
            Book book = new Book(Book.lastID + 1, null, 0, 0, null, 0, 0, null, 0);
            List<Book> books = book.showAll();
            Comparator<Book> bookComparator = (b1, b2) -> b1.getName().compareTo(b2.getName());
            books.sort(bookComparator);
            for(Book book1 : books)
                System.out.println(book1);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void AllCategoriesSpecificBookAlphabetically() {
        try {
            int idBook;
            Scanner sc = new Scanner(System.in);
            System.out.println("\nType the ID of the book you wish to see the categories for:");
            idBook = sc.nextInt();

            PreparedStatement preparedStatement = Repository.getInstance()
                                                            .getConnection()
                                                            .prepareStatement("SELECT * FROM category JOIN BookCategory ON(category.id = BookCategory.idCategory) WHERE idBook = ?");
            preparedStatement.setInt(1, idBook);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Category> categories = new ArrayList<>();
            while(resultSet.next()) {
                categories.add(new Category(resultSet.getInt("idCategory"),
                                            resultSet.getString("name")));
            }
            categories.sort((Category c1, Category c2) -> c1.getName().compareTo(c2.getName()));
            for(Category category : categories)
                System.out.println(category);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void AllAuthorsSpecificBookDebutDate() {
        try {
            int idBook;
            Scanner sc = new Scanner(System.in);
            System.out.println("\nType the ID of the book you wish to see the authors for:");
            idBook = sc.nextInt();

            PreparedStatement preparedStatement = Repository.getInstance()
                                                            .getConnection()
                                                            .prepareStatement("SELECT * FROM author JOIN BookAuthorEditor ON(author.id = BookAuthorEditor.idAuthor) WHERE idBook = ?");
            preparedStatement.setInt(1, idBook);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Author> authors = new ArrayList<>();
            while(resultSet.next()) {
                authors.add(new Author(resultSet.getInt("id"),
                                       resultSet.getString("lastName"),
                                       resultSet.getString("firstName"),
                                       resultSet.getDate("debutDate").toString()));
            }

            Comparator<Author> comparator = new Comparator<Author>() {
                @Override
                public int compare(Author o1, Author o2) {
                    if(o1.getDebutDate().compareTo(o2.getDebutDate()) == 0) {
                        if(o1.getID() < o2.getID())
                            return -1;
                        else if(o1.getID() == o2.getID())
                            return 0;
                        else
                            return 1;
                    }
                    else if(o1.getDebutDate().compareTo(o2.getDebutDate()) < 0)
                        return -1;
                    else
                        return 1;
                }
            };
            authors.sort(comparator);
            for(Author author : authors)
                System.out.println(author);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void AllBooksAndCategoriesSpecificPublisher() {
        try {
            int idPublisher;
            Scanner sc = new Scanner(System.in);
            System.out.println("\nType the ID of the publisher you wish to see the books and categories for:");
            idPublisher = sc.nextInt();

            PreparedStatement preparedStatement = Repository.getInstance()
                                                            .getConnection()
                                                            .prepareStatement("SELECT * FROM book JOIN BookPublisher ON(book.id = BookPublisher.idBook) WHERE idPublisher = ?");
            preparedStatement.setInt(1, idPublisher);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> books = new ArrayList<>();
            while(resultSet.next()) {
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
            books.sort((Book b1, Book b2) -> b1.getName().compareTo(b2.getName()));
            for(Book book : books) {
                System.out.println(book);
                System.out.println("This book is placed in the following categories:");

                PreparedStatement preparedStatement1 = Repository.getInstance()
                                                                 .getConnection()
                                                                 .prepareStatement("SELECT * FROM category JOIN BookCategory ON(category.id = BookCategory.idCategory) WHERE idBook = ?");
                preparedStatement1.setInt(1, book.getID());
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                List<Category> categories = new ArrayList<>();
                while(resultSet1.next()) {
                    categories.add(new Category(resultSet1.getInt("id"),
                                                resultSet1.getString("name")));
                }
                categories.sort((Category c1, Category c2) -> c2.getName().compareTo(c1.getName()));
                for(Category category : categories)
                    System.out.println("    " + category);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}