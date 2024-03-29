package com.newsscraper.services

import com.newsscraper.services.apireceivers.*
import com.newsscraper.transportobjects.NewsDTO
import com.newsscraper.transportobjects.TagsDTO
import com.newsscraper.transportobjects.UserDTO
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action0
import rx.functions.Action1
import rx.functions.Func1
import rx.schedulers.Schedulers
import timber.log.Timber

object ServiceManager {

    private val serviceProvider = ServiceProvider()

    fun setToken(token: String) {
        serviceProvider.token = token
    }

    fun getNews(receiver: GetNewsReceiver, fetchNews: Boolean = false) {
        setupRequest(
            serviceProvider
                .userService()
                .getNews(fetchNews),
            Action1 { receiver.onGetNewsSuccess(it as List<NewsDTO>) },
            Action1 { e -> receiver.onGetNewsError() })
    }

    fun register(receiver: RegisterReceiver, user: UserDTO) {
        setupRequest(
            serviceProvider
                .userRegisterService()
                .registerUser(user),
            Action1 { receiver.onRegisterSuccess() },
            Action1 { e -> receiver.onRegisterError() })
    }

    fun login(receiver: LoginReceiver, user: UserDTO) {
        setupRequest(
            serviceProvider
                .loginService()
                .login(user),
            Action1 { receiver.onLoginSuccess(it as String) },
            Action1 { e -> receiver.onLoginError() })
    }

    fun getTags(receiver: GetTagsReceiver) {
        setupRequest(
            serviceProvider
                .userService()
                .getTags(),
            Action1 { receiver.onGetTagsSuccess(it as List<String>) },
            Action1 { e -> receiver.onGetTagsError() })
    }

    fun putTags(receiver: PutTagsReceiver, tags: List<String>) {
        setupRequest(
            serviceProvider
            .userService()
            .putTags(TagsDTO(tags)),
            Action1 { receiver.onPutTagsSuccess() },
            Action1 { e -> receiver.onPutTagsError() })
    }

    fun getPopularTags(receiver: GetPopularTagsReceiver) {
        setupRequest(
            serviceProvider
                .userService()
                .getPopularTags(),
            Action1 {receiver.onGetPopularTagsSuccess(it as List<String>)},
            Action1 {receiver.onGetPopularTagsError()}
        )
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