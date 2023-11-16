package usa.badratdinova.libraryproject.service;

import usa.badratdinova.libraryproject.dto.create.BookCreateDto;
import usa.badratdinova.libraryproject.dto.BookDto;
import usa.badratdinova.libraryproject.dto.BookDto2;
import usa.badratdinova.libraryproject.dto.update.BookUpdateDto;

import java.util.List;

public interface BookService {
    BookDto2 getByNameV1(String name);

    BookDto2 getByNameV2(String name);

    BookDto2 getByNameV3(String name);

    BookDto createBook(BookCreateDto bookCreateDto);

    BookDto updateBook(BookUpdateDto bookUpdateDto);

    void deleteBook(Long id);

    List<BookDto> getAllBooks();
}
