package service.book;

import domain.Book;
import exception.BadRequestException;
import exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> findById(int bookId);

    void createBook(Book book);

    void updateBookStock(int bookId, int newStock) throws NotFoundException, BadRequestException;

    List<Book> getAllBooks();

    void exportAllBooks();
}
