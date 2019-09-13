package com.prashanth.imageapp.presenter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import com.prashanth.imageapp.contract.APIContract;
import com.prashanth.imageapp.model.ImageSearchResponse;
import com.prashanth.imageapp.network.ShutterstockAPI;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ImageDetailsPresenterTest {

    private static final String DUMMY = "DUMMY_STRING";

    private static final int PAGE_NUMBER = 1;

    private static final int MAX_ITEMS_PER_PAGE = 20;

    @Mock
    private APIContract.ImageListView view;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void getDataAndLoadViewTest() {
        ImageDetailsPresenter presenter = new ImageDetailsPresenter(provideAPI(true));
        presenter.fetchImages(DUMMY, DUMMY, DUMMY, PAGE_NUMBER, MAX_ITEMS_PER_PAGE, view);
        Mockito.verify(view, times(1)).callStarted();
        Mockito.verify(view, times(1)).onResponseImageList(any());
        Mockito.verify(view, times(1)).callComplete();
    }

    @Test
    public void getDataAndLoadViewFailTest() {
        ImageDetailsPresenter presenter = new ImageDetailsPresenter(provideAPI(false));
        presenter.fetchImages(DUMMY, DUMMY, DUMMY, PAGE_NUMBER, MAX_ITEMS_PER_PAGE, view);
        Mockito.verify(view, times(1)).callFailed(any(Throwable.class));
    }

    @Test
    public void unsubscribeTest() {
        ImageDetailsPresenter presenter = new ImageDetailsPresenter(provideAPI(false));
        presenter.unsubscribe();
        Mockito.verifyZeroInteractions(view);
    }

    @Test
    public void onDestroyTest() {
        ImageDetailsPresenter presenter = new ImageDetailsPresenter(provideAPI(false));
        presenter.onDestroy();
        Mockito.verifyZeroInteractions(view);
    }

    private ShutterstockAPI provideAPI(boolean success) {
        if (success) {
            return new MockRecipeResponseSuccess();
        }
        return new MockRecipeResponseFailure();
    }

    private class MockRecipeResponseSuccess implements ShutterstockAPI {

        @Override
        public Observable<ImageSearchResponse> searchImageByCategory(String category, String query, String searchId, int page, int perPage) {
            return Observable.just(new ImageSearchResponse());
        }
    }

    private class MockRecipeResponseFailure implements ShutterstockAPI {

        @Override
        public Observable<ImageSearchResponse> searchImageByCategory(String category, String query, String searchId, int page, int perPage) {
            return Observable.error(Throwable::new);
        }
    }

}
