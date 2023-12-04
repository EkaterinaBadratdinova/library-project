package usa.badratdinova.libraryproject.controller.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usa.badratdinova.libraryproject.dto.create.BookCreateDto;
import usa.badratdinova.libraryproject.dto.BookDto;
import usa.badratdinova.libraryproject.dto.BookDtoNameGenre;
import usa.badratdinova.libraryproject.dto.update.BookUpdateDto;
import usa.badratdinova.libraryproject.service.BookService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@SecurityRequirement(name = "library-users")
public class BookRestController {
    private final BookService bookService;

    @GetMapping("/name/v1")
    BookDtoNameGenre getBookByName(@RequestParam("name") String name) {
        return bookService.getByName(name);
    }

    @GetMapping("/name/v2")
    BookDtoNameGenre getBookByNameBySql(@RequestParam("name") String name) {
        return bookService.getByNameBySql(name);
    }

    @GetMapping("/name/v3")
    BookDtoNameGenre getBookByNameByCriteria(@RequestParam("name") String name) {
        return bookService.getByNameByCriteria(name);
    }

    @PostMapping("/create")
    BookDto createBook(@RequestBody @Valid BookCreateDto bookCreateDto) throws Exception {
        return bookService.createBook(bookCreateDto);
    }

    @PutMapping("/update")
    BookDto updateBook(@RequestBody @Valid BookUpdateDto bookUpdateDto) {
        return bookService.updateBook(bookUpdateDto);
    }

    @DeleteMapping("/delete/{id}")
    void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }
}
