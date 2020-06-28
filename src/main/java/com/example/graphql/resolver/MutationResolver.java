package com.example.graphql.resolver;

import com.example.graphql.dao.AuthorDao;
import com.example.graphql.dao.BookDao;
import com.example.graphql.model.Author;
import com.example.graphql.model.Book;
import com.example.graphql.model.BookInput;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

/**
 * @author pcs
 * Date         2020/4/9
 * Description:
 */
@Component
public class MutationResolver implements GraphQLMutationResolver {

    private final AuthorDao authorDao;
    private final BookDao bookDao;

    public MutationResolver(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

    public Author newAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        return authorDao.saveAndFlush(author);
    }

    public Book newBook(String title, String isbn, int pageCount, Long authorId) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPageCount(pageCount);
        book.setAuthorId(authorId);
        return bookDao.saveAndFlush(book);
    }


    public Book saveBook(BookInput input) {
        Book book = new Book();
        book.setTitle(input.getTitle());
        book.setIsbn(input.getIsbn());
        book.setPageCount(input.getPageCount());
        book.setAuthorId(input.getAuthorId());
        return bookDao.saveAndFlush(book);
    }
}
