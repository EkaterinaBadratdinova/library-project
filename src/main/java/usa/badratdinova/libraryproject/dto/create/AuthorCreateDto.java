package usa.badratdinova.libraryproject.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorCreateDto {
    @Size(min = 3, max = 10)
    @NotBlank(message = "Name is required.")
    private String name;
    @Size(min = 1, max = 30)
    @NotBlank(message = "Surname is required.")
    private String surname;
}
