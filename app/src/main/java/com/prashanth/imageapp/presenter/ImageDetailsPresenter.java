package com.prashanth.imageapp.presenter;

import androidx.annotation.VisibleForTesting;
import com.prashanth.imageapp.ShutterstockImageApplication;
import com.prashanth.imageapp.contract.APIContract;
import com.prashanth.imageapp.model.ImageSearchResponse;
import com.prashanth.imageapp.network.ShutterstockAPI;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

public class ImageDetailsPresenter implements APIContract.Presenter {

    @Inject
    ShutterstockAPI api;

    private CompositeDisposable compositeDisposable;

    private APIContract.ImageListView view;

    public ImageDetailsPresenter() {
        compositeDisposable = new CompositeDisposable();
        ShutterstockImageApplication.component.inject(this);
    }

    @VisibleForTesting()
    public ImageDetailsPresenter(ShutterstockAPI api) {
        compositeDisposable = new CompositeDisposable();
        this.api = api;
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    @Override
    public void fetchImages(String category, String query, String searchId, int pageNumber,
                            int numberOfItemsPerPage, APIContract.ImageListView view) {
        this.view = view;
        this.view.callStarted();
        Disposable disposable = api.searchImageByCategory(category, query, searchId, pageNumber, numberOfItemsPerPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ImageSearchResponse>() {
                    @Override
                    public void onNext(ImageSearchResponse response) {
                        view.onResponseImageList(response.getImageDetailsArrayList());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.callFailed(e);
                    }

                    @Override
                    public void onComplete() {
                        view.callComplete();
                    }
                });
        compositeDisposable.add(disposable);
    }

}
