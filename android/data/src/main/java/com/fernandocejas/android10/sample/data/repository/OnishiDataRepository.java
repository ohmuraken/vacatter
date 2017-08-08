package com.fernandocejas.android10.sample.data.repository;

import com.fernandocejas.android10.sample.domain.Onishi;
import com.fernandocejas.android10.sample.domain.repository.OnishiRepository;
import com.fernandocejas.android10.sample.domain.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link PostRepository} for retrieving post data.
 */
@Singleton
public class OnishiDataRepository implements OnishiRepository {

    @Inject
    OnishiDataRepository() {}

    @Override
    public Observable<List<Onishi>> onishis() {
        List<Onishi> onishisList = new ArrayList<>();
        Onishi onishi1 = new Onishi(1);
        onishi1.setMessage("This is Post Entity 1");
        onishisList.add(onishi1);

        Onishi onishi2 = new Onishi(2);
        onishi1.setMessage("This is Post Entity 1");
        onishisList.add(onishi2);
        return Observable.just(onishisList);
    }
}
