package com.fernandocejas.android10.sample.domain.repository;

import com.fernandocejas.android10.sample.domain.Post;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link Post} related data.
 */
public interface PostRepository {
    /**
     * Get an {@link Observable} which will emit a list of {@link Post}
     */
    Observable<List<Post>> posts();
}
