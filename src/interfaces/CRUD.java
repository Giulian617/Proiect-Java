package interfaces;

import exceptions.ISBNException;
import exceptions.PrimaryKeyException;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {
    public void create() throws SQLException;
    public T read() throws SQLException;
    public void update() throws SQLException;
    public void delete() throws SQLException;
    public List<T> showAll() throws SQLException;
    public void inputCreate() throws PrimaryKeyException, ISBNException;
    public void inputRead();
    public void inputUpdate() throws ISBNException;
    public void inputDelete();
}