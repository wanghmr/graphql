package com.example.graphql.resolver;

import com.example.graphql.dao.AuthorDao;
import com.example.graphql.model.Author;
import com.example.graphql.model.Book;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author pcs
 * Date         2020/4/9
 * Description:
 */
@Component
public class BookResolver implements GraphQLResolver<Book> {
    private final AuthorDao authorDao;

    public BookResolver(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    /**
     * 只会调用一次查询
     */
    public CompletableFuture<Author> getAuthor(Book book, DataFetchingEnvironment env) {
        env.getDataLoaderRegistry()
                .computeIfAbsent("author", key ->
                        DataLoader.newDataLoader(
                                (BatchLoader<String, Author>) keys -> CompletableFuture.supplyAsync(() -> authorDao.findByIdIn(keys))
                        )
                );
        return env.getDataLoader("author")
                .load(book.getAuthorId())
                .thenApply(obj -> (Author) obj);
    }

//    /**
//     * 会调用多次查询
//     */
//    public Author getAuthor(Book book) {
//        return authorDao.findAuthorById(book.getAuthorId());
//    }
}
