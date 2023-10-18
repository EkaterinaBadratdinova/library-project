package usa.badratdinova.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import usa.badratdinova.libraryproject.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
