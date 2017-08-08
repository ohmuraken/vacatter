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
import com.fernandocejas.android10.sample.presentation.internal.di.components.OnishiComponent;
import com.fernandocejas.android10.sample.presentation.model.OnishiModel;
import com.fernandocejas.android10.sample.presentation.presenter.OnishiListPresenter;
import com.fernandocejas.android10.sample.presentation.view.OnishiListView;
import com.fernandocejas.android10.sample.presentation.view.adapter.OnishisAdapter;
import com.fernandocejas.android10.sample.presentation.view.adapter.OnishisLayoutManager;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnishiListFragment extends BaseFragment implements OnishiListView {

    public interface OnishiListListener {
        void onOnishiClicked(final OnishiModel onishiModel);
    }

    @Inject
    OnishiListPresenter onishiListPresenter;
    @Inject
    OnishisAdapter onishisAdapter;

    @Bind(R.id.rv_users)
    RecyclerView rv_users;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    private OnishiListListener onishiListListener;

    public OnishiListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnishiListListener) {
            this.onishiListListener = (OnishiListListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(OnishiComponent.class).inject(this);
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
        this.onishiListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadOnishiList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.onishiListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.onishiListPresenter.pause();
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
        this.onishiListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onishiListListener = null;
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
    public void renderOnishiList(Collection<OnishiModel> onishiModelsCollection) {
        if (onishiModelsCollection != null) {
            this.onishisAdapter.setOnishisCollection(onishiModelsCollection);
        }
    }

    @Override
    public void viewOnishi(OnishiModel onishiModel) {
        if (this.onishiListListener != null) {
            this.onishiListListener.onOnishiClicked(onishiModel);
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
        this.onishisAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_users.setLayoutManager(new OnishisLayoutManager(context()));
        this.rv_users.setAdapter(onishisAdapter);
    }

    /**
     * Loads all users.
     */
    private void loadOnishiList() {
        this.onishiListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        OnishiListFragment.this.loadOnishiList();
    }

    private OnishisAdapter.OnItemClickListener onItemClickListener =
            new OnishisAdapter.OnItemClickListener()
            {
                @Override
                public void onOnishiItemClicked(OnishiModel onishiModel) {
                    if (OnishiListFragment.this.onishiListPresenter != null && onishiModel != null) {
                        OnishiListFragment.this.onishiListPresenter.onOnishiClicked(onishiModel);
                    }
                }
            };
}
