package com.fernandocejas.android10.sample.domain.repository;

import com.fernandocejas.android10.sample.domain.Onishi;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link Onishi} related data.
 */
public interface OnishiRepository {
    /**
     * Get an {@link Observable} which will emit a list of {@link Onishi}
     */
    Observable<List<Onishi>> onishis();
}
