package com.cgdpd.library.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book_copies")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCopyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @JoinColumn(
          name = "book_id",
          nullable = false,
          referencedColumnName = "id",
          foreignKey = @ForeignKey(name = "fk_book_copies_book_id"))
    @ManyToOne(fetch = LAZY)
    private BookEntity bookEntity;

    @Column(name = "tracking_status", nullable = false)
    private String trackingStatus;

    @JoinColumn(
          name = "user_id",
          referencedColumnName = "id",
          foreignKey = @ForeignKey(name = "fk_book_copies_user_id"))
    @ManyToOne(fetch = LAZY)
    private UserEntity userEntity;

    @Override
    public String toString() {
        return "BookCopyEntity{" +
              "id=" + id +
              ", bookId=" + bookEntity.getId() +
              ", trackingStatus='" + trackingStatus + '\'' +
              ", userId=" + userEntity.getId() +
              '}';
    }
}
