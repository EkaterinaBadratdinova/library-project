package usa.badratdinova.libraryproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorDto2 {
    private Long id;
    private String name;
    private String surname;
}
