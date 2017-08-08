package com.fernandocejas.android10.sample.presentation.mapper;

import com.fernandocejas.android10.sample.domain.Post;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.model.PostModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

@PerActivity
public class PostModelDataMapper {

    @Inject
    public PostModelDataMapper() {
    }

    public PostModel transform(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final PostModel postModel = new PostModel(post.getPostId());
        postModel.setMessage(post.getMessage());

        return postModel;
    }

    public Collection<PostModel> transform(Collection<Post> postsCollection) {
        Collection<PostModel> postModelsCollection;

        if (postsCollection != null && !postsCollection.isEmpty()) {
            postModelsCollection = new ArrayList<>();
            for (Post post : postsCollection) {
                postModelsCollection.add(transform(post));
            }
        } else {
            postModelsCollection = Collections.emptyList();
        }

        return postModelsCollection;
    }

}
