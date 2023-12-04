package usa.badratdinova.libraryproject.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookCreateDto {
    @Size(min = 2)
    @NotBlank(message = "Name is required.")
    private String name;
    @NotBlank(message = "Genre is required.")
    @Size(min = 3, max = 30)
    private String genre;
    @NotBlank(message = "The authors of the book are required.")
    private List<AuthorCreateDto> authors;
}
