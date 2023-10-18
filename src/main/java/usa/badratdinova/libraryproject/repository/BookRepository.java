package usa.badratdinova.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import usa.badratdinova.libraryproject.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
