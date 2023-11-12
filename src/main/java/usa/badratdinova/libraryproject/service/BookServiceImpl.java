package usa.badratdinova.libraryproject.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import usa.badratdinova.libraryproject.dto.*;
import usa.badratdinova.libraryproject.dto.create.BookCreateDto;
import usa.badratdinova.libraryproject.dto.update.BookUpdateDto;
import usa.badratdinova.libraryproject.model.Author;
import usa.badratdinova.libraryproject.model.Book;
import usa.badratdinova.libraryproject.model.Genre;
import usa.badratdinova.libraryproject.repository.AuthorRepository;
import usa.badratdinova.libraryproject.repository.BookRepository;
import usa.badratdinova.libraryproject.repository.GenreRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Override
    public BookDto2 getByNameV1(String name) {
        Book book = bookRepository.findBookByName(name).orElseThrow();
        return convertToDto(book);
    }

    @Override
    public BookDto2 getByNameV2(String name) {
        Book book = bookRepository.findBookByNameBySql(name).orElseThrow();
        return convertToDto(book);
    }

    @Override
    public BookDto2 getByNameV3(String name) {
        Specification<Book> specification = Specification.where(
                new Specification<Book>() {
                    @Override
                    public Predicate toPredicate(Root<Book> root,
                                                 CriteriaQuery<?> query,
                                                 CriteriaBuilder cb) {
                        return cb.equal(root.get("name"), name);
                    }
                }
        );
        Book book = bookRepository.findOne(specification).orElseThrow();
        return convertToDto(book);
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        Book book = bookRepository.save(convertDtoToEntity(bookCreateDto));
        BookDto bookDto = convertEntityToDto(book);
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow();
        book.setName(bookUpdateDto.getName());
        Genre genre = genreRepository.findGenreByName(bookUpdateDto.getGenre()).orElseThrow();
        book.setGenre(genre);
        Book savedBook = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(savedBook);
        return bookDto;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private BookDto2 convertToDto(Book book) {
        List<AuthorDto2> authors = book.getAuthors()
                .stream()
                .map(author -> AuthorDto2.builder()
                        .name(author.getName())
                        .surname(author.getSurname())
                        .build())
                .toList();
        return BookDto2.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }

    private BookDto convertEntityToDto(Book book) {
        List<AuthorDto2> authors = null;
        if (book.getAuthors() != null) {
            authors = book.getAuthors()
                    .stream()
                    .map(author -> AuthorDto2.builder()
                            .id(author.getId())
                            .name(author.getName())
                            .surname(author.getSurname())
                            .build())
                    .toList();
        }
        return BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .authors(authors)
                .build();
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        Author author = authorRepository.findAuthorByName(bookCreateDto.getAuthorName()).orElseThrow();
        Set<Author> authorList = new HashSet<>();
        authorList.add(author);
        Genre genre = genreRepository.findGenreByName(bookCreateDto.getGenre()).orElseThrow();
        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
                .authors(authorList)
                .build();
    }
}
