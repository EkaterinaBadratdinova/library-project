package usa.badratdinova.libraryproject.service;

import usa.badratdinova.libraryproject.dto.create.AuthorCreateDto;
import usa.badratdinova.libraryproject.dto.AuthorDto;
import usa.badratdinova.libraryproject.dto.update.AuthorUpdateDto;

import java.util.List;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);

    AuthorDto getAuthorByName(String name);

    AuthorDto getAuthorByNameBySql(String name);

    AuthorDto getAuthorByNameByCriteria(String name);

    AuthorDto createAuthor(AuthorCreateDto authorCreateDto) throws Exception;

    AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto);

    void deleteAuthor(Long id);

    List<AuthorDto> getAllAuthors();
}
