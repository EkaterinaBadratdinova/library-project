package usa.badratdinova.libraryproject.service;

import usa.badratdinova.libraryproject.dto.AuthorDto;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);
}
