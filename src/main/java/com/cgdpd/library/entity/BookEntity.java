package com.cgdpd.library.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(name = "books")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @JoinColumn(
          name = "author_id",
          nullable = false,
          referencedColumnName = "id",
          foreignKey = @ForeignKey(name = "fk_author_id"))
    @ManyToOne(fetch = LAZY)
    private AuthorEntity authorEntity;

    @Column(name = "publication_year")
    private short publicationYear;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "genre", nullable = false, length = 100)
    private String genre;
}
