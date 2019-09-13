package com.prashanth.imageapp.dependencyInjection;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.prashanth.imageapp.adapter.ImageListAdapter;
import com.prashanth.imageapp.presenter.ImageDetailsPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    private Context context;

    public PresenterModule(Context context) {
        this.context = context;
    }

    @Provides
    public LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(context);
    }

    @Provides
    public ImageListAdapter provideRecyclerViewAdapter() {
        return new ImageListAdapter(context);
    }

    @Provides
    public ImageDetailsPresenter provideImageListPresenter() {return new ImageDetailsPresenter();}

}
