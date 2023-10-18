package usa.badratdinova.libraryproject.service;

import usa.badratdinova.libraryproject.dto.GenreDto;

public interface GenreService {
    GenreDto getAllBooksAndAuthorById(Long id);
}
