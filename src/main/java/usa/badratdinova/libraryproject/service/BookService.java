package usa.badratdinova.libraryproject.service;

import usa.badratdinova.libraryproject.dto.create.BookCreateDto;
import usa.badratdinova.libraryproject.dto.BookDto;
import usa.badratdinova.libraryproject.dto.BookDtoNameGenre;
import usa.badratdinova.libraryproject.dto.update.BookUpdateDto;

import java.util.List;

public interface BookService {
    BookDtoNameGenre getByName(String name);

    BookDtoNameGenre getByNameBySql(String name);

    BookDtoNameGenre getByNameByCriteria(String name);

    BookDto createBook(BookCreateDto bookCreateDto) throws Exception;

    BookDto updateBook(BookUpdateDto bookUpdateDto);

    void deleteBook(Long id);

    List<BookDto> getAllBooks();
}
