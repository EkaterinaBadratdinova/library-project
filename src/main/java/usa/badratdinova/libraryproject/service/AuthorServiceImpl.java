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
import usa.badratdinova.libraryproject.dto.create.AuthorCreateDto;
import usa.badratdinova.libraryproject.dto.update.AuthorUpdateDto;
import usa.badratdinova.libraryproject.model.Author;
import usa.badratdinova.libraryproject.repository.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorById(Long id) {
        log.info("Try to find the author by id: {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            AuthorDto authorDto = convertToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDto getAuthorByName(String name) {
        log.info("Try to find the author by name: {}", name);
        Optional<Author> optionalAuthor = authorRepository.findAuthorByName(name);
        if (optionalAuthor.isPresent()) {
            AuthorDto authorDto = convertToDto(optionalAuthor.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDto getAuthorByNameBySql(String name) {
        log.info("Try to find the author by name: {}", name);
        Optional<Author> optionalAuthor = authorRepository.findAuthorByNameBySql(name);
        if (optionalAuthor.isPresent()) {
            AuthorDto authorDto = convertToDto(optionalAuthor.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDto getAuthorByNameByCriteria(String name) {
        Specification<Author> specification = Specification.where(
                new Specification<Author>() {
                    @Override
                    public Predicate toPredicate(Root<Author> root,
                                                 CriteriaQuery<?> query,
                                                 CriteriaBuilder cb) {
                        return cb.equal(root.get("name"), name);
                    }
                }
        );
        log.info("Try to find the author by name: {}", name);
        Optional<Author> optionalAuthor = authorRepository.findOne(specification);
        if (optionalAuthor.isPresent()) {
            AuthorDto authorDto = convertToDto(optionalAuthor.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) throws Exception {
        log.info("Try to create the author: {}", authorCreateDto.toString());
        Optional<Author> optionalAuthor = authorRepository.findAuthorByNameAndSurname(authorCreateDto.getName(), authorCreateDto.getSurname());
        if (optionalAuthor.isPresent()) {
            log.error("The author already exists");
            throw new Exception("The author already exists");
        } else {
            Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
            AuthorDto authorDto = convertEntityToDto(author);
            log.info("The author {} was created", authorDto.toString());
            return authorDto;
        }
    }

    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        log.info("Try to update the author that has the next fields: {}", authorUpdateDto.toString());
        Optional<Author> optionalAuthor = authorRepository.findById(authorUpdateDto.getId());
        if (optionalAuthor.isEmpty()) {
            log.error("There is no genre with such name. Create the genre.");
            throw new NoSuchElementException("There is no genre with such name. Create the genre.");
        }
        Author author = optionalAuthor.get();
        author.setName(authorUpdateDto.getName());
        author.setSurname(authorUpdateDto.getSurname());
        Author savedAuthor = authorRepository.save(author);
        AuthorDto authorDto = convertEntityToDto(savedAuthor);
        log.info("The author {} was updated", authorDto.toString());
        return authorDto;
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Try to delete the author by id: {}", id);
        authorRepository.deleteById(id);
        log.info("The author with id: {} was deleted", id);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        log.info("Try to find all authors");
        List<Author> authors = authorRepository.findAll();
        log.info("All found authors: {}", authors.toString());
        return authors.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private AuthorDto convertToDto(Author author) {
        List<BookDtoNameGenre> bookDtoList = author.getBooks()
                .stream()
                .map(book -> BookDtoNameGenre.builder()
                        .genre(book.getGenre().getName())
                        .name(book.getName())
                        .id(book.getId())
                        .build()
                ).toList();
        return AuthorDto.builder()
                .books(bookDtoList)
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }

    private Author convertDtoToEntity(AuthorCreateDto authorCreateDto) {
        return Author.builder()
                .name(authorCreateDto.getName())
                .surname(authorCreateDto.getSurname())
                .build();
    }

    private AuthorDto convertEntityToDto(Author author) {
        List<BookDtoNameGenre> bookDtoNameGenre = null;
        if (author.getBooks() != null) {
            bookDtoNameGenre = author.getBooks()
                    .stream()
                    .map(book -> BookDtoNameGenre.builder()
                            .genre(book.getGenre().getName())
                            .name(book.getName())
                            .id(book.getId())
                            .build())
                    .toList();
        }
        AuthorDto authorDto = AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoNameGenre)
                .build();
        return authorDto;
    }
}
