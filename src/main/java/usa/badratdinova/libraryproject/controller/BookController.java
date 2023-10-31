package usa.badratdinova.libraryproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import usa.badratdinova.libraryproject.dto.BookDto2;
import usa.badratdinova.libraryproject.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/book")
    BookDto2 getBookByName(@RequestParam("name") String name) {
        return bookService.getByNameV1(name);
    }

    @GetMapping("/book/v2")
    BookDto2 getBookByNameV2(@RequestParam("name") String name) {
        return bookService.getByNameV2(name);
    }

    @GetMapping("/book/v3")
    BookDto2 getBookByNameV3(@RequestParam("name") String name) {
        return bookService.getByNameV3(name);
    }
}
