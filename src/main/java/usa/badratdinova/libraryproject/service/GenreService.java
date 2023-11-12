package usa.badratdinova.libraryproject.service;

import usa.badratdinova.libraryproject.dto.GenreDto;
import usa.badratdinova.libraryproject.dto.GenreResponseDto;

public interface GenreService {
    GenreDto getAllBooksAndAuthorById(Long id);

    GenreResponseDto getGenreByName(String name);
}
