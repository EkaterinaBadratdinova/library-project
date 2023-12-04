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
public class GenreCreateDto {
    @Size(min = 3, max = 30)
    @NotBlank(message = "Enter the name of the genre")
    private String name;
}