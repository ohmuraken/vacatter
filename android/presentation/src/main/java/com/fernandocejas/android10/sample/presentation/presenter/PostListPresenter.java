package com.fernandocejas.android10.sample.presentation.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.sample.domain.Post;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.domain.interactor.GetPostList;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.mapper.PostModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.PostModel;
import com.fernandocejas.android10.sample.presentation.view.PostListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class PostListPresenter implements Presenter {

    private PostListView postListView;

    private final GetPostList getPostListUseCase;
    private final PostModelDataMapper postModelDataMapper;

    @Inject
    public PostListPresenter(GetPostList getPostListUseCase,
                             PostModelDataMapper postModelDataMapper) {
        this.getPostListUseCase = getPostListUseCase;
        this.postModelDataMapper = postModelDataMapper;
    }

    public void setView(@NonNull PostListView view) {
        this.postListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getPostListUseCase.dispose();
        this.postListView = null;
    }

    public void initialize() {
        this.loadPostList();
    }

    private void loadPostList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserList();
    }

    public void onPostClicked(PostModel postModel) {
        this.postListView.viewPost(postModel);
    }

    private void showViewLoading() {
        this.postListView.showLoading();
    }

    private void hideViewLoading() {
        this.postListView.hideLoading();
    }

    private void showViewRetry() {
        this.postListView.showRetry();
    }

    private void hideViewRetry() {
        this.postListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.postListView.context(),
                errorBundle.getException());
        this.postListView.showError(errorMessage);
    }

    private void showPostsCollectionInView(Collection<Post> postCollection) {
        final Collection<PostModel> postModelsCollection =
                this.postModelDataMapper.transform(postCollection);
        this.postListView.renderPostList(postModelsCollection);
    }

    private void getUserList() {
        this.getPostListUseCase.execute(new PostListObserver(), null);
    }

    private final class PostListObserver extends DefaultObserver<List<Post>> {

        @Override
        public void onComplete() {
            PostListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            PostListPresenter.this.hideViewLoading();
            PostListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            PostListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Post> posts) {
            PostListPresenter.this.showPostsCollectionInView(posts);
        }
    }
}
