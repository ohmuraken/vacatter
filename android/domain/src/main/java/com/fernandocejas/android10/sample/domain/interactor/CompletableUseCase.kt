package com.fernandocejas.android10.sample.domain.interactor

import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor
import com.fernandocejas.arrow.checks.Preconditions
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.operators.completable.CompletableToObservable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Abstract class Deal retrofit completable class.
 * This class's idea is based Usecase class.
 *
 *  Created on 8/13/17.
 */
abstract class CompletableUseCase<Params> constructor(open val threadExecutor: ThreadExecutor,
    open val postExecutionThread: PostExecutionThread) {

  val disposables: CompositeDisposable = CompositeDisposable()

  /**
   * Builds an [CompletableObserver] which will be used when executing the current [UseCase].
   */
  abstract fun buildUseCaseCompletable(params: Params): Completable

  /**
   * Executes the current use case.
   *
   * @param observer {@link DisposableObserver} which will be listening to the observable build
   * by {@link #buildUseCaseObservable(Params)} ()} method.
   * @param params Parameters (Optional) used to build/execute this use case.
   */
  fun execute(observer: DisposableCompletableObserver, params: Params) {
    Preconditions.checkNotNull(observer)
    val observable = this.buildUseCaseCompletable(params)
        .subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutionThread.getScheduler())
    addDisposable(observable.subscribeWith(observer))
  }

  /**
   * Dispose from current [CompositeDisposable].
   */
  fun dispose() {
    if (!disposables.isDisposed) {
      disposables.dispose()
    }
  }

  /**
   * Dispose from current [CompositeDisposable].
   */
  private fun addDisposable(disposable: Disposable) {
    Preconditions.checkNotNull(disposable)
    Preconditions.checkNotNull(disposables)
    disposables.add(disposable)
  }
}