package com.cgdp.library.dto.book;

import com.cgdp.library.validation.ValidationUtility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import org.hibernate.validator.constraints.ISBN;

@Builder
public record BookDTO(@NotNull Long id,
                      @NotBlank String title,
                      @NotNull Long authorId,
                      LocalDate publicationYear,
                      @ISBN String isbn,
                      @NotBlank String genre) {

    public BookDTO(@NotNull Long id, String title, @NotNull Long authorId,
                   LocalDate publicationYear, String isbn, String genre) {
        this.id = ValidationUtility.required("id", id);
        this.title = ValidationUtility.requiredNotBlank("title", title);
        this.authorId = ValidationUtility.required("authorId", authorId);
        this.publicationYear = ValidationUtility.validatePublicationYear("publicationYear",
              publicationYear);
        this.isbn = ValidationUtility.validateISBN("isbn", isbn);
        this.genre = ValidationUtility.requiredNotBlank("genre", genre);
    }
}
