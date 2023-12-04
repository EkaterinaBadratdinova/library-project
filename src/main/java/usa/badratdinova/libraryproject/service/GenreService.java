package usa.badratdinova.libraryproject.service;

import usa.badratdinova.libraryproject.dto.GenreDto;
import usa.badratdinova.libraryproject.dto.GenreResponseDto;
import usa.badratdinova.libraryproject.dto.create.GenreCreateDto;

public interface GenreService {
    GenreDto getBooksAndAuthorsByGenreId(Long id);

    GenreResponseDto getGenreByName(String name);

    GenreResponseDto createGenre(GenreCreateDto genreCreateDto) throws Exception;
}
