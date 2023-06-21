package com.cgdp.library.dto.book;

import com.cgdp.library.validation.ValidationUtility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import org.hibernate.validator.constraints.ISBN;

@Builder
public record CreateBookRequestDTO(@NotBlank String title,
                                   @NotNull Long authorId,
                                   LocalDate publicationYear,
                                   @ISBN String isbn,
                                   @NotBlank String genre) {

    public CreateBookRequestDTO(String title, @NotNull Long authorId,
                                LocalDate publicationYear, String isbn, String genre) {
        this.title = ValidationUtility.requiredNotBlank("title", title);
        this.authorId = ValidationUtility.required("authorId", authorId);
        this.publicationYear = ValidationUtility.requiredValidDate("publicationYear",
              publicationYear);
        this.isbn = ValidationUtility.requiredValidIsbn("isbn", isbn);
        this.genre = ValidationUtility.requiredNotBlank("genre", genre);
    }
}
