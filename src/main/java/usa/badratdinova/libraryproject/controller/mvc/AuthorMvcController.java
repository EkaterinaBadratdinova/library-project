package usa.badratdinova.libraryproject.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import usa.badratdinova.libraryproject.service.AuthorService;

@Controller
@RequiredArgsConstructor
public class AuthorMvcController {
    private final AuthorService authorService;

    @GetMapping("/authors")
    String getAuthorsView(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors";
    }
}
