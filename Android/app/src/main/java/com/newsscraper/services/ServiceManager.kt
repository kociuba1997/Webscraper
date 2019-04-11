package com.newsscraper.services

import com.newsscraper.services.apireceivers.GetNewsReceiver
import com.newsscraper.transportobjects.NewsDTO
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action0
import rx.functions.Action1
import rx.functions.Func1
import rx.schedulers.Schedulers
import timber.log.Timber

object ServiceManager {

    fun getNews(receiver: GetNewsReceiver) {
        setupRequest(ServiceProvider
            .userService
            .getNews(),
            Action1 { receiver.onGetNewsSuccess(it as List<NewsDTO>) },
            Action1 { e -> receiver.onGetNewsError() })
    }

    private fun setupRequest(
        observable: Observable<*>,
        onNext: Action1<Any>,
        onError: Action1<Throwable>,
        onCompleted: Action0 = Action0 { Timber.e("onCompleted") }
    ): Subscription {
        return setupRequestObservable(observable)
            .subscribe(onNext, onError, onCompleted)
    }

    private fun setupRequestObservable(
        observable: Observable<*>
    ): Observable<*> {
        return observable
            .retryWhen(getRefreshNotificationHandler())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getRefreshNotificationHandler(): Func1<Observable<out Throwable>, Observable<Any>> {
        return Func1 { observable ->
            observable
                .zipWith(Observable.range(1, 3)) { error, _ -> error }
                .flatMap { error ->
                    Observable.error<Any>(error as Throwable)
                }
        }
    }
}