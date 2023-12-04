package usa.badratdinova.libraryproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import usa.badratdinova.libraryproject.dto.AuthorDtoNameSurname;
import usa.badratdinova.libraryproject.dto.BookDto;
import usa.badratdinova.libraryproject.dto.GenreDto;
import usa.badratdinova.libraryproject.dto.GenreResponseDto;
import usa.badratdinova.libraryproject.dto.create.GenreCreateDto;
import usa.badratdinova.libraryproject.model.Genre;
import usa.badratdinova.libraryproject.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public GenreDto getBooksAndAuthorsByGenreId(Long id) {
        log.info("Try to find all books with authors by genre id: {}", id);
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        if (optionalGenre.isEmpty()) {
            log.error("Genre with id {} not found", id);
            throw new NoSuchElementException("No value present");
        } else {
            Genre genre = optionalGenre.get();
            GenreDto genreDto = convertToDto(genre);
            log.info("Found books with authors by genre id: {} : {}", id, genreDto.toString());
            return genreDto;
        }
    }

    @Override
    public GenreResponseDto getGenreByName(String name) {
        log.info("Try to find genre by name: {}", name);
        Optional<Genre> optionalGenre = genreRepository.findGenreByName(name);
        if (optionalGenre.isEmpty()) {
            log.error("Genre {} not found", name);
            throw new NoSuchElementException("No value present");
        } else {
            Genre genre = optionalGenre.get();
            GenreResponseDto genreResponseDto = convertEntityToDto(genre);
            log.info("Genre {} was found: {}", name, genreResponseDto.toString());
            return genreResponseDto;
        }
    }

    @Override
    public GenreResponseDto createGenre(GenreCreateDto genreCreateDto) throws Exception {
        log.info("Try to find the genre by name {}", genreCreateDto.getName());
        Optional<Genre> optionalGenre = genreRepository.findGenreByName(genreCreateDto.getName());
        if (optionalGenre.isEmpty()) {
            Genre genre = genreRepository.save(convertDtoToEntity(genreCreateDto));
            GenreResponseDto genreResponseDto = convertEntityToDto(genre);
            log.info("Genre: {}", genreResponseDto.toString());
            return genreResponseDto;
        } else {
            log.error("Genre with name {} not found", genreCreateDto.getName());
            throw new Exception("This genre already exists");
        }
    }

    private Genre convertDtoToEntity(GenreCreateDto genreCreateDto) {
        return Genre.builder()
                .name(genreCreateDto.getName())
                .build();
    }

    private GenreResponseDto convertEntityToDto(Genre genre) {
        return GenreResponseDto.builder()
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
