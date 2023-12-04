package usa.badratdinova.libraryproject.controller.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usa.badratdinova.libraryproject.dto.create.AuthorCreateDto;
import usa.badratdinova.libraryproject.dto.AuthorDto;
import usa.badratdinova.libraryproject.dto.update.AuthorUpdateDto;
import usa.badratdinova.libraryproject.service.AuthorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
@SecurityRequirement(name = "library-users")
public class AuthorRestController {
    private final AuthorService authorService;

    @GetMapping("/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/name/v1")
    AuthorDto getAuthorByName(@RequestParam("name") String name) {
        return authorService.getAuthorByName(name);
    }

    @GetMapping("/name/v2")
    AuthorDto getAuthorByNameBySql(@RequestParam("name") String name) {
        return authorService.getAuthorByNameBySql(name);
    }

    @GetMapping("/name/v3")
    AuthorDto getAuthorByNameByCriteria(@RequestParam("name") String name) {
        return authorService.getAuthorByNameByCriteria(name);
    }

    @PostMapping("/create")
    AuthorDto createAuthor(@RequestBody @Valid AuthorCreateDto authorCreateDto) throws Exception {
        return authorService.createAuthor(authorCreateDto);
    }

    @PutMapping("/update")
    AuthorDto updateAuthor(@RequestBody @Valid AuthorUpdateDto authorUpdateDto) {
        return authorService.updateAuthor(authorUpdateDto);
    }

    @DeleteMapping("/delete/{id}")
    void updateAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
    }
}
