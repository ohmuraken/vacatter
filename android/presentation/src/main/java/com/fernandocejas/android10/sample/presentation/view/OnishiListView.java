package com.fernandocejas.android10.sample.presentation.view;

import com.fernandocejas.android10.sample.presentation.model.OnishiModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link OnishiModel}.
 */
public interface OnishiListView extends LoadDataView {

    void renderOnishiList(Collection<OnishiModel> onishiModelCollection);

    void viewOnishi(OnishiModel onishiModel);
}
