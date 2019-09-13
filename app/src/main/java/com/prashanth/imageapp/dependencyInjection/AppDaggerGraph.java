package com.prashanth.imageapp.dependencyInjection;

import com.prashanth.imageapp.presenter.ImageDetailsPresenter;
import com.prashanth.imageapp.ui.MainActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {NetworkDaggerModule.class, ApplicationModule.class, PresenterModule.class})
public interface AppDaggerGraph {

    void inject(MainActivity activity);

    void inject(ImageDetailsPresenter imageDetailsPresenter);
}