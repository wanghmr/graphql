package com.example.graphql.resolver;

import com.example.graphql.dao.BookDao;
import com.example.graphql.model.Author;
import com.example.graphql.model.Book;
import com.example.graphql.util.DateTimeUtil;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author pcs
 * Date         2020/4/9
 * Description:
 */
@Component
public class AuthorResolver implements GraphQLResolver<Author> {

    private final DateTimeUtil dateTimeUtil;
    private final BookDao bookDao;

    public AuthorResolver(DateTimeUtil dateTimeUtil, BookDao bookDao) {
        this.dateTimeUtil = dateTimeUtil;
        this.bookDao = bookDao;
    }


    public String getCreatedTime(Author author) {
        return dateTimeUtil.convertDate2Str("yyyy-MM-dd HH:mm:ss", author.getCreatedTime());
    }

    public List<Book> getBooks(Author author) {
        return bookDao.findByAuthorId(author.getId());
    }

}
