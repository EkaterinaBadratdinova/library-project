package usa.badratdinova.libraryproject.service;

import usa.badratdinova.libraryproject.dto.AuthorDto;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);

    AuthorDto getAuthorByNameV1(String name);

    AuthorDto getAuthorByNameV2(String name);

    AuthorDto getAuthorByNameV3(String name);
}
