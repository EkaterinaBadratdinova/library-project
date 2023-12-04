package usa.badratdinova.libraryproject.dto.update;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import usa.badratdinova.libraryproject.dto.create.AuthorCreateDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookUpdateDto {
    @NotBlank(message = "ID is required.")
    private Long id;
    @NotBlank(message = "Name is required.")
    private String name;
    @NotBlank(message = "Genre is required.")
    private String genre;
    @NotBlank(message = "The authors of the book are required.")
    private List<AuthorCreateDto> authors;
}
