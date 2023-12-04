package usa.badratdinova.libraryproject.controller.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usa.badratdinova.libraryproject.dto.GenreDto;
import usa.badratdinova.libraryproject.dto.GenreResponseDto;
import usa.badratdinova.libraryproject.dto.create.GenreCreateDto;
import usa.badratdinova.libraryproject.service.GenreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
@SecurityRequirement(name = "library-users")
public class GenreRestController {
    private final GenreService genreService;

    @GetMapping("/{id}")
    GenreDto getBooksAndAuthorsByGenreId(@PathVariable("id") Long id) {
        return genreService.getBooksAndAuthorsByGenreId(id);
    }

    @PostMapping("/create")
    GenreResponseDto createGenre(@RequestBody @Valid GenreCreateDto genreCreateDto) throws Exception {
        return genreService.createGenre(genreCreateDto);
    }
}
