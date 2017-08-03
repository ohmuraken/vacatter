package com.fernandocejas.android10.sample.domain.interactor;


import com.fernandocejas.android10.sample.domain.Post;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.PostRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case
 * for retrieving a collection of all {@link Post}
 */
public class GetPostList extends UseCase<List<Post>, Void> {
    
    private final PostRepository postRepository;

    @Inject
    GetPostList(PostRepository postRepository, ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.postRepository = postRepository;
    }

    @Override
    Observable<List<Post>> buildUseCaseObservable(Void unused) {
        return this.postRepository.posts();
    }
}
