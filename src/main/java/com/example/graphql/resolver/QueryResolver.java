package com.example.graphql.resolver;

import com.example.graphql.dao.AuthorDao;
import com.example.graphql.dao.BookDao;
import com.example.graphql.model.Character;
import com.example.graphql.model.*;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author pcs
 * Date         2020/4/9
 * Description:
 */
@Component
public class QueryResolver implements GraphQLQueryResolver {
    private final AuthorDao authorDao;
    private final BookDao bookDao;

    public QueryResolver(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

//    public Author findAuthorById(Long id) {
//        return authorDao.findAuthorById(id);
//    }

    public List<Author> findAllAuthors() {
        return authorDao.findAll();
    }

    public List<Book> findAllBooks() {
        return bookDao.findAll();
    }

    public BookPage findBookPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> page = bookDao.findAll(pageable);
        BookPage bookPage = new BookPage();
        PageBase pageBase = new PageBase();
        pageBase.setPageNumber(pageNumber);
        pageBase.setPageSize(pageSize);
        pageBase.setHasNextPage(page.hasNext());
        pageBase.setTotalPages(page.getTotalPages());
        pageBase.setTotalElements(page.getTotalElements());
        bookPage.setPageBase(pageBase);
        bookPage.setContent(page.getContent());
        return bookPage;
    }

    /**
     * @param first 指定取游标后的多少个数据，与after搭配使用
     * @param after 开始游标，与first搭配使用
     * @param env   DataFetchingEnvironment
     * @return Connection<Author>
     */
    public Connection<Author> findAuthorPage(int first, String after, DataFetchingEnvironment env) {
        return new SimpleListConnection<>(authorDao.findAll()).get(env);
    }

    public Character heroForEpisode(Episode episode) {
        switch (episode) {
            case JEDI:
                Human human = new Human();
                human.setHomePlanet("HomePlanet");
                human.setId(1L);
                human.setName("Human");
                human.setAppearsIn(Collections.singletonList(Episode.JEDI));
                return human;
            case EMPIRE:
                Droid droid = new Droid();
                droid.setId(2L);
                droid.setName("Droid");
                droid.setPrimaryFunction("primaryFunction");
                droid.setAppearsIn(Collections.singletonList(Episode.EMPIRE));
                return droid;
            default:
                Human human1 = new Human();
                human1.setHomePlanet("HomePlanet1");
                human1.setId(1L);
                human1.setName("Human");
                human1.setAppearsIn(Collections.singletonList(Episode.NEWHOPE));
                return human1;
        }
    }
}
