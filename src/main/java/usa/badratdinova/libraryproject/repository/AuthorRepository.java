package usa.badratdinova.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import usa.badratdinova.libraryproject.model.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    Optional<Author> findAuthorByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM AUTHOR WHERE name = ?")
    Optional<Author> findAuthorByNameBySql(String name);
}
