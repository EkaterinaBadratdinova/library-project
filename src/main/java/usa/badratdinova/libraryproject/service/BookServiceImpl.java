package usa.badratdinova.libraryproject.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Override
    public BookDtoNameGenre getByName(String name) {
        log.info("Try to find the book by name {}", name);
        Book book = bookRepository.findBookByName(name).orElseThrow();
        BookDtoNameGenre bookDtoNameGenre = convertToDto(book);
        log.info("The found book: {}", bookDtoNameGenre.toString());
        return bookDtoNameGenre;
    }

    @Override
    public BookDtoNameGenre getByNameBySql(String name) {
        log.info("Try to find the book by name {}", name);
        Book book = bookRepository.findBookByNameBySql(name).orElseThrow();
        BookDtoNameGenre bookDtoNameGenre = convertToDto(book);
        log.info("The found book: {}", bookDtoNameGenre.toString());
        return bookDtoNameGenre;
    }

    @Override
    public BookDtoNameGenre getByNameByCriteria(String name) {
        log.info("Try to find the book by name {}", name);
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
        BookDtoNameGenre bookDtoNameGenre = convertToDto(book);
        log.info("The found book: {}", bookDtoNameGenre.toString());
        return bookDtoNameGenre;
    }

    @Override
    public List<BookDto> getAllBooks() {
        log.info("Try to find all books");
        List<Book> books = bookRepository.findAll();
        log.info("All found books: {}", books.toString());
        return books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) throws Exception {
        log.info("Try to create the book: {}", bookCreateDto.toString());
        Optional<Book> existingBook = bookRepository.findBookByName(bookCreateDto.getName());
        if (existingBook.isPresent()) {
            log.error("The book already exists");
            throw new Exception("The book already exists");
        } else {
            Book book = bookRepository.save(convertDtoToEntity(bookCreateDto));
            BookDto bookDto = convertEntityToDto(book);
            log.info("The book {} was created", bookDto.toString());
            return bookDto;
        }
    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        log.info("Try to update the book that has the next fields: {}", bookUpdateDto.toString());
        Optional<Book> optionalBook = bookRepository.findById(bookUpdateDto.getId());
        if (optionalBook.isEmpty()) {
            log.error("There is no book with such id. To update the book firstly create it.");
            throw new NoSuchElementException("There is no book with such id. To update the book firstly create it.");
        }
        Book book = optionalBook.get();
        Optional<Genre> genre = genreRepository.findGenreByName(bookUpdateDto.getGenre());
        if (genre.isEmpty()) {
            log.error("There is no genre with such name. Create the genre.");
            throw new NoSuchElementException("There is no genre with such name. Create the genre.");
        }
        Set<Author> authorList = bookUpdateDto.getAuthors()
                .stream()
                .map(author -> {
                            Optional<Author> existingAuthor = authorRepository.findAuthorByNameAndSurname(author.getName(), author.getSurname());
                            if (existingAuthor.isEmpty()) {
                                log.error("There is no author with such name and surname. To update the book create the author.");
                                throw new NoSuchElementException("There is no author with such name and surname. To update a book create the author.");
                            } else {
                                return existingAuthor.get();
                            }
                        }
                ).collect(Collectors.toSet());
        book.setName(bookUpdateDto.getName());
        book.setGenre(genre.get());
        book.setAuthors(authorList);
        Book savedBook = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(savedBook);
        log.info("The book {} was updated", bookDto.toString());
        return bookDto;
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Try to delete the book by id: {}", id);
        bookRepository.deleteById(id);
        log.info("The book with id: {} was deleted", id);
    }

    private BookDtoNameGenre convertToDto(Book book) {
        List<AuthorDtoNameSurname> authors = book.getAuthors()
                .stream()
                .map(author -> AuthorDtoNameSurname.builder()
                        .name(author.getName())
                        .surname(author.getSurname())
                        .build())
                .toList();
        return BookDtoNameGenre.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }

    private BookDto convertEntityToDto(Book book) {
        List<AuthorDtoNameSurname> authors = null;
        if (book.getAuthors() != null) {
            authors = book.getAuthors()
                    .stream()
                    .map(author -> AuthorDtoNameSurname.builder()
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
        Optional<Genre> genre = genreRepository.findGenreByName(bookCreateDto.getGenre());
        if (genre.isEmpty()) {
            throw new NoSuchElementException("There is no genre with such name. Create the genre.");
        }
        Set<Author> authorList = bookCreateDto.getAuthors()
                .stream()
                .map(author -> {
                    Optional<Author> existingAuthor = authorRepository.findAuthorByNameAndSurname(author.getName(), author.getSurname());
                    if (existingAuthor.isEmpty()) {
                        throw new NoSuchElementException("There is no author with such name and surname. Create the author.");
                    } else {
                        return existingAuthor.get();
                    }
                }
                ).collect(Collectors.toSet());
        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre.get())
                .authors(authorList)
                .build();
    }
}
