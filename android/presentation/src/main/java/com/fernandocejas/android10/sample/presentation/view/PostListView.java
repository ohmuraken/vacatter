package com.fernandocejas.android10.sample.presentation.view;

import com.fernandocejas.android10.sample.presentation.model.PostModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link PostModel}.
 */
public interface PostListView extends LoadDataView {

    void renderPostList(Collection<PostModel> postModelCollection);

    void viewPost(PostModel postModel);
}
