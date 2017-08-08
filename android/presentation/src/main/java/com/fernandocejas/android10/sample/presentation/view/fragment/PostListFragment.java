package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.components.PostComponent;
import com.fernandocejas.android10.sample.presentation.model.PostModel;
import com.fernandocejas.android10.sample.presentation.presenter.PostListPresenter;
import com.fernandocejas.android10.sample.presentation.view.PostListView;
import com.fernandocejas.android10.sample.presentation.view.adapter.PostsAdapter;
import com.fernandocejas.android10.sample.presentation.view.adapter.PostsLayoutManager;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostListFragment extends BaseFragment implements PostListView {

    public interface PostListListener {
        void onPostClicked(final PostModel postModel);
    }

    @Inject
    PostListPresenter postListPresenter;
    @Inject
    PostsAdapter postsAdapter;

    @Bind(R.id.rv_users)
    RecyclerView rv_users;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    private PostListListener postListListener;

    public PostListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PostListListener) {
            this.postListListener = (PostListListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(PostComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.postListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadPostList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.postListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.postListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv_users.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.postListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.postListListener = null;
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    @Override
    public void renderPostList(Collection<PostModel> postModelsCollection) {
        if (postModelsCollection != null) {
            this.postsAdapter.setPostsCollection(postModelsCollection);
        }
    }

    @Override
    public void viewPost(PostModel postModel) {
        if (this.postListListener != null) {
            this.postListListener.onPostClicked(postModel);
        }
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        this.postsAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_users.setLayoutManager(new PostsLayoutManager(context()));
        this.rv_users.setAdapter(postsAdapter);
    }

    /**
     * Loads all users.
     */
    private void loadPostList() {
        this.postListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        PostListFragment.this.loadPostList();
    }

    private PostsAdapter.OnItemClickListener onItemClickListener =
            new PostsAdapter.OnItemClickListener()
            {
                @Override
                public void onPostItemClicked(PostModel postModel) {
                    if (PostListFragment.this.postListPresenter != null && postModel != null) {
                        PostListFragment.this.postListPresenter.onPostClicked(postModel);
                    }
                }
            };
}
