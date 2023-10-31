package usa.badratdinova.libraryproject.service;

import usa.badratdinova.libraryproject.dto.BookDto;
import usa.badratdinova.libraryproject.dto.BookDto2;

public interface BookService {
    BookDto2 getByNameV1(String name);

    BookDto2 getByNameV2(String name);

    BookDto2 getByNameV3(String name);
}
