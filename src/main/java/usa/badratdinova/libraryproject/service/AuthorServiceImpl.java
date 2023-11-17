package usa.badratdinova.libraryproject.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import usa.badratdinova.libraryproject.dto.*;
import usa.badratdinova.libraryproject.dto.create.AuthorCreateDto;
import usa.badratdinova.libraryproject.dto.update.AuthorUpdateDto;
import usa.badratdinova.libraryproject.model.Author;
import usa.badratdinova.libraryproject.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return convertToDto(author);
    }

    @Override
    public AuthorDto getAuthorByName(String name) {
        Author author = authorRepository.findAuthorByName(name).orElseThrow();
        return convertToDto(author);
    }

    @Override
    public AuthorDto getAuthorByNameBySql(String name) {
        Author author = authorRepository.findAuthorByNameBySql(name).orElseThrow();
        return convertToDto(author);
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
        Author author = authorRepository.findOne(specification).orElseThrow();
        return convertToDto(author);
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) {
        Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
        AuthorDto authorDto = convertEntityToDto(author);
        return authorDto;
    }

    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        Author author = authorRepository.findById(authorUpdateDto.getId()).orElseThrow();
        author.setName(authorUpdateDto.getName());
        author.setSurname(authorUpdateDto.getSurname());
        Author savedAuthor = authorRepository.save(author);
        AuthorDto authorDto = convertEntityToDto(savedAuthor);
        return authorDto;
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
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
