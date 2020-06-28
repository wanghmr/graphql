package com.example.graphql.graphqlself;

import com.example.graphql.model.Book;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observables.ConnectableObservable;
import org.springframework.stereotype.Component;

/**
 * @author pcs
 * Date         2020/4/20
 * Description:
 */
//@Component
public class BookPublisher {

    private final Flowable<Book> publisher;

    private ObservableEmitter<Book> emitter;

    public BookPublisher() {
        Observable<Book> commentUpdateObservable = Observable.create(emitter -> {
            this.emitter = emitter;
        });

        ConnectableObservable<Book> connectableObservable = commentUpdateObservable.share().publish();
        connectableObservable.connect();

        publisher = connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
    }

    public void publish(final Book note) {
        System.out.println("@BookPublisher@publish@放入数据");
        emitter.onNext(note);
    }


    public Flowable<Book> getPublisher() {
        return publisher;
    }

}
