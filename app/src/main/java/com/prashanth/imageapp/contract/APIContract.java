package com.prashanth.imageapp.contract;

import com.prashanth.imageapp.model.ImageDetails;
import java.util.ArrayList;

public interface APIContract {

    interface View {

        void callStarted();

        void callComplete();

        void callFailed(Throwable throwable);
    }

    interface ImageListView extends View {

        void onResponseImageList(ArrayList<ImageDetails> imageDetails);
    }

    interface Presenter {

        void unsubscribe();

        void onDestroy();

        void fetchImages(String category, String query, String searchId,
                         int pageNumber, int numberOfItemsPerPage, APIContract.ImageListView view);
    }

}
