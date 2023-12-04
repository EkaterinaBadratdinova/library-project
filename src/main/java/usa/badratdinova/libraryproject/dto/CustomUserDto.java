package usa.badratdinova.libraryproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomUserDto {
    @Size(min = 6, max = 15)
    @NotBlank(message = "Name is required.")
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String name;
    @Size(min = 8, max = 15)
    @NotBlank(message = "Password is required.")
    @Pattern(regexp = "^[A-Za-z0-9_.+-@!?]+$")
    private String password;
}
