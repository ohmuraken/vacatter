package com.fernandocejas.android10.sample.domain.interactor;


import com.fernandocejas.android10.sample.domain.Onishi;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.OnishiRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case
 * for retrieving a collection of all {@link Onishi}
 */
public class GetOnishiList extends UseCase<List<Onishi>, Void> {

    private final OnishiRepository onishiRepository;

    @Inject
    GetOnishiList(OnishiRepository onishiRepository, ThreadExecutor threadExecutor,
                  PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.onishiRepository = onishiRepository;
    }

    @Override
    Observable<List<Onishi>> buildUseCaseObservable(Void unused) {
        return this.onishiRepository.onishis();
    }
}
