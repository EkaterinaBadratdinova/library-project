package usa.badratdinova.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import usa.badratdinova.libraryproject.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
