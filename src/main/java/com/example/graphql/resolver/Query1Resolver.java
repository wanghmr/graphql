package com.example.graphql.resolver;

import com.example.graphql.dao.AuthorDao;
import com.example.graphql.dao.BookDao;
import com.example.graphql.model.Author;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

/**
 * @author pcs
 * Date         2020/4/27
 * Description:
 */
@Component
public class Query1Resolver implements GraphQLQueryResolver {
    private final AuthorDao authorDao;
    private final BookDao bookDao;

    public Query1Resolver(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

    public Author findAuthorById(Long id) {
        return authorDao.findAuthorById(id);
    }

}
