package com.fernandocejas.android10.sample.presentation.mapper;

import com.fernandocejas.android10.sample.domain.Onishi;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.model.OnishiModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

@PerActivity
public class OnishiModelDataMapper {

    @Inject
    public OnishiModelDataMapper() {
    }

    public OnishiModel transform(Onishi onishi) {
        if (onishi == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final OnishiModel onishiModel = new OnishiModel(onishi.getOnishiId());
        onishiModel.setMessage(onishi.getMessage());

        return onishiModel;
    }

    public Collection<OnishiModel> transform(Collection<Onishi> onishisCollection) {
        Collection<OnishiModel> onishiModelsCollection;

        if (onishisCollection != null && !onishisCollection.isEmpty()) {
            onishiModelsCollection = new ArrayList<>();
            for (Onishi onishi : onishisCollection) {
                onishiModelsCollection.add(transform(onishi));
            }
        } else {
            onishiModelsCollection = Collections.emptyList();
        }

        return onishiModelsCollection;
    }

}
