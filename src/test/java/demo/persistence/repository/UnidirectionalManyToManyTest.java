package demo.persistence.repository;


import demo.persistence.model.Author;
import demo.persistence.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UnidirectionalManyToManyTest {

    @Autowired
    private AuthorJpaRepository authorJpaRepository;

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Test
    @Transactional
    void givenBookWithNonExistingAuthor_whenCreateBook_thenThrowsException() {
        Author nonExistingAuthor = Author.builder()
                .id(10L)
                .build();
        Book book = Book.builder()
                .title("The book that will never be saved")
                .build();
        book.getAuthors().add(nonExistingAuthor);
        Executable exe = () -> bookJpaRepository.saveAndFlush(book);
        assertThrows(DataAccessException.class, exe);
    }

    @Test
    @Sql("/insertAuthor1.sql")
    @Transactional
    void givenBookWithExistingAuthor_whenCreateBook_thenCreatedIsNotNull() {
        Author existingAuthor = authorJpaRepository.getReferenceById(1L);
        Book book = Book.builder().build();
        book.getAuthors().add(existingAuthor);
        Book created = bookJpaRepository.saveAndFlush(book);
        assertThat(created).isNotNull();
        /*
            insert into books (id, title) values (default, ?)
            binding parameter [1] as [VARCHAR] - [null]

            insert into book_author (book_id, author_id) values (?, ?)
            binding parameter [1] as [BIGINT] - [1]
            binding parameter [2] as [BIGINT] - [1]
         */
    }

    @Test
    @Sql("/insertBook2WithAuthor2.sql")
    @Transactional
    void givenBookWithAuthor_whenReadBook_thenFound() {
        // GIVEN
        long bookWithAuthorId = 2L;
        // WHEN
        Book found = bookJpaRepository.findById(bookWithAuthorId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        // THEN
        assertThat(found).isNotNull();
        assertThat(found.getAuthors().size()).isEqualTo(1);
        Set<Long> authorIds = found.getAuthors().stream()
                .map(Author::getId)
                .collect(Collectors.toSet());
        assertThat(authorIds).containsAll(Set.of(bookWithAuthorId));
        /*
        select book0_.id as id1_2_0_, book0_.title as title2_2_0_ from books book0_ where book0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        select authors0_.book_id as book_id1_1_0_, authors0_.author_id as author_i2_1_0_, author1_.id as id1_0_1_, author1_.name as name2_0_1_ from book_author authors0_ inner join authors author1_ on authors0_.author_id=author1_.id where authors0_.book_id=?
        binding parameter [1] as [BIGINT] - [2]
         */
    }

    @Test
    @Sql("/insertAuthor1.sql")
    @Sql("/insertBook1WithoutAuthor.sql")
    @Transactional
    void givenBookWithoutAuthor_whenUpdateAddingAuthor_thenUpdatedAuthorsSizeIs1() {
        // GIVEN
        Author existingAuthor = authorJpaRepository.getReferenceById(1L);
        Book bookWithoutAuthor = bookJpaRepository.getReferenceById(1L);
        // WHEN
        bookWithoutAuthor.getAuthors().add(existingAuthor);
        Book saved = bookJpaRepository.saveAndFlush(bookWithoutAuthor);
        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getAuthors().size()).isEqualTo(1);
        /*
        select book0_.id as id1_2_0_, book0_.title as title2_2_0_ from books book0_ where book0_.id=?
        binding parameter [1] as [BIGINT] - [1]

        select authors0_.book_id as book_id1_1_0_, authors0_.author_id as author_i2_1_0_, author1_.id as id1_0_1_, author1_.name as name2_0_1_ from book_author authors0_ inner join authors author1_ on authors0_.author_id=author1_.id where authors0_.book_id=?
        binding parameter [1] as [BIGINT] - [1]

        select author0_.id as id1_0_0_, author0_.name as name2_0_0_ from authors author0_ where author0_.id=?
        binding parameter [1] as [BIGINT] - [1]

        insert into book_author (book_id, author_id) values (?, ?)
        binding parameter [1] as [BIGINT] - [1]
        binding parameter [2] as [BIGINT] - [1]
         */
    }

    @Test
    @Sql("/insertAuthor1.sql")
    @Sql("/insertBook2WithAuthor2.sql")
    @Transactional
    void givenBookWithAuthor_whenUpdateAuthor_thenUpdatedNewAuthor() {
        // GIVEN
        long updatedAuthorId = 1L;
        Author existingAuthor = authorJpaRepository.getReferenceById(updatedAuthorId);
        Book bookWithAuthor = bookJpaRepository.findById(2L)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        // WHEN
        bookWithAuthor.getAuthors().clear();
        bookWithAuthor.getAuthors().add(existingAuthor);
        Book saved = bookJpaRepository.saveAndFlush(bookWithAuthor);
        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getAuthors().size()).isEqualTo(1);
        Set<Long> authorIds = saved.getAuthors().stream()
                .map(Author::getId)
                .collect(Collectors.toSet());
        assertThat(authorIds).containsAll(Set.of(updatedAuthorId));
        /*
        select book0_.id as id1_2_0_, book0_.title as title2_2_0_ from books book0_ where book0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        select authors0_.book_id as book_id1_1_0_, authors0_.author_id as author_i2_1_0_, author1_.id as id1_0_1_, author1_.name as name2_0_1_ from book_author authors0_ inner join authors author1_ on authors0_.author_id=author1_.id where authors0_.book_id=?
        binding parameter [1] as [BIGINT] - [2]

        select author0_.id as id1_0_0_, author0_.name as name2_0_0_ from authors author0_ where author0_.id=?
        binding parameter [1] as [BIGINT] - [1]

        delete from book_author where book_id=? and author_id=?
        binding parameter [1] as [BIGINT] - [2]
        binding parameter [2] as [BIGINT] - [2]

        insert into book_author (book_id, author_id) values (?, ?)
        binding parameter [1] as [BIGINT] - [2]
        binding parameter [2] as [BIGINT] - [1]
         */
    }


    @Test
    @Sql("/insertAuthor1.sql")
    @Sql("/insertBook2WithAuthor2.sql")
    @Transactional
    void givenBookWithAuthor_whenAddingAuthor_thenAdded() {
        // GIVEN
        long alreadyAddedAuthorId = 2L;
        long updatedAuthorId = 1L;
        Author existingAuthor = authorJpaRepository.getReferenceById(updatedAuthorId);
        Book bookWithAuthor = bookJpaRepository.findById(2L)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        // WHEN
        bookWithAuthor.getAuthors().add(existingAuthor);
        Book saved = bookJpaRepository.saveAndFlush(bookWithAuthor);
        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getAuthors().size()).isEqualTo(2);
        Set<Long> authorIds = saved.getAuthors().stream()
                .map(Author::getId)
                .collect(Collectors.toSet());
        assertThat(authorIds).containsAll(Set.of(alreadyAddedAuthorId, updatedAuthorId));
        /*
        select book0_.id as id1_2_0_, book0_.title as title2_2_0_ from books book0_ where book0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        select authors0_.book_id as book_id1_1_0_, authors0_.author_id as author_i2_1_0_, author1_.id as id1_0_1_, author1_.name as name2_0_1_ from book_author authors0_ inner join authors author1_ on authors0_.author_id=author1_.id where authors0_.book_id=?
        binding parameter [1] as [BIGINT] - [2]

        select author0_.id as id1_0_0_, author0_.name as name2_0_0_ from authors author0_ where author0_.id=?
        binding parameter [1] as [BIGINT] - [1]

        insert into book_author (book_id, author_id) values (?, ?)
        binding parameter [1] as [BIGINT] - [2]
        binding parameter [2] as [BIGINT] - [1]
         */
    }

    @Test
    @Sql("/insertBook2WithAuthor2.sql")
    @Transactional
    void givenBookWithAuthor_whenDeletingAuthor_thenDeleted() {
        // GIVEN
        Book bookWithAuthor = bookJpaRepository.findById(2L)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        // WHEN
        bookWithAuthor.getAuthors().clear();
        Book saved = bookJpaRepository.saveAndFlush(bookWithAuthor);
        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getAuthors().size()).isEqualTo(0);
        /*
        select book0_.id as id1_2_0_, book0_.title as title2_2_0_ from books book0_ where book0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        select authors0_.book_id as book_id1_1_0_, authors0_.author_id as author_i2_1_0_, author1_.id as id1_0_1_, author1_.name as name2_0_1_ from book_author authors0_ inner join authors author1_ on authors0_.author_id=author1_.id where authors0_.book_id=?
        binding parameter [1] as [BIGINT] - [2]

        delete from book_author where book_id=?
        binding parameter [1] as [BIGINT] - [2]
         */
    }

}
