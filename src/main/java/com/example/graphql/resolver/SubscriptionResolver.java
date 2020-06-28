package com.example.graphql.resolver;

import com.example.graphql.graphqlself.BookPublisher;
import com.example.graphql.model.Book;
import com.example.graphql.model.StockPriceUpdate;
import graphql.execution.reactive.SingleSubscriberPublisher;
import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import graphql.schema.DataFetchingEnvironment;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author pcs
 * Date         2020/4/20
 * Description:
 */
//@Component
public class SubscriptionResolver implements GraphQLSubscriptionResolver {

    private final BookPublisher bookPublisher;

    public SubscriptionResolver(BookPublisher bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public Publisher<Book> authorAddBook(Long authorId, DataFetchingEnvironment env) {
        // 需要过滤是否发送，不太好使，不是100%能发出去
        return bookPublisher.getPublisher().filter(f -> f.getAuthorId() == authorId);
    }
}
