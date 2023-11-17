package usa.badratdinova.libraryproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import usa.badratdinova.libraryproject.dto.BookDtoNameGenre;
import usa.badratdinova.libraryproject.service.BookService;

@Controller
@RequiredArgsConstructor
public class BookMvcController {

    private final BookService bookService;

    @GetMapping("/books")
    String getBooksView(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/books/book")
    String getBookByNameView(Model model, @RequestParam("name") String name) {
        model.addAttribute("book", bookService.getByName(name));
        return "book";
    }
}
