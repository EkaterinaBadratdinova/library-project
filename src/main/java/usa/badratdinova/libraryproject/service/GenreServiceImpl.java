package usa.badratdinova.libraryproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import usa.badratdinova.libraryproject.dto.AuthorDtoNameSurname;
import usa.badratdinova.libraryproject.dto.BookDto;
import usa.badratdinova.libraryproject.dto.GenreDto;
import usa.badratdinova.libraryproject.dto.GenreResponseDto;
import usa.badratdinova.libraryproject.model.Genre;
import usa.badratdinova.libraryproject.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public GenreDto getAllBooksAndAuthorById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        return convertToDto(genre);
    }

    @Override
    public GenreResponseDto getGenreByName(String name) {
        Genre genre = genreRepository.findGenreByName(name).orElseThrow();
        return convertEntityToDto(genre);
    }

    private GenreResponseDto convertEntityToDto(Genre genre) {
        return GenreResponseDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

    private GenreDto convertToDto(Genre genre) {
        List<BookDto> bookDtoList = genre.getBooks()
                .stream()
                .map(book -> BookDto.builder()
                        .genre(book.getGenre().getName())
                        .name(book.getName())
                        .id(book.getId())
                        .authors(book.getAuthors()
                                .stream()
                                .map(author -> AuthorDtoNameSurname.builder()
                                        .id(author.getId())
                                        .name(author.getName())
                                        .surname(author.getSurname())
                                        .build()).toList())
                        .build()
                ).toList();
        return GenreDto.builder()
                .books(bookDtoList)
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
