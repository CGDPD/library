package com.cgdpd.library.catalog.app.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "publication_year")
    private short publicationYear;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "genre", nullable = false)
    private String genre;

    @JoinColumn(
          name = "author_id",
          nullable = false,
          referencedColumnName = "id",
          foreignKey = @ForeignKey(name = "fk_book_author_id"))
    @ManyToOne(fetch = LAZY)
    private AuthorEntity authorEntity;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private List<BookCopyEntity> bookCopyEntities;

    @Override
    public String toString() {
        return "BookEntity{" +
              "id=" + id +
              ", title='" + title + '\'' +
              ", publicationYear=" + publicationYear +
              ", isbn='" + isbn + '\'' +
              ", genre='" + genre + '\'' +
              ", authorId=" + authorEntity.getId() +
              '}';
    }
}
