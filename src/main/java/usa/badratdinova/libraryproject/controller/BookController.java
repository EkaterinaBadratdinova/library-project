package usa.badratdinova.libraryproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usa.badratdinova.libraryproject.dto.create.BookCreateDto;
import usa.badratdinova.libraryproject.dto.BookDto;
import usa.badratdinova.libraryproject.dto.BookDtoNameGenre;
import usa.badratdinova.libraryproject.dto.update.BookUpdateDto;
import usa.badratdinova.libraryproject.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/book")
    BookDtoNameGenre getBookByName(@RequestParam("name") String name) {
        return bookService.getByName(name);
    }

    @GetMapping("/book/v2")
    BookDtoNameGenre getBookByNameBySql(@RequestParam("name") String name) {
        return bookService.getByNameBySql(name);
    }

    @GetMapping("/book/v3")
    BookDtoNameGenre getBookByNameByCriteria(@RequestParam("name") String name) {
        return bookService.getByNameByCriteria(name);
    }

    @PostMapping("/book/create")
    BookDto createBook(@RequestBody BookCreateDto bookCreateDto) {
        return bookService.createBook(bookCreateDto);
    }

    @PutMapping("/book/update")
    BookDto updateBook(@RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.updateBook(bookUpdateDto);
    }

    @DeleteMapping("/book/delete/{id}")
    void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }
}
