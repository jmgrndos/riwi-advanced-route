package dao.book;

import domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDAO {

    Optional<Book> findById(int bookId);

    void createBook(Book book);

    Book getBookByIsbn(String isbn);

    void updateBookStock(Book book);

    List<Book> getAllBooks();
}
