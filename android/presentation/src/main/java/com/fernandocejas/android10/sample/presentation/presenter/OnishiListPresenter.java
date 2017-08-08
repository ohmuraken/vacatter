package com.fernandocejas.android10.sample.presentation.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.sample.domain.Onishi;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.domain.interactor.GetOnishiList;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.mapper.OnishiModelDataMapper;
import com.fernandocejas.android10.sample.presentation.model.OnishiModel;
import com.fernandocejas.android10.sample.presentation.view.OnishiListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class OnishiListPresenter implements Presenter {

    private OnishiListView onishiListView;

    private final GetOnishiList getOnishiListUseCase;
    private final OnishiModelDataMapper onishiModelDataMapper;

    @Inject
    public OnishiListPresenter(GetOnishiList getOnishiListUseCase,
                               OnishiModelDataMapper onishiModelDataMapper) {
        this.getOnishiListUseCase = getOnishiListUseCase;
        this.onishiModelDataMapper = onishiModelDataMapper;
    }

    public void setView(@NonNull OnishiListView view) {
        this.onishiListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getOnishiListUseCase.dispose();
        this.onishiListView = null;
    }

    public void initialize() {
        this.loadOnishiList();
    }

    private void loadOnishiList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserList();
    }

    public void onOnishiClicked(OnishiModel onishiModel) {
        this.onishiListView.viewOnishi(onishiModel);
    }

    private void showViewLoading() {
        this.onishiListView.showLoading();
    }

    private void hideViewLoading() {
        this.onishiListView.hideLoading();
    }

    private void showViewRetry() {
        this.onishiListView.showRetry();
    }

    private void hideViewRetry() {
        this.onishiListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.onishiListView.context(),
                errorBundle.getException());
        this.onishiListView.showError(errorMessage);
    }

    private void showOnishisCollectionInView(Collection<Onishi> onishiCollection) {
        final Collection<OnishiModel> onishiModelsCollection =
                this.onishiModelDataMapper.transform(onishiCollection);
        this.onishiListView.renderOnishiList(onishiModelsCollection);
    }

    private void getUserList() {
        this.getOnishiListUseCase.execute(new OnishiListObserver(), null);
    }

    private final class OnishiListObserver extends DefaultObserver<List<Onishi>> {

        @Override
        public void onComplete() {
            OnishiListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            OnishiListPresenter.this.hideViewLoading();
            OnishiListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            OnishiListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Onishi> onishis) {
            OnishiListPresenter.this.showOnishisCollectionInView(onishis);
        }
    }
}
