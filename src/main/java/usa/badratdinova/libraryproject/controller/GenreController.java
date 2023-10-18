package usa.badratdinova.libraryproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import usa.badratdinova.libraryproject.dto.GenreDto;
import usa.badratdinova.libraryproject.service.GenreService;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genre/{id}")
    GenreDto getAllBooksAndAuthorById(@PathVariable("id") Long id) {
        return genreService.getAllBooksAndAuthorById(id);
    }
}
