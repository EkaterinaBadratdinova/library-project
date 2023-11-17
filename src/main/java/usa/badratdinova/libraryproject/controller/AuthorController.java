package usa.badratdinova.libraryproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import usa.badratdinova.libraryproject.dto.create.AuthorCreateDto;
import usa.badratdinova.libraryproject.dto.AuthorDto;
import usa.badratdinova.libraryproject.dto.update.AuthorUpdateDto;
import usa.badratdinova.libraryproject.service.AuthorService;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/author/v1")
    AuthorDto getAuthorByName(@RequestParam("name") String name) {
        return authorService.getAuthorByName(name);
    }

    @GetMapping("/author/v2")
    AuthorDto getAuthorByNameBySql(@RequestParam("name") String name) {
        return authorService.getAuthorByNameBySql(name);
    }

    @GetMapping("/author/v3")
    AuthorDto getAuthorByNameByCriteria(@RequestParam("name") String name) {
        return authorService.getAuthorByNameByCriteria(name);
    }

    @PostMapping("/author/create")
    AuthorDto createAuthor(@RequestBody AuthorCreateDto authorCreateDto) {
        return authorService.createAuthor(authorCreateDto);
    }

    @PutMapping("/author/update")
    AuthorDto updateAuthor(@RequestBody AuthorUpdateDto authorUpdateDto) {
        return authorService.updateAuthor(authorUpdateDto);
    }

    @DeleteMapping("/author/delete/{id}")
    void updateAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
    }
}
