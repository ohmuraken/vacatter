package com.fernandocejas.android10.sample.data.repository;

import com.fernandocejas.android10.sample.domain.Post;
import com.fernandocejas.android10.sample.domain.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link PostRepository} for retrieving post data.
 */
@Singleton
public class PostDataRepository implements PostRepository {

    @Inject
    PostDataRepository() {}

    @Override
    public Observable<List<Post>> posts() {
        List<Post> postsList = new ArrayList<>();
        Post post1 = new Post(1);
        post1.setMessage("This is Post Entity 1");
        postsList.add(post1);

        Post post2 = new Post(2);
        post1.setMessage("This is Post Entity 1");
        postsList.add(post2);
        return Observable.just(postsList);
    }
}
